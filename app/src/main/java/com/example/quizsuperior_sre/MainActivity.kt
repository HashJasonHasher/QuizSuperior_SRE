package com.example.quizsuperior_sre

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizsuperior_sre.ui.theme.QuizSuperior_SRETheme
import androidx.core.content.edit
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

data class QuizColors(
    val paper: Color,
    val clay: Color,
    val slate: Color,
    val deepSlate: Color,
    val softWhite: Color,
    val danger: Color,
    val success: Color,
    val selected: Color,
    val correct: Color
)

val LocalQuizColors = staticCompositionLocalOf<QuizColors> {
    error("No QuizColors provided")
}

private val LightQuizColors = QuizColors(
    paper = Color(0xFFF4EBC8),
    clay = Color(0xFFD28A5B),
    slate = Color(0xFF506477),
    deepSlate = Color(0xFF314452),
    softWhite = Color(0xFFFDF8EA),
    danger = Color(0xFFB73A3A),
    success = Color(0xFF2E7D32),
    selected = Color(0xFFE7EEF5),
    correct = Color(0xFFDFF3E0)
)

private val DarkQuizColors = QuizColors(
    paper = Color(0xFF2D3748),
    clay = Color(0xFF1A202C),
    slate = Color(0xFFCBD5E0),
    deepSlate = Color(0xFFE2E8F0),
    softWhite = Color(0xFF4A5568),
    danger = Color(0xFFF56565),
    success = Color(0xFF48BB78),
    selected = Color(0xFF3B4D61),
    correct = Color(0xFF2D4F35)
)

private val Paper @Composable get() = LocalQuizColors.current.paper
private val Clay @Composable get() = LocalQuizColors.current.clay
private val Slate @Composable get() = LocalQuizColors.current.slate
private val DeepSlate @Composable get() = LocalQuizColors.current.deepSlate
private val SoftWhite @Composable get() = LocalQuizColors.current.softWhite
private val Danger @Composable get() = LocalQuizColors.current.danger
private val Success @Composable get() = LocalQuizColors.current.success

sealed class AppScreen {
    object Title : AppScreen()
    object Choose : AppScreen()
    data class Quiz(val subjectId: String) : AppScreen()
    data class Cards(val subjectId: String) : AppScreen()
    data class Resources(val subjectId: String) : AppScreen()
    object Leaderboard : AppScreen()
    data class Result(val score: Int, val total: Int, val subjectName: String, val timedOut: Boolean) : AppScreen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var darkTheme by remember { 
                mutableStateOf(getPreferences(Context.MODE_PRIVATE).getBoolean("dark_mode", false)) 
            }
            
            val quizColors = if (darkTheme) DarkQuizColors else LightQuizColors
            
