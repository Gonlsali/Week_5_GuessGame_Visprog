package com.example.week5.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week5.viewmodel.GuessGameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuessGameView(
    guessGameViewModel: GuessGameViewModel = viewModel()
) {
    val guessState by guessGameViewModel.guessUIState.collectAsState()
    var guessValue by rememberSaveable { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Guess The Number",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(12.dp)
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray
            ),
            modifier = Modifier
                .size(width = 320.dp, height = 320.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF3F44BB)
                    ),
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.End)
                        .size(width = 190.dp, height = 32.dp),
                ) {
                    Text(
                        text = "Number of Guesses : ${guessState.chances}",
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(vertical = 7.dp, horizontal = 16.dp)
                    )
                }

                Text(
                    text = guessState.answer.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(12.dp)
                )

                Text(
                    text = """
                        From 1 to 10 Guess the Number.
                        Score : ${guessState.score}
                    """.trimIndent(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedTextField(
                    value = guessValue,
                    onValueChange = {
                        guessValue = it
                    },
                    label = {
                        Text(text = "Enter your number")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .width(250.dp),
                    shape = RoundedCornerShape(25.dp)
                )

                Button(
                    onClick = { guessGameViewModel.check(guessValue) }
                ) {
                    Text(
                        text = "Guess",
                        fontSize = 20.sp
                    )
                }

                if (guessState.isGameOver) {
                    LostCardAlert(guessGameViewModel)
                }

                if (guessState.score == 3) {
                    WinCardAlert(guessGameViewModel)
                }
            }
        }
    }
}

@Composable
fun WinCardAlert(
    guessViewModel: GuessGameViewModel
) {
    AlertDialog(
        title = {
            Text(
                text = "Welp,"
            )
        },
        text = {
            Text(
                text = "You Scored 3!"
            )
        },
        onDismissRequest = {},
        confirmButton = {
            Button(
                onClick = { guessViewModel.resetQuestion()}
            )
            {
                Text(text = "Play Again")
            }
        },
        dismissButton = {
            Button(
                onClick = { }
            )
            {
                Text(text = "Exit")
            }
        }
    )
}

@Composable
fun LostCardAlert(
    guessViewModel: GuessGameViewModel
) {
    AlertDialog(
        title = {
            Text(
                text = "Oh no!"
            )
        },
        text = {
            Text(
                text = "You lose!"
            )
        },
        onDismissRequest = {},
        confirmButton = {
            Button(
                onClick = { guessViewModel.resetQuestion()}
            )
            {
                Text(text = "Play Again")
            }
        },
        dismissButton = {
            Button(
                onClick = { }
            )
            {
                Text(text = "Exit")
            }
        }
    )
}

@Preview (showBackground = true, showSystemUi = true)
@Composable
fun GuessGamePreview() {
    GuessGameView()
}