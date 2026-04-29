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
import com.example.quizsuperior_sre.ui.theme.QuizSuperior_SRETheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QuizSuperior_SRETheme {
                TitleScreen(
                    onStartClick = {
                        // Start quiz later
                    },
                    onOptionsClick = {
                        // Open options later
                    },
                    onExitClick = {
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun TitleScreen(
    onStartClick: () -> Unit,
    onOptionsClick: () -> Unit,
    onExitClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
            Text(text = "Start Quiz")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onOptionsClick,
            modifier = Modifier.width(220.dp)
        ) {
            Text(text = "Options")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onExitClick,
            modifier = Modifier.width(220.dp)
        ) {
            Text(text = "Exit")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TitleScreenPreview() {
    QuizSuperior_SRETheme {
        TitleScreen(
            onStartClick = {},
            onOptionsClick = {},
            onExitClick = {}
        )
    }
}