            CompositionLocalProvider(LocalQuizColors provides quizColors) {
                QuizSuperior_SRETheme(darkTheme = darkTheme) {
                    QuizSuperiorApp(
                        isDarkTheme = darkTheme,
                        onDarkThemeChange = { 
                            darkTheme = it
                            getPreferences(Context.MODE_PRIVATE).edit().putBoolean("dark_mode", it).apply()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizSuperiorApp(
    isDarkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit

) {
    val context = LocalContext.current
    val activity = context as Activity

    val uriHandler = LocalUriHandler.current
    val subjects = remember { QuizRepository.sampleSubjects() }
    
    val prefs = remember { context.getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE) }
    
    fun loadLeaderboard(): List<ScoreEntry> {
        val data = prefs.getString("leaderboard", null) ?: return listOf(
            ScoreEntry("Alex", "Calculus 1", 4, 4),
            ScoreEntry("Sam", "History", 4, 4),
            ScoreEntry("Jordan", "Java Prog 1", 3, 4),
            ScoreEntry("Taylor", "Trigonometry", 3, 4),
            ScoreEntry("Riley", "Python 1", 2, 4)
        )
        return try {
            data.split(";").filter { it.isNotBlank() }.map { line ->
                val parts = line.split("|")
                ScoreEntry(parts[0], parts[1], parts[2].toInt(), parts[3].toInt())
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveLeaderboard(entries: List<ScoreEntry>) {
        val serialized = entries.joinToString(";") { "${it.playerName}|${it.subjectName}|${it.correct}|${it.total}" }
        prefs.edit { putString("leaderboard", serialized) }
    }

    val leaderboard = remember {
        mutableStateListOf<ScoreEntry>().apply { addAll(loadLeaderboard()) }
    }

    var screen by remember { mutableStateOf<AppScreen>(AppScreen.Title) }
    var selectedSubjectId by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var showSettings by remember { mutableStateOf(false) }
    var soundEffects by remember { mutableStateOf(true) }
    var music by remember { mutableStateOf(false) }
    var hints by remember { mutableStateOf(true) }

    val selectedSubject = selectedSubjectId?.let { id -> subjects.firstOrNull { it.id == id } }

    if (showSettings) {
        SettingsDialog(
            soundEffects = soundEffects,
            music = music,
            hints = hints,
            isDarkTheme = isDarkTheme,
            onSoundEffectsChange = { soundEffects = it },
            onMusicChange = { music = it },
            onHintsChange = { hints = it },
            onDarkThemeChange = onDarkThemeChange,
            onDismiss = { showSettings = false }
        )
    }

    when (val current = screen) {
        AppScreen.Title -> TitleScreen(
            onStartClick = { screen = AppScreen.Choose },
            onLeadClick = { screen = AppScreen.Leaderboard },
            onExitClick = { activity.finish() },
            onSettingsClick = { showSettings = true }
        )

        AppScreen.Choose -> ChooseScreen(
            selectedSubject = selectedSubject,
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            subjects = subjects,
            onBackClick = { screen = AppScreen.Title },
            onSubjectSelected = { selectedSubjectId = it.id },
            onStartQuiz = {
                if (selectedSubject != null) {
                    screen = AppScreen.Quiz(selectedSubject.id)
                }
            },
            onOpenCards = {
                if (selectedSubject != null) {
                    screen = AppScreen.Cards(selectedSubject.id)
                }
            },
            onOpenResources = {
                if (selectedSubject != null) {
                    screen = AppScreen.Resources(selectedSubject.id)
                }
            },
            onInvalidAction = {
                // handled inside screen by local error message
            }
        )

        is AppScreen.Quiz -> {
            val subject = subjects.first { it.id == current.subjectId }
            QuizScreen(
                subject = subject,
                soundEffects = soundEffects,
                hints = hints,
                onBackClick = { screen = AppScreen.Choose },
                onFinished = { score, total, timedOut ->
                    screen = AppScreen.Result(score, total, subject.name, timedOut)
                }
            )
        }

        is AppScreen.Cards -> {
            val subject = subjects.first { it.id == current.subjectId }
            CardsScreen(
                subject = subject,
                onBackClick = { screen = AppScreen.Choose }
            )
        }

        is AppScreen.Resources -> {
            val subject = subjects.first { it.id == current.subjectId }
            ResourcesScreen(
                subject = subject,
                uriHandler = uriHandler,
                onBackClick = { screen = AppScreen.Choose }
            )
        }

        AppScreen.Leaderboard -> LeaderboardScreen(
            entries = leaderboard,
            onBackClick = { screen = AppScreen.Title }
        )

        is AppScreen.Result -> ResultScreen(
            subjectName = current.subjectName,
            score = current.score,
            total = current.total,
            timedOut = current.timedOut,
            onSaveScore = { playerName ->
                val newEntry = ScoreEntry(playerName, current.subjectName, current.score, current.total)
                leaderboard.add(0, newEntry)
                leaderboard.sortByDescending { it.percent }
                saveLeaderboard(leaderboard.toList())
                screen = AppScreen.Leaderboard
            },
            onPlayAgain = {
                selectedSubjectId?.let { id ->
                    screen = AppScreen.Quiz(id)
                } ?: run { screen = AppScreen.Choose }
            },
            onHome = { screen = AppScreen.Title },
            onLeaderboard = { screen = AppScreen.Leaderboard }
        )
    }
}

@Composable
private fun TitleScreen(
    onStartClick: () -> Unit,
    onLeadClick: () -> Unit,
    onExitClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Clay)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.34f)
                .background(Paper),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "QUIZ",
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Black,
                    color = Slate,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "SUPERIOR",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Black,
                    color = Slate,
                    letterSpacing = 1.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.66f)
                .padding(horizontal = 40.dp, vertical = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            PrimaryMenuButton(text = "START", onClick = onStartClick)
            PrimaryMenuButton(text = "SCORE", onClick = onLeadClick)
            PrimaryMenuButton(text = "EXIT", onClick = onExitClick)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onSettingsClick,
                modifier = Modifier.size(88.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .background(Paper, RoundedCornerShape(18.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "⚙", fontSize = 44.sp, color = Slate)
                }
            }
        }
    }
}

@Composable
private fun ChooseScreen(
    selectedSubject: Subject?,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    subjects: List<Subject>,
    onBackClick: () -> Unit,
    onSubjectSelected: (Subject) -> Unit,
    onStartQuiz: () -> Unit,
    onOpenCards: () -> Unit,
    onOpenResources: () -> Unit,
    onInvalidAction: () -> Unit
) {
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val filtered = remember(searchQuery, subjects) {
        if (searchQuery.isBlank()) subjects else subjects.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
                it.group.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Clay)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Paper)
                .padding(top = 30.dp, bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CHOOSE",
                fontSize = 54.sp,
                fontWeight = FontWeight.Black,
                color = Slate,
                letterSpacing = 1.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    onSearchQueryChange(it)
                    errorMessage = null
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search subjects") },
                singleLine = true
            )

            selectedSubject?.let {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Paper),
                    shape = RoundedCornerShape(22.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(text = "Selected", fontWeight = FontWeight.Bold, color = Slate)
                        Text(text = it.name, fontSize = 24.sp, fontWeight = FontWeight.Black, color = DeepSlate)
                        Text(text = it.description, color = Slate)
                    }
                }
            }

