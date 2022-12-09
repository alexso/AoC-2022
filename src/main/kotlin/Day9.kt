import java.io.File

fun main(args: Array<String>) {
    solutionPart1()
    solutionPart2()
}

private var isDebug = false

private val shouldPrintMap = isDebug
private val seeVisitedPlaces = false
private var arraySize = if (isDebug) 50 else 1000
private val inputFile = if (isDebug) "inputData/Input9Example.txt" else "inputData/Input9.txt"

private val array = Array(arraySize) { CharArray(arraySize) }
private var head = Pair(array.size/2,array.size/2)
private var tail = Pair(array.size/2,array.size/2)
private var tails = mutableListOf<Pair<Int,Int>>()
private var count = 0
private var tailLength = 0

private val visitedSpots = mutableSetOf<Pair<Int,Int>>()

private fun solutionPart1() {
    clearMap()
    visitedSpots.clear()
    tailLength = 1
    array[tail.first][tail.second] = 'T'
    for (i in 0 until tailLength) {
        tails.add(head)
    }
    File(inputFile).forEachLine { text ->
        val move = text.split(" ")
        when (move[0]){
            "R" -> moveHeadRight(move[1].toInt())
            "L" -> moveHeadLeft(move[1].toInt())
            "D" -> moveHeadDown(move[1].toInt())
            "U"-> moveHeadUp(move[1].toInt())
        }
        // printMap()
    }
    println("Part 1. Number of visited places: ${visitedSpots.size}")
}

private fun solutionPart2() {
    clearMap()
    visitedSpots.clear()
    tailLength = 9
    for (i in 0 until tailLength) {
        tails.add(head)
    }

    File(inputFile).forEachLine { text ->
        val move = text.split(" ")
        when (move[0]){
            "R" -> moveHeadRight(move[1].toInt())
            "L" -> moveHeadLeft(move[1].toInt())
            "D" -> moveHeadDown(move[1].toInt())
            "U"-> moveHeadUp(move[1].toInt())
        }
        // printMap()
    }

    if (seeVisitedPlaces) {
        clearMap()
        visitedSpots.forEach { position ->
            array[position.second][position.first] = '#'
        }
        printMap(true)
    }

    println("Part 2. Number of visited places: ${visitedSpots.size}")
}

private fun printMap(forcePrint: Boolean = false) {
    if (shouldPrintMap || forcePrint) {
        array.forEachIndexed { columnIndex, row ->
            row.forEachIndexed { rowIndex, value ->
                if (rowIndex == head.first && columnIndex == head.second) {
                    print('H')
                } else {
                    print(value)
                }
            }
            println()
        }
        println()
    }
}

private fun clearMap() {
    array.forEachIndexed() { rowIndex, row ->
        row.forEachIndexed { columnIndex, value ->
            array[rowIndex][columnIndex] = '.'
        }
    }
}

private fun moveHeadRight(steps: Int) {
    for (i in 0 until steps) {
        head = Pair(head.first+1, head.second)
        moveTail()
    }
}

private fun moveHeadLeft(steps: Int) {
    for (i in 0 until steps) {
        head = Pair(head.first-1, head.second)
        moveTail()
    }
}

private fun moveHeadUp(steps: Int) {
    for (i in 0 until steps) {
        head = Pair(head.first, head.second-1)
        moveTail()
    }
}

private fun moveHeadDown(steps: Int) {
    for (i in 0 until steps) {
        head = Pair(head.first, head.second+1)
        moveTail()
    }
}

private fun isAdjacent(first: Pair<Int,Int>, second: Pair<Int,Int>): Boolean {
    var isAdjacent = true
    if (first.first < second.first -1 || first.first > second.first +1) {
        isAdjacent = false
    }
    else if (first.second < second.second -1 || first.second > second.second +1) {
        isAdjacent = false
    }
    return isAdjacent
}

private fun moveTail(first: Pair<Int,Int>, second: Pair<Int,Int>, index: Int) {
    val isAdjacent = isAdjacent(first, second)
    var moveHorizontal = false
    var moveVertical = false
    if (first.first < second.first -1 || first.first > second.first +1) {
        moveHorizontal = true
    }
    if (first.second < second.second -1 || first.second > second.second +1) {
        moveVertical = true
    }
    if (moveVertical && first.first != second.first) {
        moveHorizontal = true
    }

    if (moveHorizontal && first.second != second.second) {
        moveVertical = true
    }

    var newFirst = second.first
    var newSecond = second.second
    if (!isAdjacent) {
        if (moveHorizontal) {
            if (first.first > second.first) {
                newFirst++
            } else if (first.first < second.first) {
                newFirst--
            }
        }
        if (moveVertical) {
            if (first.second > second.second) {
                newSecond++
            } else if (first.second < second.second) {
                newSecond--
            }
        }
        tails[index] = Pair(newFirst, newSecond)
    }

    if (array[newSecond][newFirst] == '.') array[newSecond][newFirst] = (index +1).toString()[0]

}

private fun moveTail() {
    printMap()
    clearMap()
    tails.forEachIndexed { index, currentTail ->
        val first = if (index == 0) head else tails[index-1]
        val second = currentTail
        moveTail(first, second, index)
    }
    printMap()
    visitedSpots.add(tails[tailLength-1])
}
