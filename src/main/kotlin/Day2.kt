import java.io.File

fun main(args: Array<String>) {
    var allWinScore = 0
    File("inputData/Input2.txt").forEachLine {
        allWinScore += when {
            it[0] == 'A' && it[2] =='X' -> 3 + 1
            it[0] == 'A' && it[2] =='Y' -> 6 + 2
            it[0] == 'A' && it[2] =='Z' -> 0 + 3

            it[0] == 'B' && it[2] =='X' -> 0 + 1
            it[0] == 'B' && it[2] =='Y' -> 3 + 2
            it[0] == 'B' && it[2] =='Z' -> 6 + 3

            it[0] == 'C' && it[2] =='X' -> 6 + 1
            it[0] == 'C' && it[2] =='Y' -> 0 + 2
            it[0] == 'C' && it[2] =='Z' -> 3 + 3
            else -> 0
        }
    }
    println("Total score when win all: $allWinScore")

    var strategicScore = 0
    File("inputData/Input2.txt").forEachLine {
        strategicScore += when {
            it[0] == 'A' && it[2] =='X' -> 0 + 3
            it[0] == 'A' && it[2] =='Y' -> 3 + 1
            it[0] == 'A' && it[2] =='Z' -> 6 + 2

            it[0] == 'B' && it[2] =='X' -> 0 + 1
            it[0] == 'B' && it[2] =='Y' -> 3 + 2
            it[0] == 'B' && it[2] =='Z' -> 6 + 3

            it[0] == 'C' && it[2] =='X' -> 0 + 2
            it[0] == 'C' && it[2] =='Y' -> 3 + 3
            it[0] == 'C' && it[2] =='Z' -> 6 + 1
            else -> 0
        }
    }
    println("Total score when strategic: $strategicScore")
}