            if (filtered.isEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SoftWhite),
                    shape = RoundedCornerShape(22.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "No subjects match \"$searchQuery\".",
                        modifier = Modifier.padding(18.dp),
                        color = Danger,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                val grouped = filtered.groupBy { it.group }
                grouped.forEach { (group, groupSubjects) ->
                    Text(
                        text = group.uppercase(),
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                        fontWeight = FontWeight.Black,
                        color = Paper,
                        letterSpacing = 1.sp
                    )
                    groupSubjects.forEach { subject ->
                        SubjectTile(
                            subject = subject,
                            selected = selectedSubject?.id == subject.id,
                            onClick = {
                                onSubjectSelected(subject)
                                errorMessage = null
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            errorMessage?.let {
                Text(text = it, color = Color.White, fontWeight = FontWeight.Bold)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ActionButton(text = "START QUIZZING", modifier = Modifier.weight(1f)) {
                    if (selectedSubject == null) {
                        errorMessage = "Select a subject first."
                        onInvalidAction()
                    } else {
                        onStartQuiz()
                    }
                }
                ActionButton(text = "CARDS", modifier = Modifier.weight(1f)) {
                    if (selectedSubject == null) {
                        errorMessage = "Select a subject first."
                        onInvalidAction()
                    } else {
                        onOpenCards()
                    }
                }
            }

            ActionButton(text = "RESOURCES", modifier = Modifier.fillMaxWidth()) {
                if (selectedSubject == null) {
                    errorMessage = "Select a subject first."
                    onInvalidAction()
                } else {
                    onOpenResources()
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            BackButton(onClick = onBackClick)
        }
    }
}

@Composable
private fun QuizScreen(
    subject: Subject,
    soundEffects: Boolean,
    hints: Boolean,
    onBackClick: () -> Unit,
    onFinished: (score: Int, total: Int, timedOut: Boolean) -> Unit
) {
    val questions = remember(subject.id) { subject.questions.shuffled() }
    var currentIndex by remember(subject.id) { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var answered by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }
    var feedback by remember { mutableStateOf<String?>(null) }
    var timeLeft by remember(subject.id) { mutableIntStateOf(60) }
    var finished by remember { mutableStateOf(false) }

    fun finishQuiz(timedOut: Boolean) {
        if (finished) return
        finished = true
        onFinished(score, questions.size, timedOut)
    }

    LaunchedEffect(subject.id) {
        while (timeLeft > 0 && !finished) {
            kotlinx.coroutines.delay(1000)
            timeLeft -= 1
        }
        if (!finished && timeLeft == 0) {
            finishQuiz(timedOut = true)
        }
    }

    val question = questions[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Clay)
            .verticalScroll(rememberScrollState())
            .padding(18.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Paper, RoundedCornerShape(24.dp))
                .padding(18.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(text = subject.name.uppercase(), fontWeight = FontWeight.Black, color = Slate, fontSize = 30.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = subject.description, color = Slate, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Time Left: ${formatTime(timeLeft)}",
                    color = if (timeLeft <= 10) Danger else DeepSlate,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Question ${currentIndex + 1} / ${questions.size}",
                    fontWeight = FontWeight.Bold,
                    color = Slate
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = SoftWhite),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(text = question.prompt, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = DeepSlate)

                question.options.forEachIndexed { index, option ->
                    val selected = selectedOption == index
                    val background = when {
                        answered && index == question.correctIndex -> LocalQuizColors.current.correct
                        selected -> LocalQuizColors.current.selected
                        else -> Paper
                    }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = background),
                        shape = RoundedCornerShape(18.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !answered) { selectedOption = index }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = option, modifier = Modifier.weight(1f), color = DeepSlate)
                            Text(
                                text = if (selected) "●" else "○",
                                color = Slate,
                                fontSize = 18.sp
                            )
                        }
                    }
                }

                if (feedback != null) {
                    Text(
                        text = feedback!!,
                        color = if (feedback!!.startsWith("Correct")) Success else Danger,
                        fontWeight = FontWeight.Bold
                    )
                    if (answered && hints) {
                        Text(
                            text = question.explanation,
                            color = Slate,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = onBackClick, modifier = Modifier.weight(1f)) {
                Text("Back")
            }

            ActionButton(
                text = if (!answered) "Submit" else if (currentIndex == questions.lastIndex) "Finish" else "Next",
                modifier = Modifier.weight(1f)
            ) {
                if (!answered) {
                    val picked = selectedOption
                    if (picked == null) {
                        feedback = "Choose an answer first."
                        return@ActionButton
                    }
                    answered = true
                    val correct = picked == question.correctIndex
                    if (correct) {
                        score += 1
                        feedback = if (soundEffects) "Correct! +1" else "Correct!"
                    } else {
                        feedback = "Incorrect! Answer: ${question.options[question.correctIndex]}"
                    }
                } else {
                    if (currentIndex == questions.lastIndex) {
                        finishQuiz(timedOut = false)
                    } else {
                        currentIndex += 1
                        selectedOption = null
                        answered = false
                        feedback = null
                    }
                }
            }
        }
    }
}

@Composable
private fun CardsScreen(
    subject: Subject,
    onBackClick: () -> Unit
) {
    var expandedIndex by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Clay)
            .verticalScroll(rememberScrollState())
            .padding(18.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Paper, RoundedCornerShape(24.dp))
                .padding(18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "CARDS", fontWeight = FontWeight.Black, color = Slate, fontSize = 38.sp)
        }

