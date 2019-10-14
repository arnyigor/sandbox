package leetcode_problems

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
    if(resString.length>=9 && buffer.first().toInt()>0){
        //todo
    }
    return resString.toInt() * sign
}