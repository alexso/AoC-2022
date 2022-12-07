import java.io.File

fun main(args: Array<String>) {
    solutionPart1()
    solutionPart2()
}

private fun solutionPart1() {
    val listOfCrates = mutableListOf<MutableList<String>>()

    File("inputData/Input5.txt").forEachLine { text ->
        when {
            text.trim().startsWith("[") -> addCrates(text, listOfCrates)
            text.trim().startsWith("1") -> sortCrates(listOfCrates)
            text.trim().startsWith("move") -> moveCrate(text, listOfCrates)
            else -> {}
        }
    }

    var resultString = ""
    listOfCrates.forEach{ resultString += it.last()}

    println("The string is: $resultString")

}

private fun solutionPart2() {
    val listOfCrates = mutableListOf<MutableList<String>>()

    File("inputData/Input5.txt").forEachLine { text ->
        when {
            text.trim().startsWith("[") -> addCrates(text, listOfCrates)
            text.trim().startsWith("1") -> sortCrates(listOfCrates)
            text.trim().startsWith("move") -> moveCrateVersion2(text, listOfCrates)
            else -> {}
        }
    }

    var resultString = ""
    listOfCrates.forEach{ resultString += it.last()}

    println("For second model, the string is: $resultString")

}

private fun moveCrateVersion2(text: String, listOfCrates: MutableList<MutableList<String>>) {
    val moveText = text.split(" ")
    val cratesToMove = moveText[1].toInt()
    val from = moveText[3].toInt() -1
    val to = moveText[5].toInt() -1
    for (i in cratesToMove downTo 1) {
        val indexToMove = listOfCrates[from].count() - i
        listOfCrates[to].add(listOfCrates[from][indexToMove])
        listOfCrates[from].removeAt(indexToMove)
    }
}

private fun moveCrate(text: String, listOfCrates: MutableList<MutableList<String>>) {
    val moveText = text.split(" ")
    val cratesToMove = moveText[1].toInt()
    val from = moveText[3].toInt() -1
    val to = moveText[5].toInt() -1
    for (i in 1 .. cratesToMove) {
        listOfCrates[to].add(listOfCrates[from].last())
        listOfCrates[from].removeLast()
    }
}

private fun sortCrates(listOfCrates: MutableList<MutableList<String>>) {
    listOfCrates.forEach {
        it.reverse()
        it.removeAll { it.isEmpty() }
    }
}

private fun addCrates(text: String, listOfCrates: MutableList<MutableList<String>>) {
    var modifiedText = " $text"
    var index =0
    while(true) {
        if (modifiedText.isEmpty()) {
            break
        }
        val crate = modifiedText.take(4)
        modifiedText = modifiedText.drop(4)
        val formatted = crate.trim(' ', '[', ']')
        if (listOfCrates.count() == index) {
            listOfCrates.add(mutableListOf(formatted))
        } else {
            listOfCrates[index].add(formatted)
        }
        index++
    }
}