        Spacer(modifier = Modifier.height(18.dp))

        subject.cards.forEachIndexed { index, card ->
            Card(
                colors = CardDefaults.cardColors(containerColor = SoftWhite),
                shape = RoundedCornerShape(22.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clickable { expandedIndex = if (expandedIndex == index) null else index }
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = card.title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = DeepSlate)
                    Text(text = if (expandedIndex == index) card.body else card.body.take(72) + if (card.body.length > 72) "..." else "", color = Slate)
                    Text(
                        text = if (expandedIndex == index) "Tap to collapse" else "Tap to expand",
                        color = LocalQuizColors.current.slate,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        BackButton(onClick = onBackClick)
    }
}

@Composable
private fun ResourcesScreen(
    subject: Subject,
    uriHandler: androidx.compose.ui.platform.UriHandler,
    onBackClick: () -> Unit
) {
    var filter by remember { mutableStateOf("") }
    val resources = remember(subject.id, filter) {
        if (filter.isBlank()) subject.resources else subject.resources.filter {
            it.title.contains(filter, ignoreCase = true) || it.summary.contains(filter, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Clay)
            .verticalScroll(rememberScrollState())
            .padding(18.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Paper, RoundedCornerShape(24.dp))
                .padding(18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "RESOURCES", fontWeight = FontWeight.Black, color = Slate, fontSize = 34.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = filter,
            onValueChange = { filter = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search resources") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (resources.isEmpty()) {
            Card(
                colors = CardDefaults.cardColors(containerColor = SoftWhite),
                shape = RoundedCornerShape(22.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "No resources available for \"$filter\". Try a different search.",
                    modifier = Modifier.padding(18.dp),
                    color = Danger,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            resources.forEach { resource ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = SoftWhite),
                    shape = RoundedCornerShape(22.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = resource.title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = DeepSlate)
                        Text(text = resource.summary, color = Slate)
                        if (!resource.url.isNullOrBlank()) {
                            TextButton(onClick = { uriHandler.openUri(resource.url) }) {
                                Text("Open resource")
                            }
                        } else {
                            Text(text = "No external link attached.", color = Slate)
                        }
                    }
                }
            }
        }

        BackButton(onClick = onBackClick)
    }
}

@Composable
private fun LeaderboardScreen(
    entries: List<ScoreEntry>,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Clay)
            .verticalScroll(rememberScrollState())
            .padding(18.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Paper, RoundedCornerShape(24.dp))
                .padding(18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "BEST SCORES", fontWeight = FontWeight.Black, color = Slate, fontSize = 32.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        entries.take(10).forEachIndexed { index, entry ->
            Card(
                colors = CardDefaults.cardColors(containerColor = SoftWhite),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${index + 1}", fontSize = 24.sp, fontWeight = FontWeight.Black, color = Slate)
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = entry.playerName, fontWeight = FontWeight.Bold, color = DeepSlate)
                        Text(text = entry.subjectName, color = Slate)
                        Text(text = "${entry.correct}/${entry.total} (${entry.percent}%)", color = Slate)
                    }
                }
            }
        }

