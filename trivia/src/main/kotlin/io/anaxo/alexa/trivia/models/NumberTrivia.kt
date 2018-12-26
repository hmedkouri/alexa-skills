package io.anaxo.alexa.trivia.models

data class NumberTrivia (
    val text: String,
    val number: Int,
    val found: Boolean,
    val type: String?
)
