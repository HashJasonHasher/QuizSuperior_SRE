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
                TitleScreen(
                    onStartClick = {},
                    onLeadClick = {},
                    onExitClick = { finish() },
                    onSettingsClick = {
                        // Handle settings click later
                    }
                )
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