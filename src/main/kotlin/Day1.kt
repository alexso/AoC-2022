import java.io.File

fun main(args: Array<String>) {
    var elve = 0
    val list = mutableListOf<Int>()
    File("inputData/Input1.txt").forEachLine { carry ->
        if (carry.isNotEmpty()) {
            elve += carry.toInt()
        } else {
            list.add(elve)
            elve = 0
        }
    }
    list.sortDescending()
    println("Top carry is: ${list.first()}")
    val top3 = list.subList(0, 3)
    println("Three top carries are together: ${top3.sum()}")

}
