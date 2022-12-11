import java.io.File

private val inputFile = "inputData/Input11.txt"

fun main(args: Array<String>) {
    solutionPart1()
    solutionPart2()
}

private fun solutionPart1() {
    val monkeys = readMonkeys()
    for (i in 0 until 20) {
        monkeys.forEach {  monkey ->
            while (monkey.items.isNotEmpty()) {
                var worryLevel = monkey.items.removeFirst()
                worryLevel = doOperation(worryLevel, monkey.operation, monkey.newTerm ?: worryLevel)
                worryLevel /= 3L
                if (worryLevel % monkey.testTerm == 0L) {
                    monkeys[monkey.onTrue].items.add(worryLevel)
                } else {
                    monkeys[monkey.onFalse].items.add(worryLevel)
                }
                monkey.inspectionCount++
            }
        }
    }
    monkeys.sortByDescending { it.inspectionCount }
    val monkey1 = monkeys[0].inspectionCount
    val monkey2 = monkeys[1].inspectionCount
    println("result is : $monkey1 * $monkey2 = ${monkey1 * monkey2}")
}

private fun printMonkeyList(monkeys: MutableList<Monkey>) {
    monkeys.forEachIndexed { index, monkey ->
        println("Monkey $index: ${monkey.items.joinToString()}")
    }
}

private fun printMonkeyInspectionList(monkeys: MutableList<Monkey>) {
    monkeys.forEachIndexed { index, monkey ->
        println("Monkey $index: ${monkey.inspectionCount}")
    }
}

private fun doOperation(worrylevel: Long, operation: Operation, newTerm: Long): Long {
    return when (operation) {
        Operation.ADD -> worrylevel + newTerm
        Operation.SUB -> worrylevel - newTerm
        Operation.MUL -> worrylevel * newTerm
        Operation.DIV -> worrylevel / newTerm
    }
}

private fun solutionPart2() {
    val monkeys = readMonkeys()
    var lcm = 1L
    monkeys.forEach {
        lcm *= it.testTerm
    }
    for (i in 0 until 10000) {
        monkeys.forEach {  monkey ->
            while (monkey.items.isNotEmpty()) {
                var worryLevel = monkey.items.removeFirst()
                worryLevel = doOperation(worryLevel, monkey.operation, monkey.newTerm ?: worryLevel)
                worryLevel %= lcm
                if (worryLevel % monkey.testTerm == 0L) {
                    monkeys[monkey.onTrue].items.add(worryLevel)
                } else {
                    monkeys[monkey.onFalse].items.add(worryLevel)
                }
                monkey.inspectionCount++
            }
        }
    }
    monkeys.sortByDescending { it.inspectionCount }
    val monkey1 = monkeys[0].inspectionCount.toLong()
    val monkey2 = monkeys[1].inspectionCount.toLong()
    println("result is : $monkey1 * $monkey2 = ${monkey1 * monkey2}")
}

private fun readMonkeys(): MutableList<Monkey> {
    val listOfMonkeys = mutableListOf<Monkey>()
    var startItems = mutableListOf<Long>()
    var operation: Operation = Operation.DIV
    var newTerm: Long? = null
    var testTerm = 0L
    var onTrue = -1
    var onFalse = -1
    File(inputFile).forEachLine { text ->
        val formattedText = text.trim()
        when {
            formattedText.startsWith("Monkey") -> {}
            formattedText.startsWith("Starting") -> {
                val importantText = formattedText.removePrefix("Starting items: ")
                val items = importantText.split(", ")
                items.forEach { startItems.add(it.toLong()) }
            }
            formattedText.startsWith("Operation") -> {
                val importantText = formattedText.removePrefix("Operation: new = ")
                val items = importantText.split(" ")
                operation = Operation.parse(items[1])
                newTerm = items[2].toLongOrNull()
            }
            formattedText.startsWith("Test") -> {
                val items = formattedText.split(" ")
                testTerm = items.last().toLong()
            }
            formattedText.startsWith("If true") -> {
                val items = formattedText.split(" ")
                onTrue = items.last().toInt()
            }
            formattedText.startsWith("If false") -> {
                val items = formattedText.split(" ")
                onFalse = items.last().toInt()
                val newStartItems = mutableListOf<Long>()
                newStartItems.addAll(startItems)
                // Everything read for this monkey.
                listOfMonkeys.add(
                    Monkey(
                        newStartItems,
                        operation,
                        newTerm = newTerm,
                        testTerm = testTerm,
                        onTrue = onTrue,
                        onFalse = onFalse
                    )
                )
                startItems.clear()
                operation = Operation.DIV
                newTerm = 0
                testTerm = 0
                onTrue = -1
                onFalse = -1
            }
            else -> {}
        }
    }
    return listOfMonkeys
}

private data class Monkey(
    val items: MutableList<Long> =  mutableListOf(),
    val operation: Operation,
    val newTerm: Long?,
    val testTerm: Long,
    val onTrue: Int,
    val onFalse: Int,
    var inspectionCount: Int = 0
)

private enum class Operation {
    ADD,
    SUB,
    MUL,
    DIV;

    companion object {
        fun parse(value: String): Operation {
            return when (value) {
                "+" -> ADD
                "-" -> SUB
                "*" -> MUL
                "/" -> DIV
                else -> throw Exception("Invalid operation")
            }
        }
    }
}
