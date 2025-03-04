package day_3

import java.io.File

fun getData() : MutableList<String> {
    var data = mutableListOf<String>()
    try{
        data = File("src/day_3/data_day_3").readLines().toMutableList()
    }
    catch (e: Exception){
        println(e.message)
    }
    return data
}

fun getSum(list : MutableList<String>) : Long {
    tailrec fun accSum(index : Int, sum : Long) : Long{
        if (index >= list.size) return sum
        val currentWord = list[index]
        val middle = list[index].length / 2
        val wordDivided = arrayOf(currentWord.substring(0, middle), currentWord.substring(middle))
        val commonChars = wordDivided[0].toSet().intersect(wordDivided[1].toSet())

        val priorityNum = commonChars.sumOf {
            when (it) {
                in 'a'..'z' -> (it.code - 'a'.code + 1).toLong()
                in 'A'..'Z' -> (it.code - 'A'.code + 27).toLong()
                else -> 0
            }
        }
        return accSum(index+1, sum + priorityNum)
    }
    return accSum(0, 0)
}

// lösning jag hittat på nätet

fun part1(list: MutableList<String>) : Long = list.map { word ->
    word.substring(0 until word.length / 2).toCharArray() to word.substring(word.length/2).toCharArray() }
        .flatMap { (first, second) -> first intersect second.toSet() }.sumOf { it.toScore() }.toLong()

fun Char.toScore():Int =
    if(this.isUpperCase()) {
        this.code - 'A'.code + 27
    } else{
        this.code - 'a'.code + 1
    }

fun main(){

    println(getSum(getData()))

    println(part1(getData()))
}