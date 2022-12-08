import java.io.File

fun main(args: Array<String>) {
    solutionPart1()
    solutionPart2()
}

private fun solutionPart1() {
    val trees = mutableListOf<MutableList<Int>>()
    val grid = mutableListOf<MutableList<Int>>()
    File("inputData/Input8.txt").forEachLine { text ->
        val row = mutableListOf<Int>()
        val  treeRow = mutableListOf<Int>()
        text.forEach {
            row.add(it.digitToInt())
            treeRow.add(0)
        }
        grid.add(row)

        trees.add(treeRow)
    }
    var count = 0
    grid.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, tree ->
            if (isTreeVisible(grid, tree, rowIndex, columnIndex)) {
                count++
                trees[rowIndex][columnIndex] = 1
            }
        }
    }

    // To see result for each tree
//    trees.forEach { row ->
//        row.forEach { print(it) }
//        println()
//    }

    println("Part 1. Result is $count")
}

private fun isTreeVisible(grid: MutableList<MutableList<Int>>, tree: Int, rowIndex: Int, columnIndex: Int): Boolean {
    val numberOfColumns = grid[0].count()
    val numberOfRows = grid.count()
    val isTreeOnEdge = rowIndex == 0 || columnIndex == 0 || rowIndex == numberOfRows -1 || rowIndex == numberOfColumns -1
    if (isTreeOnEdge) return true
    var isSeenFromLeft = true
    var isSeenFromRight = true
    var isSeenFromTop = true
    var isSeenFromBottom = true
    for (i in 0 until columnIndex) {
        if(grid[rowIndex][i] >= tree) {
            isSeenFromLeft = false
        }
    }
    if (isSeenFromLeft) return true
    for (i in columnIndex+1 until numberOfColumns) {
        if(grid[rowIndex][i] >= tree) {
            isSeenFromRight = false
        }
    }
    if (isSeenFromRight) return true

    for (i in 0 until rowIndex) {
        if(grid[i][columnIndex] >= tree) {
            isSeenFromTop = false
        }
    }

    if (isSeenFromTop) return true

    for (i in rowIndex +1 until numberOfColumns) {
        if(grid[i][columnIndex] >= tree) {
            isSeenFromBottom = false
        }
    }

    return isSeenFromBottom
}


private fun solutionPart2() {
    val trees = mutableListOf<MutableList<Int>>()
    val grid = mutableListOf<MutableList<Int>>()
    File("inputData/Input8.txt").forEachLine { text ->
        val row = mutableListOf<Int>()
        val  treeRow = mutableListOf<Int>()
        text.forEach {
            row.add(it.digitToInt())
            treeRow.add(0)
        }
        grid.add(row)

        trees.add(treeRow)
    }
    var maxScore = 0
    grid.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, tree ->
            val score = getTreeScore(grid, tree, rowIndex, columnIndex)
            if (score > maxScore) maxScore = score
            trees[rowIndex][columnIndex] = score
        }
    }

    // To see result for each tree
//    trees.forEach { row ->
//        row.forEach { print(it) }
//        println()
//    }

    println("Part 2. Result is $maxScore")
}

private fun getTreeScore(grid: MutableList<MutableList<Int>>, tree: Int, rowIndex: Int, columnIndex: Int): Int {
    val numberOfColumns = grid[0].count()
    val numberOfRows = grid.count()
    var leftScore = 0
    var rightScore = 0
    var topScore = 0
    var bottomScore = 0
    for (i in columnIndex -1  downTo  0) {
        leftScore++
        if(grid[rowIndex][i] >= tree) {
            break
        }
    }

    for (i in columnIndex+1 until numberOfColumns) {
        rightScore++
        if(grid[rowIndex][i] >= tree) {
            break
        }
    }

    for (i in rowIndex-1 downTo  0) {
        topScore++
        if(grid[i][columnIndex] >= tree) {
            break
        }
    }

    for (i in rowIndex +1 until numberOfColumns) {
        bottomScore++
        if(grid[i][columnIndex] >= tree) {
            break
        }
    }

    return leftScore*rightScore*topScore*bottomScore
}
