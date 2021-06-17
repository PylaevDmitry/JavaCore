package stepic

fun main() {
    calcNullValues(arrayOf(82, null, null, null, null, null, 7, 55, null, 59, 4, 25, null, null, 66, null, 3, 62, 55, null, 42, 5, 35, 8, null))
}

fun calcNullValues(input: Array<Int?>):Array<Int>{
    return arrayOf(input.count{it==null}, input.filterNotNull().sumOf { it })

}