package com.example.quizsuperior_sre

data class QuizQuestion(
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class StudyCard(
    val title: String,
    val body: String
)

data class StudyResource(
    val title: String,
    val summary: String,
    val url: String? = null
)

data class Subject(
    val id: String,
    val group: String,
    val name: String,
    val description: String,
    val questions: List<QuizQuestion>,
    val cards: List<StudyCard>,
    val resources: List<StudyResource>
)

data class ScoreEntry(
    val playerName: String,
    val subjectName: String,
    val correct: Int,
    val total: Int
) {
    val percent: Int
        get() = if (total == 0) 0 else (correct * 100 / total)
}
