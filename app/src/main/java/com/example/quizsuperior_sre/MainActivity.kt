package com.example.quizsuperior_sre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import com.example.quizsuperior_sre.ui.theme.QuizSuperior_SRETheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QuizSuperior_SRETheme {
                var currentScreen by remember { mutableStateOf("title") }

                when (currentScreen) {
                    "title" -> TitleScreen(
                        onStartClick = {},
                        onLeadClick = { currentScreen = "leaderboard" },
                        onExitClick = { finish() },
                        onSettingsClick = {
                            currentScreen = "settings"
                        }
                    )
                    "leaderboard" -> LeaderboardScreen(
                        onBackClick = { currentScreen = "title" }
                    )
                    "settings" -> SettingsScreen(
                        onBackClick = { currentScreen = "title" }
                    )
                }
            }
        }
    }
}

@Composable
fun TitleScreen(
    onStartClick: () -> Unit,
    onLeadClick: () -> Unit,
    onExitClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {

        // Center content
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Quiz Superior",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(60.dp))

            Button(
                onClick = onStartClick,
                modifier = Modifier.width(220.dp)
            ) {
                Text("Start Quiz")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onLeadClick,
                modifier = Modifier.width(220.dp)
            ) {
                Text("Leaderboard")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onExitClick,
                modifier = Modifier.width(220.dp)
            ) {
                Text("Exit")
            }
        }

        // Bottom-right settings button (empty image placeholder)
        IconButton(
            onClick = onSettingsClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(100.dp)
        ) {
            // 🔽 Replace this later with your cog icon
            Image(
                painterResource(id = R.drawable.cog),
                contentDescription = "Settings",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TitleScreenPreview() {
    QuizSuperior_SRETheme {
        TitleScreen(
            onStartClick = {},
            onLeadClick = {},
            onExitClick = {},
            onSettingsClick = {}
        )
    }
}

@Composable
fun LeaderboardScreen(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        // Center content: Title and Empty Column
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Leaderboard",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Empty column list placeholder
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Future leaderboard data goes here
            }
        }

        // Back button in the bottom-left corner
        Button(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .width(120.dp)
        ) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeaderboardScreenPreview() {
    QuizSuperior_SRETheme {
        LeaderboardScreen(onBackClick = {})
    }
}

@Composable
fun SettingsScreen(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        // Title at the top center
        Text(
            text = "Settings",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        // Center content: Options
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Dark Mode: Off", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Language: English", fontSize = 20.sp)
        }

        // Back button in the bottom-left corner
        Button(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .width(120.dp)
        ) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    QuizSuperior_SRETheme {
        SettingsScreen(onBackClick = {})
    }
}