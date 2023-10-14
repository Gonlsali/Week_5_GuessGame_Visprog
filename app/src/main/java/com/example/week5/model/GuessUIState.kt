package com.example.week5.model

import kotlin.random.Random
import kotlin.random.nextInt

data class GuessUIState(
    val chances: Int = 3,
    val score: Int = 0,
    val answer: Int = Random.nextInt(1..10),
    val isGameOver: Boolean = false,
)