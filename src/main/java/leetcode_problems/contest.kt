package leetcode_problems

fun main() {
//    val (a, b) = readLine()!!.split(' ')
    println(equalsStr("a..", "a.."))
}

fun equalsStr(strA: String, strB: String): Boolean {
    return getStrBuilder(strA) == getStrBuilder(strB)
}

fun getStrBuilder(strB: String) = StringBuilder().apply {
    strB.forEach { ch ->
        if (ch != '.') {
            append(ch)
        } else {
            if (length > 0) {
                delete(length - 1, length)
            }
        }
    }
}.toString()