        BackButton(onClick = onBackClick)
    }
}

@Composable
private fun ResultScreen(
    subjectName: String,
    score: Int,
    total: Int,
    timedOut: Boolean,
    onSaveScore: (String) -> Unit,
    onPlayAgain: () -> Unit,
    onHome: () -> Unit,
    onLeaderboard: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var hasSaved by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Clay)
            .padding(18.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Paper),
            shape = RoundedCornerShape(26.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = if (timedOut) "TIME OVER" else "GAME OVER",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black,
                    color = Slate
                )
                Text(text = subjectName, fontWeight = FontWeight.Bold, color = DeepSlate)
                Text(text = "$score / $total", fontSize = 34.sp, fontWeight = FontWeight.Black, color = DeepSlate)
                Text(text = "${if (total == 0) 0 else score * 100 / total}%", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Slate)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (!hasSaved) {
            Card(
                colors = CardDefaults.cardColors(containerColor = SoftWhite),
                shape = RoundedCornerShape(22.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Enter name for leaderboard:", fontWeight = FontWeight.Bold, color = DeepSlate)
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Your Name") },
                        singleLine = true
                    )
                    ActionButton(text = "SAVE SCORE", modifier = Modifier.fillMaxWidth()) {
                        if (name.isNotBlank()) {
                            onSaveScore(name)
                            hasSaved = true
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            ActionButton(text = "PLAY AGAIN", modifier = Modifier.weight(1f), onClick = onPlayAgain)
            ActionButton(text = "LEADERBOARD", modifier = Modifier.weight(1f), onClick = onLeaderboard)
        }

        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(onClick = onHome, modifier = Modifier.fillMaxWidth()) {
            Text("HOME")
        }
    }
}

@Composable
private fun SettingsDialog(
    soundEffects: Boolean,
    music: Boolean,
    hints: Boolean,
    isDarkTheme: Boolean,
    onSoundEffectsChange: (Boolean) -> Unit,
    onMusicChange: (Boolean) -> Unit,
    onHintsChange: (Boolean) -> Unit,
    onDarkThemeChange: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Settings") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("Sound Effects")
                    Switch(checked = soundEffects, onCheckedChange = onSoundEffectsChange)
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("Music")
                    Switch(checked = music, onCheckedChange = onMusicChange)
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("Show Hints")
                    Switch(checked = hints, onCheckedChange = onHintsChange)
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("Dark Mode")
                    Switch(checked = isDarkTheme, onCheckedChange = onDarkThemeChange)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Done") }
        }
    )
}

@Composable
private fun PrimaryMenuButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.82f)
            .height(74.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Paper, contentColor = Slate),
        shape = RoundedCornerShape(18.dp)
    ) {
        Text(text = text, fontWeight = FontWeight.Black, fontSize = 32.sp, letterSpacing = 1.sp)
    }
}

@Composable
private fun ActionButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Paper, contentColor = Slate),
        shape = RoundedCornerShape(18.dp)
    ) {
        Text(text = text, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
    }
}

@Composable
private fun SubjectTile(subject: Subject, selected: Boolean, onClick: () -> Unit) {
    val bg = if (selected) Paper else SoftWhite
    Card(
        colors = CardDefaults.cardColors(containerColor = bg),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(text = subject.name, fontWeight = FontWeight.Black, color = DeepSlate)
                Text(text = subject.group, color = Slate, fontWeight = FontWeight.Bold)
            }
            Text(text = subject.description, color = Slate)
        }
    }
}

@Composable
private fun BackButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(140.dp)
            .height(70.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Paper, contentColor = Slate),
        shape = RoundedCornerShape(18.dp)
    ) {
        Text(text = "BACK", fontWeight = FontWeight.Black, fontSize = 22.sp)
    }
}

private fun formatTime(seconds: Int): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format(java.util.Locale.US, "%02d:%02d", mins, secs)
}
