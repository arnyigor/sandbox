package leetcode_problems

import java.util.*

fun main() {
    println(isValidPar(""))
}

private fun anagram() {
    // qiu,iuq->1
    // zprl,zprc->0
    var result = 0
    val first = readLine()!!
    val second = readLine()!!

    println(result)
}

fun isValidPar(s: String): Boolean {
    val map = hashMapOf('[' to ']', '(' to ')', '{' to '}')
    val stack = Stack<Char>()
    for (c in s) {
        if (map.keys.contains(c)) {
            stack.push(c)
        } else if (map.values.contains(c)) {
            if (stack.isNotEmpty() && map[stack.peek()] == c) {
                stack.pop()
            } else {
                return false
            }
        }
    }
    return stack.empty()
}

private fun getSmallestIndex(arr: IntArray): Int {
    var smallestIndex = 0
    var smallest = arr[smallestIndex]
    for ((i, num) in arr.withIndex()) {
        if (num < smallest) {
            smallest = num
            smallestIndex = i
        }
    }
    return smallestIndex
}

private fun distinct() {
    val n = readLine()?.toInt() ?: 0
    val set = hashSetOf<Int>()
    for (i in 0 until n) {
        set.add(readLine()?.toInt() ?: 0)
    }
    for (i in set) {
        println(i)
    }
}

private fun distinctArr() {
    var tmp = -1
    for (i in 0 until (readLine()!!.toInt())) {
        val num = readLine()!!.toInt()
        when {
            tmp == -1 -> {
                tmp = num
                println(num)
            }
            tmp != num -> {
                tmp = num
                println(num)
            }
        }
    }
}

private fun distinctArr1(arr: IntArray): IntArray {
    return arr.distinct().toIntArray()
}

private fun getMaxOfList(list: MutableList<Int>): Int {
    var max = 0
    for ((i, num) in list.withIndex()) {
        when {
            i == 0 && num == 1 -> max = 1
            i > 0 && num == 1 && list[i - 1] == 1 -> {
                max++
            }
            num == 1 -> {
                if (max < 1) {
                    max = 1
                }
            }
        }
    }
    return max
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