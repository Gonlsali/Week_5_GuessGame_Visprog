package com.example.week5.viewmodel

import androidx.lifecycle.ViewModel
import com.example.week5.model.GuessUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.nextInt

class GuessGameViewModel: ViewModel() {
    private val _guessUIState = MutableStateFlow(GuessUIState())
    val guessUIState: StateFlow<GuessUIState> = _guessUIState.asStateFlow()

    init {
        resetQuestion()
    }

    fun resetQuestion() {
        _guessUIState.update { guess ->
            guess.copy(
                answer = Random.nextInt(1..10),
                isGameOver = false,
                score = 0,
                chances = 3
            )
        }
    }

    fun check(value: String) {
        val guess = value.toIntOrNull()?:0
        if (guess == _guessUIState.value.answer) {
            _guessUIState.update { guess ->
                guess.copy(
                    score = guess.score + 1,
                    answer = Random.nextInt(1..10)
                )
            }
        } else {
            _guessUIState.update { guess ->
                guess.copy(
                    answer = Random.nextInt(1..10),
                    chances = guess.chances - 1,
                    score = 0
                )
            }
        }

        // Chance 0 akan game over
        if (_guessUIState.value.chances == 0){
            _guessUIState.update { guess->
                guess.copy(
                    isGameOver = true
                )
            }
        }
    }
}