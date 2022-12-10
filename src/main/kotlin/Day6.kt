import java.io.File

fun main(args: Array<String>) {
    solutionPart1()
    solutionPart2()
}

private fun solutionPart1() {
    val inputData = File("inputData/Input6.txt").readText()
    println("Part 1 - needed length is: ${inputData.charsNeededToFirstUniqueSequence(4)}")
}
private fun solutionPart2() {
    val inputData = File("inputData/Input6.txt").readText()
    println("Part 2 - needed length is: ${inputData.charsNeededToFirstUniqueSequence(14)}")
}

private fun String.charsNeededToFirstUniqueSequence(uniqueLength: Int): Int {
    var newString: String = ""
    this.forEach {
        newString += it
        val reversedString = newString.reversed().take(uniqueLength)
        if (reversedString.count() >= uniqueLength) {
            if (reversedString.allUnique()) {
                return newString.count()
            }
        }
    }
    return newString.count()
}

private fun String.allUnique(): Boolean {
    val set = mutableSetOf<Char>()
    set.addAll(this.asSequence())
    return set.size == this.length
}
