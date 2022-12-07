import java.io.File
import java.util.*

fun main(args: Array<String>) {
    solution()
}

private fun solution() {
    val directories = mutableMapOf<String, Long>()
    var dirStack = mutableListOf<String>()
    File("inputData/Input7.txt").forEachLine { text ->
//        if (text.startsWith("dir")) {
//            val directoryRow = text.split(" ")
//            dirStack.add(directoryRow[1])
//        }
        if (text.startsWith("$ cd /")) {
            dirStack.add("root")
        }
        else if (text.startsWith("$ cd ..")) {
            dirStack = dirStack.dropLast(1).toMutableList()
        } else if (text.startsWith("$ cd ")) {
            val goIntoDirectory = text.split(" ")
            dirStack.add(goIntoDirectory[2] + " " + UUID.randomUUID().toString())
        } else if (text[0].isDigit()) {
            val fileRow = text.split(" ")
            dirStack.forEach{
                directories[it] = (directories[it] ?: 0) + fileRow[0].toLong()
            }
        }
    }
    var count = 0L
    directories.forEach { (key, value) -> if (value <= 100000L) count += value}
    println("Part1. Sum is: $count")

    val usedSpace = directories["root"] ?: 0L
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
