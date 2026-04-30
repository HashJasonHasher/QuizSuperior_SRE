package com.example.quizsuperior_sre

object QuizRepository {
    fun sampleSubjects(): List<Subject> = listOf(
        subject(
            id = "java1",
            group = "College",
            name = "Java Prog 1",
            description = "Primitive types, loops, classes, and basic program structure.",
            questions = listOf(
                q(
                    "Which of the following is NOT a primitive data type?",
                    listOf("int", "String", "char", "boolean"),
                    1,
                    "String is a class, not a primitive type."
                ),
                q(
                    "Which loop is best when the number of iterations is known?",
                    listOf("for", "while", "do-while", "switch"),
                    0,
                    "A for-loop is commonly used when the count is known ahead of time."
                ),
                q(
                    "What does the new keyword do?",
                    listOf("Declares a variable", "Creates an object", "Imports a package", "Ends a method"),
                    1,
                    "new creates a new object instance."
                ),
                q(
                    "Which statement prints a line in Java?",
                    listOf("printLine()", "console.log()", "System.out.println()", "printfline()"),
                    2,
                    "System.out.println() prints a line of output."
                )
            ),
            cards = listOf(
                c("Primitive Types", "int, double, char, boolean, and other primitives hold basic values directly."),
                c("Objects", "Use new to create objects such as Scanner or ArrayList."),
                c("Output", "System.out.println() is the standard way to print a line in Java.")
            ),
            resources = listOf(
                r("Oracle Java Tutorial", "Official Java tutorial pages for beginners.", "https://docs.oracle.com/javase/tutorial/"),
                r("W3Schools Java", "Quick Java references and examples.", "https://www.w3schools.com/java/"),
                r("Java String Guide", "Refresh how Strings work in Java.", "https://docs.oracle.com/javase/8/docs/api/java/lang/String.html")
            )
        ),
        subject(
            id = "history",
            group = "High School",
            name = "History",
            description = "Important dates, events, and people in world history.",
            questions = listOf(
                q(
                    "Which ancient civilization built the pyramids of Giza?",
                    listOf("Rome", "Egypt", "Greece", "Persia"),
                    1,
                    "The pyramids of Giza were built in ancient Egypt."
                ),
                q(
                    "World War II ended in which year?",
                    listOf("1939", "1941", "1945", "1950"),
                    2,
                    "World War II ended in 1945."
                ),
                q(
                    "The Renaissance began in which region of Europe?",
                    listOf("Italy", "Germany", "England", "Russia"),
                    0,
                    "The Renaissance is commonly associated with Italy."
                ),
                q(
                    "Who was the first president of the United States?",
                    listOf("Abraham Lincoln", "George Washington", "Thomas Jefferson", "John Adams"),
                    1,
                    "George Washington was the first U.S. president."
                )
            ),
            cards = listOf(
                c("Timeline", "Use a simple timeline to link events, causes, and outcomes."),
                c("People", "Focus on names, roles, and why a person mattered historically."),
                c("Dates", "Associate big events with key years for faster recall.")
            ),
            resources = listOf(
                r("Britannica History", "Short reference entries on major historical topics.", "https://www.britannica.com/topic/history"),
                r("History.com Topics", "Popular overviews of major events and figures.", "https://www.history.com/topics"),
                r("Crash Course History", "Fast review videos for broad historical topics.", "https://www.youtube.com/@crashcourse")
            )
        ),
        subject(
            id = "trig",
            group = "High School",
            name = "Trigonometry",
            description = "Right-triangle ratios, identities, and basic trig conversions.",
            questions = listOf(
                q(
                    "Which ratio equals sine?",
                    listOf("opposite / hypotenuse", "adjacent / hypotenuse", "opposite / adjacent", "hypotenuse / opposite"),
                    0,
                    "Sine is opposite over hypotenuse."
                ),
                q(
                    "Which identity is always true?",
                    listOf("sin = 1 / csc", "cos = 1 / tan", "tan = 1 / sin", "sec = 1 / cot"),
                    0,
                    "sin and csc are reciprocals."
                ),
                q(
                    "What is tangent?",
                    listOf("sin / cos", "cos / sin", "opposite / adjacent", "adjacent / hypotenuse"),
                    2,
                    "Tangent is opposite over adjacent."
                ),
                q(
                    "What is secant the reciprocal of?",
                    listOf("sine", "cosine", "tangent", "cotangent"),
                    1,
                    "Secant is the reciprocal of cosine."
                )
            ),
            cards = listOf(
                c("SOH CAH TOA", "Sine = Opposite / Hypotenuse, Cosine = Adjacent / Hypotenuse, Tangent = Opposite / Adjacent."),
                c("Reciprocals", "csc, sec, and cot are reciprocal trig functions."),
                c("Quick Check", "Remember: tan = sin / cos and cos = 1 / sec.")
            ),
            resources = listOf(
                r("Khan Academy Trigonometry", "Fast refresher lessons and practice problems.", "https://www.khanacademy.org/math/trigonometry"),
                r("Trig Identities", "Common trig identities in one place.", "https://www.mathsisfun.com/algebra/trig-identities.html"),
                r("Unit Circle Guide", "A quick unit-circle review page.", "https://www.mathopenref.com/unitcircle.html")
            )
        ),
        subject(
            id = "physics",
            group = "High School",
            name = "Physics",
            description = "Motion, force, energy, and basic formulas.",
            questions = listOf(
                q(
                    "What is the formula for force?",
                    listOf("F = m + a", "F = m / a", "F = m x a", "F = a / m"),
                    2,
                    "Force equals mass times acceleration."
                ),
                q(
                    "Which unit measures energy?",
                    listOf("Newton", "Joule", "Watt", "Pascal"),
                    1,
                    "Energy is measured in joules."
                ),
                q(
                    "What does velocity include?",
                    listOf("Distance only", "Speed and direction", "Mass and time", "Force and energy"),
                    1,
                    "Velocity is speed with direction."
                ),
                q(
                    "Which quantity is a vector?",
                    listOf("Temperature", "Distance", "Speed", "Acceleration"),
                    3,
                    "Acceleration has both magnitude and direction, so it is a vector."
                )
            ),
            cards = listOf(
                c("Newton's Second Law", "Force equals mass times acceleration: F = ma."),
                c("Energy", "Energy is measured in joules and often appears as kinetic or potential energy."),
                c("Velocity", "Velocity includes direction, while speed does not."),
            ),
            resources = listOf(
                r("Khan Academy Physics", "Short physics lessons and practice.", "https://www.khanacademy.org/science/physics"),
                r("Physics Classroom", "Concept explanations and example problems.", "https://www.physicsclassroom.com/"),
                r("HyperPhysics", "Compact reference for many physics topics.", "http://hyperphysics.phy-astr.gsu.edu/")
            )
        ),
        subject(
            id = "calc1",
            group = "College",
            name = "Calculus 1",
            description = "Limits, derivatives, and basic integral ideas.",
            questions = listOf(
                q(
                    "What is the derivative of x^2?",
                    listOf("x", "2x", "x^3", "2"),
                    1,
                    "By the power rule, the derivative of x^2 is 2x."
                ),
                q(
                    "The limit of a function describes what happens as x ___.",
                    listOf("goes to infinity only", "approaches a value", "becomes undefined", "gets squared"),
                    1,
                    "A limit studies the value approached by a function."
                ),
                q(
                    "Which rule is used to differentiate a product of two functions?",
                    listOf("Chain rule", "Product rule", "Quotient rule", "Power rule"),
                    1,
                    "The product rule is used for f(x)g(x)."
                ),
                q(
                    "An integral is most closely related to ___.",
                    listOf("adding areas", "finding slopes", "subtracting roots", "sorting data"),
                    0,
                    "Integrals are often used to find accumulated area."
                )
            ),
            cards = listOf(
                c("Power Rule", "d/dx of x^n = n x^(n-1)."),
                c("Limits", "Look for the value the function approaches, not just the value at a point."),
                c("Integrals", "Think of accumulation and area under a curve.")
            ),
            resources = listOf(
                r("Paul's Online Math Notes", "Fast calculus explanations with examples.", "https://tutorial.math.lamar.edu/Classes/CalcI/CalcI.aspx"),
                r("Khan Academy Calculus", "Practice for limits and derivatives.", "https://www.khanacademy.org/math/calculus-1"),
                r("Derivative Rules", "Quick reference page for common derivative rules.", "https://www.mathsisfun.com/calculus/derivatives-rules.html")
            )
        ),
        subject(
            id = "python1",
            group = "College",
            name = "Python 1",
            description = "Basic Python syntax, variables, lists, and loops.",
            questions = listOf(
                q(
                    "Which symbol starts a comment in Python?",
                    listOf("//", "#", "/*", "--"),
                    1,
                    "Python comments start with #."
                ),
                q(
                    "What does len([1, 2, 3]) return?",
                    listOf("2", "3", "4", "6"),
                    1,
                    "The list has three elements."
                ),
                q(
                    "Which structure repeats code while a condition is true?",
                    listOf("for loop", "while loop", "if statement", "try block"),
                    1,
                    "A while loop repeats while a condition remains true."
                ),
                q(
                    "Which type is mutable?",
                    listOf("tuple", "list", "str", "int"),
                    1,
                    "Lists are mutable, meaning they can be changed."
                )
            ),
            cards = listOf(
                c("Lists", "Lists are ordered and mutable collections."),
                c("Loops", "Use while when a condition controls repetition."),
                c("Comments", "A # symbol turns text into a comment in Python.")
            ),
            resources = listOf(
                r("Python Tutorial", "Official Python tutorial pages.", "https://docs.python.org/3/tutorial/"),
                r("W3Schools Python", "Simple examples for syntax practice.", "https://www.w3schools.com/python/"),
                r("Python Lists", "Review how list methods work.", "https://docs.python.org/3/tutorial/datastructures.html")
            )
        ),
        subject(
            id = "softwareeng",
            group = "College",
            name = "Software Eng.",
            description = "Requirements, design, UML, testing, and project planning.",
            questions = listOf(
                q(
                    "What does UML stand for?",
                    listOf("Universal Modeling Language", "Unified Modeling Language", "User Module Layout", "Unified Machine Logic"),
                    1,
                    "UML stands for Unified Modeling Language."
                ),
                q(
                    "Which document usually describes what the system should do?",
                    listOf("Requirements specification", "Source code", "Build script", "Screenshot"),
                    0,
                    "Requirements specify expected behavior."
                ),
                q(
                    "What is the purpose of unit testing?",
                    listOf("Make UI prettier", "Check small pieces of code", "Replace documentation", "Rename variables"),
                    1,
                    "Unit tests focus on small pieces of code."
                ),
                q(
                    "Which is an example of an alternative flow?",
                    listOf("Happy path only", "User input invalid", "No diagram needed", "Final release"),
                    1,
                    "Invalid input is a common alternative flow."
                )
            ),
            cards = listOf(
                c("Requirements", "Describe what the system must do and what the user needs."),
                c("UML", "Use diagrams to model classes, sequences, and state changes."),
                c("Testing", "Validate that the app behaves the way the use cases describe.")
            ),
            resources = listOf(
                r("UML Guide", "Quick explanations of class and sequence diagrams.", "https://www.uml-diagrams.org/"),
                r("Software Engineering Basics", "A broad review of software engineering ideas.", "https://www.geeksforgeeks.org/software-engineering/"),
                r("Requirements Analysis", "Notes about writing useful system requirements.", "https://www.techtarget.com/searchcio/definition/requirements-analysis")
            )
        )
    )

    private fun subject(
        id: String,
        group: String,
        name: String,
        description: String,
        questions: List<QuizQuestion>,
        cards: List<StudyCard>,
        resources: List<StudyResource>
    ) = Subject(id, group, name, description, questions, cards, resources)

    private fun q(
        prompt: String,
        options: List<String>,
        correctIndex: Int,
        explanation: String
    ) = QuizQuestion(prompt, options, correctIndex, explanation)

    private fun c(title: String, body: String) = StudyCard(title, body)

    private fun r(title: String, summary: String, url: String? = null) = StudyResource(title, summary, url)
}
