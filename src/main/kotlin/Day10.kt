import java.io.File

private val inputFile = "inputData/Input10.txt"

fun main(args: Array<String>) {
    solutionPart1()
    println()
    solutionPart2()
}

var cyclesToCheck = listOf(20, 60, 100, 140, 180, 220)

private fun solutionPart1() {
    val signals = mutableListOf<Pair<Pair<Int,Int>,Int>>()
    var cycle = 0
    var registerX = 1
    File(inputFile).forEachLine { text ->
        val instruction = text.split(" ")
        when {
            instruction[0] == "noop" -> {
                cycle++
                check(cycle, registerX, signals)
            }
            instruction[0] == "addx" -> {
                cycle++
                check(cycle, registerX, signals)
                cycle++
                check(cycle, registerX, signals)
                registerX += instruction[1].toInt()
            }
        }

    }
    signals.forEach { println("${it.first.first} * ${it.first.second} = ${it.second}") }
    println("Total signal strength is: ${signals.sumOf{it.second}}")
}

private fun check(cycle: Int, regX: Int, signals: MutableList<Pair<Pair<Int, Int>, Int>>) {

    if (cyclesToCheck.contains(cycle)) {
        val input = Pair(cycle, regX)
        val output = Pair(input, cycle*regX)
        signals.add(output)
    }
}

private fun solutionPart2() {
    println("Solution for part 2")
    var cycle = 0
    var registerX = 1
    File(inputFile).forEachLine { text ->
        val instruction = text.split(" ")
        when {
            instruction[0] == "noop" -> {
                cycle++
                draw(cycle, registerX)
            }
            instruction[0] == "addx" -> {
                cycle++
                draw(cycle, registerX)
                cycle++
                draw(cycle, registerX)
                registerX += instruction[1].toInt()
            }
        }
    }
}

private fun draw(cycle: Int, registerX: Int) {
    if (cycle ==200) {
        print("")
    }
    var horizontalPosition = (cycle %40)
    if (horizontalPosition == 0) horizontalPosition += 40
    if (horizontalPosition >= registerX && horizontalPosition < (registerX +3)) {
        print('#')
    } else {
        print('.')
    }
    if (cycle % 40 == 0) {
        println()
    }
}
