package leetcode_problems

import java.util.*

fun reverse(num: Int): Int {
    if (num >= Int.MAX_VALUE) return 0
    var input = num
    val sign = if (num < 0) {
        input *= -1
        -1
    } else {
        1
    }
    val chArr = input.toString().toCharArray()
    val buffer = StringBuilder()
    for (ch in chArr.size - 1 downTo 0) {
        buffer.append(chArr[ch])
    }
    val resString = buffer.toString()
    if (resString.length >= 9 && buffer.first().toInt() > 0) {
        //todo
    }
    return resString.toInt() * sign
}


fun stringCompress(input: String): String {
    var currentChar: Char? = null
    var currentSize = 0
    val result = StringBuilder()
    val array = input.toCharArray()
    for (c in array) {
        if (currentChar == null) {
            currentChar = c
            currentSize = 1
            result.append(c).append(currentSize.toString())
        } else {
            if (c == currentChar) {
                currentSize++
                result.deleteCharAt(result.length - 1)
                result.append(currentSize.toString())
            } else {
                currentChar = c
                currentSize = 1
                result.append(c).append(currentSize.toString())
            }
        }
    }
    return if (result.length < input.length) {
        result.toString()
    } else {
        input
    }
}

fun totalCountChars(input: String): String {
    val chArray = arrayListOf<Char>()
    for (c in input.toCharArray()) {
        chArray.add(c)
    }
    var currentChar: Char? = null
    val iterator = chArray.iterator()
    var currentCount = 0
    while (iterator.hasNext()){
        val next = iterator.next()
        if (currentChar == null) {
            currentChar = next
            iterator.remove()
            currentCount++
        }else{
            if (currentChar == next) {
                iterator.remove()
                currentCount++
            }
        }
    }
    return input
}

fun sortMassive(arr: Array<Int>) {
    var cur: Int? = null
    var ind = 0
    val size = arr.size
    while (ind < size - 1) {
        val arValue = arr[ind]
        if(cur==null){
            cur = arValue
        }else{
            if(arValue<cur){
                cur
            }
        }
        ind++
    }
}