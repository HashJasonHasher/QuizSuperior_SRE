package com.example.quizsuperior_sre

/**
 * Data model file for Quiz Superior.
 * These data classes define the app's main objects: quiz questions, flashcards, 
 * resources, subjects, and leaderboard scores.
 */

/**
 * Represents one multiple-choice quiz question.
 *
 * @property prompt The text of the question being asked.
 * @property options A list of possible answers for the user to choose from.
 * @property correctIndex The 0-based index of the correct answer within the [options] list.
 * @property explanation A short text shown after answering to explain why the answer is correct or provide more context.
 */
data class QuizQuestion(
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

/**
 * Represents one flashcard used by the Cards screen for quick study.
 *
 * @property title The front or heading of the card (e.g., the concept name).
 * @property body The back or detailed explanation of the card.
 */
data class StudyCard(
    val title: String,
    val body: String
)

/**
 * Represents one outside learning resource shown on the Resources screen.
 *
 * @property title The name of the resource or website.
 * @property summary A brief description of what the user can learn from this resource.
 * @property url The web address for the resource. Nullable if the resource is just a text reference.
 */
data class StudyResource(
    val title: String,
    val summary: String,
    val url: String? = null
)

/**
 * Represents a full quiz topic or subject category.
 * Each subject acts as a container for related questions, flashcards, and resources.
 *
 * @property id A unique string identifier used for navigation and selection logic.
 * @property group The category name (e.g., "College", "High School") used to group subjects in the UI.
 * @property name The display name of the subject.
 * @property description A short summary of what the subject covers.
 * @property questions The list of [QuizQuestion] objects available for this subject.
 * @property cards The list of [StudyCard] objects for the flashcard study mode.
 * @property resources The list of [StudyResource] links for external learning.
 */
data class Subject(
    val id: String,
    val group: String,
    val name: String,
    val description: String,
    val questions: List<QuizQuestion>,
    val cards: List<StudyCard>,
    val resources: List<StudyResource>
)

/**
 * Represents one leaderboard score entry saved by a user after completing a quiz.
 *
 * @property playerName The name entered by the user to identify their score.
 * @property subjectName The name of the subject the user was quizzed on.
 * @property correct The number of questions the user answered correctly.
 * @property total The total number of questions presented in the quiz.
 */
data class ScoreEntry(
    val playerName: String,
    val subjectName: String,
    val correct: Int,
    val total: Int
) {
    /**
     * Computed property that calculates the score percentage.
     * Returns 0 if [total] is zero to avoid division by zero errors.
     */
    val percent: Int
        get() = if (total == 0) 0 else (correct * 100 / total)
}
