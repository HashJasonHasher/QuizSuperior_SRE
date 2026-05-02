package com.example.quizsuperior_sre

object QuizRepository {
    fun sampleSubjects(): List<Subject> = listOf(
        subject(
            id = "java1",
            group = "College",
            name = "Java Prog 1",
            description = "Primitive types, loops, classes, and basic program structure.",
            questions = listOf(
                q("Which of the following is NOT a primitive data type?", listOf("int", "String", "char", "boolean"), 1, "String is a class, not a primitive type."),
                q("Which loop is best when the number of iterations is known?", listOf("for", "while", "do-while", "switch"), 0, "A for-loop is commonly used when the count is known ahead of time."),
                q("What does the new keyword do?", listOf("Declares a variable", "Creates an object", "Imports a package", "Ends a method"), 1, "new creates a new object instance."),
                q("Which statement prints a line in Java?", listOf("printLine()", "console.log()", "System.out.println()", "printfline()"), 2, "System.out.println() prints a line of output."),
                q("Which Java type stores true or false values?", listOf("int", "boolean", "char", "double"), 1, "boolean stores either true or false."),
                q("Which symbol ends most Java statements?", listOf(":", ";", ".", ","), 1, "Most Java statements end with a semicolon."),
                q("Which keyword creates a class in Java?", listOf("class", "define", "struct", "object"), 0, "The class keyword is used to define a class."),
                q("Which operator checks if two values are equal?", listOf("=", "==", "!=", "<>"), 1, "== compares two values for equality."),
                q("Which method is the starting point of many Java programs?", listOf("start()", "main()", "run()", "init()"), 1, "The main method is commonly the entry point of a Java program."),
                q("Which collection can grow or shrink in size?", listOf("int", "array", "ArrayList", "char"), 2, "ArrayList is a resizable collection.")
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
                q("Which ancient civilization built the pyramids of Giza?", listOf("Rome", "Egypt", "Greece", "Persia"), 1, "The pyramids of Giza were built in ancient Egypt."),
                q("World War II ended in which year?", listOf("1939", "1941", "1945", "1950"), 2, "World War II ended in 1945."),
                q("The Renaissance began in which region of Europe?", listOf("Italy", "Germany", "England", "Russia"), 0, "The Renaissance is commonly associated with Italy."),
                q("Who was the first president of the United States?", listOf("Abraham Lincoln", "George Washington", "Thomas Jefferson", "John Adams"), 1, "George Washington was the first U.S. president."),
                q("Which empire was ruled by Julius Caesar?", listOf("Roman", "Ottoman", "Mongol", "Aztec"), 0, "Julius Caesar was a leader of Rome."),
                q("The Declaration of Independence was adopted in what year?", listOf("1492", "1776", "1812", "1865"), 1, "The Declaration of Independence was adopted in 1776."),
                q("Who led the Mongol Empire during its major expansion?", listOf("Genghis Khan", "Napoleon", "Alexander the Great", "Charlemagne"), 0, "Genghis Khan founded and expanded the Mongol Empire."),
                q("Which war was fought between the North and South in the United States?", listOf("Revolutionary War", "Civil War", "World War I", "War of 1812"), 1, "The U.S. Civil War was fought between the Union and Confederacy."),
                q("Which civilization is associated with democracy in Athens?", listOf("Ancient Greece", "Ancient Egypt", "Ancient China", "Ancient Persia"), 0, "Athens in Ancient Greece is strongly associated with early democracy."),
                q("The Cold War was mainly between the United States and which country?", listOf("France", "Soviet Union", "Japan", "Mexico"), 1, "The Cold War was mainly between the United States and the Soviet Union.")
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
                q("Which ratio equals sine?", listOf("opposite / hypotenuse", "adjacent / hypotenuse", "opposite / adjacent", "hypotenuse / opposite"), 0, "Sine is opposite over hypotenuse."),
                q("Which identity is always true?", listOf("sin = 1 / csc", "cos = 1 / tan", "tan = 1 / sin", "sec = 1 / cot"), 0, "sin and csc are reciprocals."),
                q("What is tangent?", listOf("sin / cos", "cos / sin", "opposite / adjacent", "adjacent / hypotenuse"), 2, "Tangent is opposite over adjacent."),
                q("What is secant the reciprocal of?", listOf("sine", "cosine", "tangent", "cotangent"), 1, "Secant is the reciprocal of cosine."),
                q("Which ratio equals cosine?", listOf("opposite / hypotenuse", "adjacent / hypotenuse", "opposite / adjacent", "hypotenuse / adjacent"), 1, "Cosine is adjacent over hypotenuse."),
                q("In SOH CAH TOA, what does TOA stand for?", listOf("Tangent = Opposite / Adjacent", "Tangent = Adjacent / Opposite", "Tangent = Opposite / Hypotenuse", "Tangent = Hypotenuse / Adjacent"), 0, "TOA means Tangent equals Opposite over Adjacent."),
                q("What is cosecant the reciprocal of?", listOf("sine", "cosine", "tangent", "secant"), 0, "Cosecant is the reciprocal of sine."),
                q("What is cotangent the reciprocal of?", listOf("sine", "cosine", "tangent", "secant"), 2, "Cotangent is the reciprocal of tangent."),
                q("Which expression is equal to tan(x)?", listOf("sin(x) / cos(x)", "cos(x) / sin(x)", "1 / sin(x)", "1 / cos(x)"), 0, "Tangent equals sine divided by cosine."),
                q("What is sin(90°)?", listOf("0", "1", "-1", "undefined"), 1, "The sine of 90 degrees is 1.")
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
                q("What is the formula for force?", listOf("F = m + a", "F = m / a", "F = m x a", "F = a / m"), 2, "Force equals mass times acceleration."),
                q("Which unit measures energy?", listOf("Newton", "Joule", "Watt", "Pascal"), 1, "Energy is measured in joules."),
                q("What does velocity include?", listOf("Distance only", "Speed and direction", "Mass and time", "Force and energy"), 1, "Velocity is speed with direction."),
                q("Which quantity is a vector?", listOf("Temperature", "Distance", "Speed", "Acceleration"), 3, "Acceleration has both magnitude and direction, so it is a vector."),
                q("Which unit measures force?", listOf("Joule", "Newton", "Watt", "Meter"), 1, "Force is measured in newtons."),
                q("What is inertia?", listOf("Resistance to changes in motion", "Stored electrical energy", "The speed of light", "The pull of magnets"), 0, "Inertia is an object's resistance to changes in motion."),
                q("What is the formula for kinetic energy?", listOf("KE = mgh", "KE = 1/2 mv^2", "KE = Fd", "KE = ma"), 1, "Kinetic energy equals one half mass times velocity squared."),
                q("Which quantity is measured in meters per second?", listOf("Speed", "Mass", "Force", "Energy"), 0, "Meters per second measures speed or velocity."),
                q("What does gravity do?", listOf("Pushes objects apart", "Attracts objects with mass", "Stops all motion", "Removes friction"), 1, "Gravity is an attractive force between masses."),
                q("Which law says every action has an equal and opposite reaction?", listOf("Newton's First Law", "Newton's Second Law", "Newton's Third Law", "Law of Conservation of Energy"), 2, "Newton's Third Law describes equal and opposite reactions.")
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
                q("What is the derivative of x^2?", listOf("x", "2x", "x^3", "2"), 1, "By the power rule, the derivative of x^2 is 2x."),
                q("The limit of a function describes what happens as x ___.", listOf("goes to infinity only", "approaches a value", "becomes undefined", "gets squared"), 1, "A limit studies the value approached by a function."),
                q("Which rule is used to differentiate a product of two functions?", listOf("Chain rule", "Product rule", "Quotient rule", "Power rule"), 1, "The product rule is used for f(x)g(x)."),
                q("An integral is most closely related to ___.", listOf("adding areas", "finding slopes", "subtracting roots", "sorting data"), 0, "Integrals are often used to find accumulated area."),
                q("What is the derivative of x^3?", listOf("3x^2", "x^2", "3x", "x^4"), 0, "By the power rule, the derivative of x^3 is 3x^2."),
                q("What is the derivative of a constant?", listOf("0", "1", "x", "The constant itself"), 0, "The derivative of a constant is 0."),
                q("Which rule is used for a function inside another function?", listOf("Power rule", "Chain rule", "Product rule", "Sum rule"), 1, "The chain rule is used for composite functions."),
                q("What does a derivative usually represent?", listOf("Area", "Rate of change", "Volume", "Average only"), 1, "A derivative represents an instantaneous rate of change."),
                q("What is the derivative of sin(x)?", listOf("cos(x)", "-cos(x)", "tan(x)", "-sin(x)"), 0, "The derivative of sin(x) is cos(x)."),
                q("What is the integral of 1 with respect to x?", listOf("0", "1", "x + C", "x^2 + C"), 2, "The antiderivative of 1 is x plus a constant.")
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
                q("Which symbol starts a comment in Python?", listOf("//", "#", "/*", "--"), 1, "Python comments start with #."),
                q("What does len([1, 2, 3]) return?", listOf("2", "3", "4", "6"), 1, "The list has three elements."),
                q("Which structure repeats code while a condition is true?", listOf("for loop", "while loop", "if statement", "try block"), 1, "A while loop repeats while a condition remains true."),
                q("Which type is mutable?", listOf("tuple", "list", "str", "int"), 1, "Lists are mutable, meaning they can be changed."),
                q("Which keyword defines a function in Python?", listOf("function", "def", "func", "define"), 1, "Python uses def to define a function."),
                q("What is the output of 2 + 3?", listOf("23", "5", "6", "Error"), 1, "2 + 3 performs numeric addition and returns 5."),
                q("Which data type stores key-value pairs?", listOf("list", "tuple", "dictionary", "string"), 2, "A dictionary stores key-value pairs."),
                q("Which method adds an item to the end of a list?", listOf("append()", "addEnd()", "pushBack()", "insertEnd()"), 0, "append() adds an item to the end of a list."),
                q("Which keyword is used for conditional branching?", listOf("when", "if", "check", "case"), 1, "Python uses if for conditional branching."),
                q("What does range(3) produce when looped over?", listOf("1, 2, 3", "0, 1, 2", "0, 1, 2, 3", "3 only"), 1, "range(3) produces 0, 1, and 2.")
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
                q("What does UML stand for?", listOf("Universal Modeling Language", "Unified Modeling Language", "User Module Layout", "Unified Machine Logic"), 1, "UML stands for Unified Modeling Language."),
                q("Which document usually describes what the system should do?", listOf("Requirements specification", "Source code", "Build script", "Screenshot"), 0, "Requirements specify expected behavior."),
                q("What is the purpose of unit testing?", listOf("Make UI prettier", "Check small pieces of code", "Replace documentation", "Rename variables"), 1, "Unit tests focus on small pieces of code."),
                q("Which is an example of an alternative flow?", listOf("Happy path only", "User input invalid", "No diagram needed", "Final release"), 1, "Invalid input is a common alternative flow."),
                q("Which UML diagram shows classes and relationships?", listOf("Class diagram", "Use case diagram", "State diagram", "Activity log"), 0, "A class diagram models classes and their relationships."),
                q("Which UML diagram shows interactions over time?", listOf("Class diagram", "Sequence diagram", "Package diagram", "Deployment diagram"), 1, "A sequence diagram shows interactions between objects over time."),
                q("What is a use case?", listOf("A user goal or interaction with the system", "A database backup", "A programming language", "A color palette"), 0, "A use case describes how a user interacts with a system to achieve a goal."),
                q("What does debugging mean?", listOf("Writing documentation only", "Finding and fixing errors", "Deleting source code", "Changing the font"), 1, "Debugging means finding and fixing problems in code."),
                q("What is refactoring?", listOf("Changing code structure without changing behavior", "Adding random features", "Deleting all tests", "Skipping design"), 0, "Refactoring improves code structure while preserving behavior."),
                q("What is a requirement?", listOf("A statement of what the system should do", "A type of loop", "A compiler error", "A database row"), 0, "A requirement describes expected system behavior or constraints.")
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
        ),
        subject(
            id = "datastructures",
            group = "Computer Science",
            name = "Data Structures",
            description = "Arrays, lists, stacks, queues, trees, and basic storage concepts.",
            questions = listOf(
                q("Which data structure uses LIFO order?", listOf("Queue", "Stack", "Array", "Tree"), 1, "A stack uses Last In, First Out order."),
                q("Which data structure uses FIFO order?", listOf("Stack", "Queue", "Graph", "Tree"), 1, "A queue uses First In, First Out order."),
                q("What is an array?", listOf("A fixed collection of indexed elements", "A loop type", "A database table", "A sorting algorithm"), 0, "An array stores elements by index."),
                q("Which structure has nodes connected by edges?", listOf("Graph", "Array", "Stack", "String"), 0, "Graphs contain vertices/nodes connected by edges."),
                q("What is the root of a tree?", listOf("The first/top node", "The last leaf", "A duplicate node", "An edge"), 0, "The root is the top node of a tree."),
                q("Which operation adds an item to a stack?", listOf("push", "pop", "peek", "poll"), 0, "Push adds an item to the top of a stack."),
                q("Which operation removes an item from a stack?", listOf("push", "pop", "offer", "enqueue"), 1, "Pop removes the top item from a stack."),
                q("Which data structure is often used for undo features?", listOf("Stack", "Queue", "Set", "Map"), 0, "Undo actions are commonly stored in a stack."),
                q("Which collection stores key-value pairs?", listOf("List", "Map", "Stack", "Queue"), 1, "A map stores keys connected to values."),
                q("Which structure avoids duplicate values?", listOf("Set", "List", "Array", "Queue"), 0, "A set is commonly used to store unique values.")
            ),
            cards = listOf(
                c("Stack", "A stack follows LIFO: Last In, First Out. Common operations are push, pop, and peek."),
                c("Queue", "A queue follows FIFO: First In, First Out. It is useful for waiting lines and task scheduling."),
                c("Trees and Graphs", "Trees organize data hierarchically. Graphs model connections between objects.")
            ),
            resources = listOf(
                r("Oracle Collections Tutorial", "Official Java Collections tutorial covering interfaces, implementations, and algorithms.", "https://docs.oracle.com/javase/tutorial/collections/index.html"),
                r("Oracle Collections Overview", "Overview of Java's unified framework for representing and manipulating collections.", "https://docs.oracle.com/javase/8/docs/technotes/guides/collections/overview.html"),
                r("Java Collections on dev.java", "Modern Java learning material for collections and data storage.", "https://dev.java/learn/api/collections-framework/")
            )
        ),

        subject(
            id = "algorithms",
            group = "Computer Science",
            name = "Algorithms",
            description = "Sorting, searching, Big-O, recursion, and problem-solving steps.",
            questions = listOf(
                q("What is an algorithm?", listOf("A step-by-step solution", "A programming error", "A hardware device", "A database"), 0, "An algorithm is a clear set of steps for solving a problem."),
                q("What does Big-O describe?", listOf("Code color", "Algorithm efficiency", "Screen size", "Variable type"), 1, "Big-O describes how runtime or memory grows as input size increases."),
                q("Which search requires sorted data?", listOf("Linear search", "Binary search", "Random search", "Depth search"), 1, "Binary search works by repeatedly dividing sorted data."),
                q("Which sorting algorithm repeatedly swaps neighboring elements?", listOf("Bubble sort", "Binary search", "Merge sort", "DFS"), 0, "Bubble sort compares and swaps adjacent elements."),
                q("What is recursion?", listOf("A function calling itself", "A loop that never starts", "A database command", "A UI component"), 0, "Recursion happens when a function calls itself."),
                q("Which case stops recursion?", listOf("Base case", "Loop case", "Error case", "Import case"), 0, "A base case prevents recursion from continuing forever."),
                q("Which algorithm idea splits problems into smaller parts?", listOf("Divide and conquer", "Random guessing", "Hard coding", "Compilation"), 0, "Divide and conquer breaks problems into smaller subproblems."),
                q("What is linear search?", listOf("Checking items one by one", "Dividing sorted data", "Sorting numbers", "Hashing passwords"), 0, "Linear search checks each item until it finds a match."),
                q("Which sort is commonly based on divide and conquer?", listOf("Merge sort", "Bubble sort", "Selection sort", "Linear sort"), 0, "Merge sort divides, sorts, and merges parts."),
                q("What usually happens if an algorithm is O(n)?", listOf("Work grows roughly with input size", "It always takes one step", "It never finishes", "It uses no memory"), 0, "O(n) means work grows linearly with input size.")
            ),
            cards = listOf(
                c("Big-O", "Big-O describes growth. O(1) is constant, O(n) is linear, and O(n²) grows much faster."),
                c("Binary Search", "Binary search repeatedly cuts a sorted list in half to find a target value."),
                c("Recursion", "Recursion needs a base case and a recursive case. Without a base case, it may run forever.")
            ),
            resources = listOf(
                r("Khan Academy Algorithms", "Beginner-friendly algorithm lessons covering searching, sorting, and efficiency.", "https://www.khanacademy.org/computing/computer-science/algorithms"),
                r("Khan Academy Recursion", "Explanation of recursive algorithms and base cases.", "https://www.khanacademy.org/computing/computer-science/algorithms/recursive-algorithms/a/recursion"),
                r("AP CSP Algorithms", "Intro algorithms, flowcharts, pseudocode, correctness, and efficiency.", "https://www.khanacademy.org/computing/ap-computer-science-principles/algorithms-101")
            )
        ),

        subject(
            id = "sql",
            group = "Computer Science",
            name = "Databases / SQL",
            description = "Tables, queries, joins, keys, and database basics.",
            questions = listOf(
                q("What does SQL stand for?", listOf("Structured Query Language", "Simple Question Logic", "System Queue List", "Stored Query Loop"), 0, "SQL stands for Structured Query Language."),
                q("Which SQL command retrieves data?", listOf("SELECT", "INSERT", "DELETE", "UPDATE"), 0, "SELECT is used to query and retrieve data."),
                q("Which SQL command adds a new row?", listOf("INSERT", "DROP", "JOIN", "WHERE"), 0, "INSERT adds new data to a table."),
                q("Which clause filters rows?", listOf("WHERE", "ORDER BY", "CREATE", "TABLE"), 0, "WHERE filters rows based on a condition."),
                q("What is a primary key?", listOf("A unique row identifier", "A duplicate column", "A password", "A table backup"), 0, "A primary key uniquely identifies each row."),
                q("What does JOIN do?", listOf("Combines related table data", "Deletes rows", "Creates passwords", "Renames a database"), 0, "JOIN connects rows from related tables."),
                q("Which command changes existing rows?", listOf("UPDATE", "INSERT", "SELECT", "CREATE"), 0, "UPDATE modifies existing data."),
                q("Which command removes rows?", listOf("DELETE", "SELECT", "JOIN", "ALTER"), 0, "DELETE removes rows from a table."),
                q("What is a foreign key?", listOf("A field linking to another table", "A hidden password", "A duplicate database", "A programming loop"), 0, "A foreign key connects one table to another."),
                q("Which keyword sorts query results?", listOf("ORDER BY", "WHERE", "INSERT", "VALUES"), 0, "ORDER BY sorts the returned rows.")
            ),
            cards = listOf(
                c("SELECT", "SELECT retrieves data from one or more tables. WHERE filters the results."),
                c("Keys", "Primary keys uniquely identify rows. Foreign keys connect related tables."),
                c("JOIN", "JOIN is used when data is split across multiple related tables.")
            ),
            resources = listOf(
                r("W3Schools SQL Tutorial", "Beginner-friendly SQL syntax, examples, and practice.", "https://www.w3schools.com/sql/"),
                r("W3Schools MySQL Tutorial", "Intro to MySQL and relational database usage.", "https://www.w3schools.com/MYSQL/default.asp"),
                r("MySQL SQL Basics", "Basic SQL commands used with MySQL databases.", "https://www.w3schools.com/mysql/mysql_sql.asp")
            )
        ),

        subject(
            id = "cybersecurity",
            group = "Computer Science",
            name = "Cybersecurity",
            description = "Passwords, hashing, encryption, malware, risk, and security basics.",
            questions = listOf(
                q("What is phishing?", listOf("A social engineering attack", "A sorting algorithm", "A database backup", "A programming language"), 0, "Phishing tricks users into revealing sensitive information."),
                q("What does encryption do?", listOf("Protects data by making it unreadable without a key", "Deletes files", "Speeds up Wi-Fi", "Creates a database"), 0, "Encryption protects data confidentiality."),
                q("What is hashing commonly used for?", listOf("Password storage and integrity checks", "Drawing UI screens", "Sorting lists only", "Playing audio"), 0, "Hashes are often used to verify passwords and file integrity."),
                q("Which is a strong password practice?", listOf("Use long unique passwords", "Reuse one password", "Use your name only", "Share passwords"), 0, "Long unique passwords reduce account compromise risk."),
                q("What does MFA stand for?", listOf("Multi-Factor Authentication", "Main File Access", "Multiple Folder App", "Manual Firewall Alert"), 0, "MFA requires more than one factor to verify identity."),
                q("What is malware?", listOf("Malicious software", "A safe backup", "A coding style", "A database key"), 0, "Malware is software designed to harm systems or steal data."),
                q("What does a firewall help do?", listOf("Control network traffic", "Write code automatically", "Increase screen brightness", "Format text"), 0, "Firewalls filter network traffic based on rules."),
                q("What is least privilege?", listOf("Only giving users access they need", "Giving everyone admin rights", "Deleting accounts daily", "Using no passwords"), 0, "Least privilege limits access to what is necessary."),
                q("Which security goal protects data from unauthorized viewing?", listOf("Confidentiality", "Availability", "Formatting", "Compilation"), 0, "Confidentiality keeps data private."),
                q("Which security goal keeps systems usable when needed?", listOf("Availability", "Syntax", "Inheritance", "Indexing"), 0, "Availability means systems and data are accessible when needed.")
            ),
            cards = listOf(
                c("CIA Triad", "Confidentiality protects privacy, integrity protects correctness, and availability keeps systems usable."),
                c("Authentication", "Authentication verifies identity. MFA improves security by requiring more than one proof."),
                c("Phishing", "Phishing uses fake messages, links, or websites to trick people into giving up information.")
            ),
            resources = listOf(
                r("NIST Cybersecurity Framework", "Official NIST framework for managing cybersecurity risk.", "https://www.nist.gov/cyberframework"),
                r("NIST CSF 2.0 Small Business", "NIST cybersecurity framework resources focused on small businesses.", "https://www.nist.gov/itl/smallbusinesscyber/nist-cybersecurity-framework-0"),
                r("NIST CSF 2.0 PDF", "Official Cybersecurity Framework 2.0 publication.", "https://nvlpubs.nist.gov/nistpubs/CSWP/NIST.CSWP.29.pdf")
            )
        ),

        subject(
            id = "statistics",
            group = "Mathematics",
            name = "Statistics",
            description = "Mean, median, probability, samples, distributions, and data interpretation.",
            questions = listOf(
                q("What is the mean?", listOf("The average", "The middle value", "The most common value", "The largest value"), 0, "The mean is found by adding values and dividing by the number of values."),
                q("What is the median?", listOf("The middle value", "The average", "The smallest value", "The range"), 0, "The median is the middle value when data is ordered."),
                q("What is the mode?", listOf("The most frequent value", "The average", "The middle value", "The total"), 0, "The mode is the value that appears most often."),
                q("What is range?", listOf("Highest value minus lowest value", "Average of all values", "Middle value", "Most frequent value"), 0, "Range measures spread by subtracting minimum from maximum."),
                q("Probability values usually range from ___.", listOf("0 to 1", "1 to 1000", "-10 to 10", "5 to 50"), 0, "Probability ranges from 0 impossible to 1 certain."),
                q("What is a sample?", listOf("A smaller group selected from a population", "The entire population", "A guaranteed result", "A graph only"), 0, "A sample is a subset used to study a larger population."),
                q("What does a bar graph compare?", listOf("Categories", "Only passwords", "Only source code", "Only maps"), 0, "Bar graphs compare quantities across categories."),
                q("What does standard deviation measure?", listOf("Spread from the mean", "The middle value", "The most common value", "The number of columns"), 0, "Standard deviation measures how spread out values are."),
                q("What is an outlier?", listOf("A value far from most others", "The average", "The exact center", "The first value only"), 0, "Outliers are unusually high or low compared with the rest of the data."),
                q("If an event is impossible, its probability is ___.", listOf("0", "1", "50", "100"), 0, "Impossible events have probability 0.")
            ),
            cards = listOf(
                c("Center", "Mean is the average, median is the middle, and mode is the most frequent value."),
                c("Spread", "Range and standard deviation describe how spread out a data set is."),
                c("Probability", "Probability describes how likely an event is, from 0 impossible to 1 certain.")
            ),
            resources = listOf(
                r("Khan Academy Statistics and Probability", "Intro lessons on probability models, samples, and data.", "https://www.khanacademy.org/kmap/measurement-and-data-h/md224-statistics-and-probability"),
                r("Khan Academy Statistics Videos", "Video lessons covering descriptive statistics and probability.", "https://www.youtube.com/channel/UCRXuOXLW3LcQLWvxbZiIZ0w/featured"),
                r("Statistics: The Average", "Khan Academy video on mean, median, and mode.", "https://www.youtube.com/watch?v=uhxtUt_-GyM")
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
