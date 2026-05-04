package com.example.quizsuperior_sre

// Data model file for Quiz Superior.
// These data classes define the app's main objects: quiz questions, flashcards, resources, subjects, and leaderboard scores.

// Represents one multiple-choice quiz question.
// prompt = question text, options = answer choices, correctIndex = index of the right answer, explanation = feedback shown after answering.
data class QuizQuestion(
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

// Represents one flashcard used by the Cards screen.
// title is the front/heading of the card, and body contains the study explanation.
data class StudyCard(
    val title: String,
    val body: String
)

// Represents one outside learning resource shown on the Resources screen.
// The url is nullable so a resource can exist even if it does not have an external link.
data class StudyResource(
    val title: String,
    val summary: String,
    val url: String? = null
)

// Represents a full quiz topic/subject.
// Each subject has metadata plus its own questions, flashcards, and resource links.
data class Subject(
    val id: String,
    val group: String,
    val name: String,
    val description: String,
    val questions: List<QuizQuestion>,
    val cards: List<StudyCard>,
    val resources: List<StudyResource>
)

// Represents one leaderboard score saved by the user.
// The percent property below is calculated automatically from correct and total.
data class ScoreEntry(
    val playerName: String,
    val subjectName: String,
    val correct: Int,
    val total: Int
) {
    // Computed score percentage. The total == 0 check prevents division by zero.
    val percent: Int
        get() = if (total == 0) 0 else (correct * 100 / total)
}
