# Quiz Superior Prototype Source

This bundle contains the Kotlin source files for the Quiz Superior prototype.

## Files
- `MainActivity.kt` - app navigation and Compose screens
- `QuizModels.kt` - data classes
- `QuizRepository.kt` - sample subjects, cards, resources, and questions

## How to use in Android Studio
1. Copy the files in `app/src/main/java/com/example/quizsuperior_sre/` into your Android Studio project with the same package name.
2. Keep your existing `QuizSuperior_SRETheme` files.
3. Make sure your project uses Jetpack Compose and Material 3.
4. Run the app on an emulator or device.

## Implemented screens
- Title screen
- Subject selection screen
- Quiz screen with timer, scoring, and wrong-answer feedback
- Study cards screen
- Resources screen with in-app search and browser links
- Leaderboard screen
- Settings dialog

## Error handling included
- User must select a subject before starting quiz/cards/resources
- Search filters can return no results
- Quiz handles unanswered questions
- Quiz auto-finishes when the timer runs out
