import java.io.File

fun main(args: Array<String>) {
    val result = solutionPart1()
    solutionPart2(result)
}

private fun solutionPart1(): MutableMap<String, Long> {
    val directories = mutableMapOf<String, Long>()
    var allPaths = mutableListOf<String>()
    File("inputData/Input7.txt").forEachLine { text ->

        when {
            text.startsWith("$ cd /") -> allPaths.add("/")
            text.startsWith("$ cd ..") -> allPaths = allPaths.dropLast(1).toMutableList()
            text.startsWith("$ cd ") -> {
                val goIntoDirectory = text.split(" ")
                val fullPath = allPaths.last() + goIntoDirectory[2] + "/"
                allPaths.add(fullPath)
            }
            text[0].isDigit() -> {
                val fileRow = text.split(" ")
                allPaths.forEach {
                    directories[it] = (directories[it] ?: 0) + fileRow[0].toLong()
                }
            }
        }
    }
    return directories
}

private fun solutionPart2(directories: MutableMap<String, Long>) {
    var count = 0L
    directories.forEach { (key, value) -> if (value <= 100000L) count += value}
    println("Part1. Sum is: $count")

    val usedSpace = directories["/"] ?: 0L
    val fullSpace = 70000000L
    val neededSpace = 30000000L
    val emptySpace = fullSpace - usedSpace
    val needToDeleteSpace = neededSpace - emptySpace
    var foundSize = fullSpace

    directories.forEach { (key, value) ->
        if (value >= needToDeleteSpace && value <= foundSize) {
            foundSize = value
        }
    }

    println("Part 2. Minimum directory size to fulfill requirement has size: $foundSize")
}
