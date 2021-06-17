package profSoftTest

import java.util.regex.Pattern
import kotlin.collections.HashMap

fun main() {
    val str = "Мама, мама,мыла. мыла: мыла;раму!"
    for(item in wordSearch(str)) println("${item.key}:${item.value}")
}

fun wordSearch (string:String): HashMap<String, Int> {
    val hashMap = HashMap<String, Int>()
    val pattern: Pattern = Pattern.compile("\\s*(\\s|,|!|;|:|\\.)\\s*")
    for (word in pattern.split(string.trim().lowercase())) {
        if (hashMap.containsKey(word)) hashMap[word] = hashMap.getValue(word)+1
        else hashMap[word] = 1
    }
    return hashMap
}




