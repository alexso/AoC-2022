import java.io.File

fun main(args: Array<String>) {
    solutionPart1()
    solutionPart2()
}

private fun solutionPart1() {
    var commonItems = ""
    File("inputData/Input3.txt").forEachLine { rucksack ->
        val compartment1 = rucksack.take(rucksack.length/2)
        val compartment2 = rucksack.drop(rucksack.length/2)
        var foundMatch = false
        compartment1.forEach {
            if (compartment2.contains(it)) {
                if (!foundMatch) {
                    commonItems += it
                }
                foundMatch =true
            }
        }
    }
    var prioritySum = 0
    println("Total common items: ${commonItems.length}")
    commonItems.forEach {
        prioritySum += getPriorityFor(it)
    }

    println("Total sum: $prioritySum")
}

private fun solutionPart2() {
    var commonItems = ""
    var readRows = 0
    var line1 = ""
    var line2 = ""
    var line3 = ""
    File("inputData/Input3.txt").forEachLine { rucksack ->
        when (readRows) {
            0 -> line1 =rucksack
            1 -> line2 = rucksack
            2 -> {
                line3 = rucksack
                commonItems += findCommonItemFor(line1, line2, line3)
            }
        }
        readRows++
        if (readRows > 2) readRows = 0
    }
    var prioritySum = 0
    println("Total common items: ${commonItems.length}")
    commonItems.forEach {
        prioritySum += getPriorityFor(it)
    }

    println("Total badge sum: $prioritySum")
}

fun findCommonItemFor(line1: String, line2: String, line3: String): Char {
    line1.forEach {
        if (line2.contains(it) && line3.contains(it)) return it
    }
    println("Something is wrong")
    return '-'
}

private fun getPriorityFor(char: Char): Int {
    val priority = if (char.isUpperCase()) char.code - 65 + 27 else char.code - 97 + 1
    // println("$char has priority: $priority")
    return priority
}