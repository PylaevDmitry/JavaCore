package profSoftTest

import java.io.FileNotFoundException
import java.io.PrintWriter
import kotlin.random.Random

fun main() {
    val arr:Array<Int> = randomizer(0, 100, 20)
    arr.forEach { print("$it ") }
    println()

    sort(arr)
    arr.forEach { print("$it ") }
    println()

    val valueToSearch = 28
    val out:Boolean = intSearch(arr, valueToSearch)
    println("$valueToSearch $out")
}

fun randomizer (down:Int, up:Int, length:Int) = Array(length) { Random.nextInt(down, up) }

fun sort (array: Array<Int>) = array.sort()

fun intSearch (array: Array<Int>, value:Int) = array.contains(value)




