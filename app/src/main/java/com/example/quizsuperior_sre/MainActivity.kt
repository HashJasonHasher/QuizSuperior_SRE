package com.example.quizsuperior_sre

// MainActivity.kt contains the app entry point, theme color setup, screen navigation, and all Jetpack Compose UI screens.
// The app uses manual screen state with the AppScreen sealed class instead of a separate navigation library.

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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

// Holds the custom color palette used throughout the quiz app.
// The app provides either LightQuizColors or DarkQuizColors depending on the dark mode setting.
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

// CompositionLocal used to make QuizColors available to every composable without passing colors manually as parameters.
val LocalQuizColors = staticCompositionLocalOf<QuizColors> {
    error("No QuizColors provided")
}

// Light mode version of the custom app color palette.
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

// Dark mode version of the custom app color palette.
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

// Convenience getters for the current theme colors.
// These make the UI code easier to read than repeatedly typing LocalQuizColors.current.colorName.
private val Paper @Composable get() = LocalQuizColors.current.paper
private val Clay @Composable get() = LocalQuizColors.current.clay
private val Slate @Composable get() = LocalQuizColors.current.slate
private val DeepSlate @Composable get() = LocalQuizColors.current.deepSlate
private val SoftWhite @Composable get() = LocalQuizColors.current.softWhite
private val Danger @Composable get() = LocalQuizColors.current.danger
private val Success @Composable get() = LocalQuizColors.current.success

// Defines every screen/state the app can display.
// The screen variable in QuizSuperiorApp stores one of these values to control navigation.
sealed class AppScreen {
    object Title : AppScreen()
    object Choose : AppScreen()
    data class Quiz(val subjectIds: List<String>) : AppScreen()
    data class Cards(val subjectId: String) : AppScreen()
    data class Resources(val subjectId: String) : AppScreen()
    object Leaderboard : AppScreen()
    data class Result(val score: Int, val total: Int, val subjectName: String, val timedOut: Boolean) : AppScreen()
}

// Main Android activity. This is the first Kotlin class Android launches when the app opens.
class MainActivity : ComponentActivity() {
    // Called when the activity is created. Sets up edge-to-edge display, theme state, and Compose content.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // Loads the saved dark mode preference so the app remembers the user's theme choice.
            var darkTheme by remember {
                mutableStateOf(getPreferences(MODE_PRIVATE).getBoolean("dark_mode", false))
            }

            // Chooses the correct custom color palette based on the dark mode switch.
            val quizColors = if (darkTheme) DarkQuizColors else LightQuizColors

