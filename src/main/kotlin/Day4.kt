import java.io.File

fun main(args: Array<String>) {
    solutionPart1()
    solutionPart2()
}

private fun solutionPart1() {
    var count = 0
    File("inputData/Input4.txt").forEachLine { pair ->
        val elves = pair.split(",")
        val firstElf = elves[0].split("-")
        val secondElf = elves[1].split("-")
        if ( (firstElf[0].toInt() <= secondElf[0].toInt() && firstElf[1].toInt() >= secondElf[1].toInt()) ||
            (secondElf[0].toInt() <= firstElf[0].toInt() && secondElf[1].toInt() >= firstElf[1].toInt())
                ) {
            count++
        }
    }
    println("Number of elves that totally overlaps: $count")
}

private fun solutionPart2() {
    var count = 0
    File("inputData/Input4.txt").forEachLine { pair ->
        val elves = pair.split(",")
        val firstElf = elves[0].split("-")
        val secondElf = elves[1].split("-")
        if (firstElf[0].toInt() <= secondElf[1].toInt() && firstElf[1].toInt() >= secondElf[0].toInt()) {
            count++
        }
    }
    println("Number of elves that overlaps in some part: $count")
}