            CompositionLocalProvider(LocalQuizColors provides quizColors) {
                QuizSuperior_SRETheme(darkTheme = darkTheme) {
                    QuizSuperiorApp(
                        isDarkTheme = darkTheme,
                        onDarkThemeChange = {
                            darkTheme = it
                            getPreferences(MODE_PRIVATE).edit { putBoolean("dark_mode", it) }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
// Root composable for the entire app.
// It owns global app state, screen navigation, selected subjects, settings, and leaderboard storage.
@Composable
fun QuizSuperiorApp(
    isDarkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit

) {
    // Accesses Android context/activity so the app can use SharedPreferences, URLs, and exit behavior.
    val context = LocalContext.current
    val activity = context as Activity

    val uriHandler = LocalUriHandler.current
    // Loads the list of available subjects once for the current composition.
    val subjects = remember { QuizRepository.sampleSubjects() }

    // SharedPreferences stores persistent app data such as leaderboard scores and settings.
    val prefs = remember { context.getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE) }

    // Reads leaderboard entries from SharedPreferences.
    // If no saved data exists, default sample scores are shown.
    fun loadLeaderboard(): List<ScoreEntry> {
        val data = prefs.getString("leaderboard", null) ?: return listOf(
            ScoreEntry("Alex", "Calculus 1", 10, 10),
            ScoreEntry("Sam", "History", 9, 10),
            ScoreEntry("Jordan", "Java Prog 1", 7, 10),
            ScoreEntry("Taylor", "Trigonometry", 6, 10),
            ScoreEntry("Riley", "Python 1", 5, 10)
        )
        return try {
            data.split(";").filter { it.isNotBlank() }.map { line ->
                val parts = line.split("|")
                ScoreEntry(parts[0], parts[1], parts[2].toInt(), parts[3].toInt())
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    // Saves leaderboard entries as one serialized string in SharedPreferences.
    fun saveLeaderboard(entries: List<ScoreEntry>) {
        val serialized = entries.joinToString(";") { "${it.playerName}|${it.subjectName}|${it.correct}|${it.total}" }
        prefs.edit { putString("leaderboard", serialized) }
    }

    // Mutable list that updates the leaderboard UI whenever scores are added or removed.
    val leaderboard = remember {
        mutableStateListOf<ScoreEntry>().apply { addAll(loadLeaderboard()) }
    }

    // Current screen. Changing this value is how the app navigates between pages.
    var screen by remember { mutableStateOf<AppScreen>(AppScreen.Title) }
    // Stores all selected subject IDs so quizzes can combine multiple topics.
    var selectedSubjectIds by remember { mutableStateOf<Set<String>>(emptySet()) }
    var searchQuery by remember { mutableStateOf("") }
    var showSettings by remember { mutableStateOf(false) }
    var soundEffects by remember { mutableStateOf(prefs.getBoolean("sound_effects", true)) }
    var music by remember { mutableStateOf(prefs.getBoolean("music", false)) }
    var hints by remember { mutableStateOf(prefs.getBoolean("hints", true)) }

    // Converts selected IDs into full Subject objects for the UI and quiz creation.
    val selectedSubjects = subjects.filter { it.id in selectedSubjectIds }

    // Shows the settings dialog only when the gear button has been clicked.
    if (showSettings) {
        SettingsDialog(
            soundEffects = soundEffects,
            music = music,
            hints = hints,
            isDarkTheme = isDarkTheme,
            onSoundEffectsChange = {
                soundEffects = it
                prefs.edit { putBoolean("sound_effects", it) }
            },
            onMusicChange = {
                music = it
                prefs.edit { putBoolean("music", it) }
            },
            onHintsChange = {
                hints = it
                prefs.edit { putBoolean("hints", it) }
            },
            onDarkThemeChange = onDarkThemeChange,
            onDismiss = { showSettings = false }
        )
    }

    // Scaffold provides one shared top app bar and background for every screen.
    // This avoids adding a toolbar separately to each screen.
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Paper
                )
            )
        },
        containerColor = Clay
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Chooses which screen composable to display based on the current AppScreen value.
            when (val current = screen) {
                AppScreen.Title -> TitleScreen(
                    onStartClick = { screen = AppScreen.Choose },
                    onLeadClick = { screen = AppScreen.Leaderboard },
                    onExitClick = { activity.finish() },
                    onSettingsClick = { showSettings = true }
                )

                AppScreen.Choose -> ChooseScreen(
                    selectedSubjects = selectedSubjects,
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    subjects = subjects,
                    onBackClick = { screen = AppScreen.Title },
                    onSubjectSelected = { subject ->
                        selectedSubjectIds =
                            if (subject.id in selectedSubjectIds) {
                                selectedSubjectIds - subject.id
                            } else {
                                selectedSubjectIds + subject.id
                            }
                    },
                    onStartQuiz = {
                        if (selectedSubjects.isNotEmpty()) {
                            screen = AppScreen.Quiz(selectedSubjects.map { it.id })
                        }
                    },
                    onOpenCards = {
                        if (selectedSubjects.size == 1) {
                            screen = AppScreen.Cards(selectedSubjects.first().id)
                        }
                    },
                    onOpenResources = {
                        if (selectedSubjects.size == 1) {
                            screen = AppScreen.Resources(selectedSubjects.first().id)
                        }
                    },
                    onInvalidAction = {
                        // handled inside screen by local error message
                    }
                )

                is AppScreen.Quiz -> {
                    // Finds all selected subjects and combines their questions into a temporary mixed quiz.
                    val quizSubjects = subjects.filter { it.id in current.subjectIds }

                    // Temporary Subject used only for the quiz screen.
                    // It keeps the QuizScreen reusable for both single-topic and mixed-topic quizzes.
                    val mixedSubject = Subject(
                        id = quizSubjects.joinToString("_") { it.id },
                        group = "Mixed",
                        name = if (quizSubjects.size == 1) {
                            quizSubjects.first().name
                        } else {
                            "Mixed Quiz"
                        },
                        description = if (quizSubjects.size == 1) {
                            quizSubjects.first().description
                        } else {
                            quizSubjects.joinToString(", ") { it.name }
                        },
                        questions = quizSubjects
                            .flatMap { it.questions }
                            .shuffled()
                            .take(10),
                        cards = emptyList(),
                        resources = emptyList()
                    )

                    QuizScreen(
                        subject = mixedSubject,
                        soundEffects = soundEffects,
                        hints = hints,
                        onBackClick = { screen = AppScreen.Choose },
                        onFinished = { score, total, timedOut ->
                            screen = AppScreen.Result(score, total, mixedSubject.name, timedOut)
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
                    onRemoveEntry = { index ->
                        leaderboard.removeAt(index)
                        saveLeaderboard(leaderboard.toList())
                    },
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
                        if (selectedSubjectIds.isNotEmpty()) {
                            screen = AppScreen.Quiz(selectedSubjectIds.toList())
                        } else {
                            screen = AppScreen.Choose
                        }
                    },
                    onHome = { screen = AppScreen.Title },
                    onLeaderboard = { screen = AppScreen.Leaderboard }
                )
            }
        }
    }
}
// Title/home screen with Start, Score, Exit, and Settings buttons.
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

// Subject selection screen. Supports searching, selecting multiple topics, and opening quiz/cards/resources.
@Composable
private fun ChooseScreen(
    selectedSubjects: List<Subject>,
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
    val selectedSubjectIds = selectedSubjects.map { it.id }.toSet()

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

            if (selectedSubjects.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Paper),
                    shape = RoundedCornerShape(22.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(text = "Selected Topics", fontWeight = FontWeight.Bold, color = Slate)
                        Text(
                            text = selectedSubjects.joinToString(", ") { it.name },
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = DeepSlate
                        )
                        Text(
                            text = "Quiz will pull 10 questions from the selected topics.",
                            color = Slate
                        )
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
                            selected = subject.id in selectedSubjectIds,
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
                    if (selectedSubjects.isEmpty()) {
                        errorMessage = "Select a subject first."
                        onInvalidAction()
                    } else {
                        onStartQuiz()
                    }
                }
                ActionButton(text = "CARDS", modifier = Modifier.weight(1f)) {
                    if (selectedSubjects.size != 1) {
                        errorMessage = "Select exactly one subject for cards."
                        onInvalidAction()
                    } else {
                        onOpenCards()
                    }
                }
            }

            ActionButton(text = "RESOURCES", modifier = Modifier.fillMaxWidth()) {
                if (selectedSubjects.size != 1) {
                    errorMessage = "Select exactly one subject for Resources."
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

// Main quiz-taking screen. Displays questions, randomized answer choices, timer, feedback, and scoring.
@Composable
private fun QuizScreen(
    subject: Subject,
    soundEffects: Boolean,
    hints: Boolean,
    onBackClick: () -> Unit,
    onFinished: (score: Int, total: Int, timedOut: Boolean) -> Unit
) {
    // Shuffles the questions once for this quiz attempt.
    val questions = remember(subject.id) { subject.questions.shuffled() }
    var currentIndex by remember(subject.id) { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var answered by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }
    var feedback by remember { mutableStateOf<String?>(null) }
    var timeLeft by remember(subject.id) { mutableIntStateOf(180) }
    var finished by remember { mutableStateOf(false) }

    val question = questions[currentIndex]
    // Pairs each answer option with whether it is correct, then shuffles choices for the current question.
    val optionsWithCorrectness = remember(currentIndex) {
        question.options.mapIndexed { index, s -> s to (index == question.correctIndex) }.shuffled()
    }

    // Finishes the quiz exactly once, then reports the final score to the parent screen.
    fun finishQuiz(timedOut: Boolean) {
        if (finished) return
        finished = true
        onFinished(score, questions.size, timedOut)
    }

    // Countdown timer. Runs once for this quiz and ends the quiz automatically if time reaches zero.
    LaunchedEffect(subject.id) {
        while (timeLeft > 0 && !finished) {
            kotlinx.coroutines.delay(1000)
            timeLeft -= 1
        }
        if (!finished && timeLeft == 0) {
            finishQuiz(timedOut = true)
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

                optionsWithCorrectness.forEachIndexed { index, pair ->
                    val (option, isCorrect) = pair
                    val selected = selectedOption == index
                    val background = when {
                        answered && isCorrect -> LocalQuizColors.current.correct
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
            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text("Back")
            }

            ActionButton(
                text = if (!answered) "Submit" else if (currentIndex == questions.lastIndex) "Finish" else "Next",
                modifier = Modifier.weight(1f)
            ) {
                if (!answered) {
                    val pickedIndex = selectedOption
                    if (pickedIndex == null) {
                        feedback = "Choose an answer first."
                        return@ActionButton
                    }
                    answered = true
                    val isCorrect = optionsWithCorrectness[pickedIndex].second
                    if (isCorrect) {
                        score += 1
                        feedback = if (soundEffects) "Correct! +1" else "Correct!"
                    } else {
                        val correctText = optionsWithCorrectness.find { it.second }?.first ?: "Unknown"
                        feedback = "Incorrect! Answer: $correctText"
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

// Flashcard screen for one selected subject. Cards can be tapped to expand or collapse.
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

// Resources screen for one selected subject. Lets users search resources and open external links.
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

// Leaderboard screen. Shows saved scores and allows entries to be removed.
@Composable
private fun LeaderboardScreen(
    entries: List<ScoreEntry>,
    onRemoveEntry: (Int) -> Unit,
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

        entries.forEachIndexed { index, entry ->
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "${index + 1}", fontSize = 24.sp, fontWeight = FontWeight.Black, color = Slate)
                        Spacer(modifier = Modifier.width(12.dp))
                        IconButton(onClick = { onRemoveEntry(index) }) {
                            Text(text = "🗑", fontSize = 24.sp, color = Danger)
                        }
                    }
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

// End-of-quiz result screen. Shows score, allows saving to leaderboard, and provides navigation options.
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Paper),
                shape = RoundedCornerShape(26.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = if (timedOut) "TIME OVER" else "GAME OVER",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Black,
                        color = Slate,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = subjectName,
                        fontWeight = FontWeight.Bold,
                        color = DeepSlate,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "$score / $total",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Black,
                        color = DeepSlate,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${if (total == 0) 0 else score * 100 / total}%",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate,
                        textAlign = TextAlign.Center
                    )
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
            OutlinedButton(
                onClick = onHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text("HOME")
            }
        }
    }
}

// Settings dialog for sound effects, music toggle, hints, and dark mode.
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

// Large menu button used on the title screen.
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

// Reusable filled action button used across multiple screens.
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

// Clickable card representing one subject in the Choose screen.
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

// Reusable back button used by several screens.
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

// Converts a number of seconds into MM:SS format for the quiz timer.
private fun formatTime(seconds: Int): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format(java.util.Locale.US, "%02d:%02d", mins, secs)
}
