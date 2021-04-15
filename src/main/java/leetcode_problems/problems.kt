package leetcode_problems

fun main() {
    println(
        mergeTwoLists(
            ListNode(2, ListNode(3, ListNode(1))),
            ListNode(3, ListNode(4, ListNode(2)))
        )
    )
}

data class ListNode(var `val`: Int, var next: ListNode? = null)

fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
    var head: ListNode? = l1
    var cur: ListNode? = null
    while (head != null) {
        val num = head.`val`
        if (cur == null) {
            cur = ListNode(num, head)
        } else {
            val i = cur.`val`
            if (i < num) {
                cur = head
            }
        }
        head = head.next
    }
    return null
}

fun arraydiff(arr1: IntArray, arr2: IntArray): Int {
    var result = 0
    arr1.forEach { result += it }
    arr2.forEach { result -= it }
    return result
}

fun longestCommonPrefix(strs: Array<String>): String {
    if (strs.isNotEmpty()) {
        val first = strs.first()
        for (i in first.length downTo 1) {
            val tmp = first.substring(0, i)
            if (strs.all { cur -> cur.startsWith(tmp) }) return tmp
        }
    }
    return ""
}

fun romanToInt(s: String): Int {
    var num = 0
    val chars = s.toCharArray()
    val size = chars.size
    var ignoreNext = false
    for ((i, roman) in chars.withIndex()) {
        if (ignoreNext) {
            ignoreNext = false
            continue
        }
        val hasNextChar = i < size - 1
        val temp = when {
            hasNextChar && roman == 'C' -> {
                when (chars[i + 1]) {
                    'D' -> {
                        ignoreNext = true
                        400
                    }
                    'M' -> {
                        ignoreNext = true
                        900
                    }
                    else -> getNum(roman)
                }
            }
            hasNextChar && roman == 'X' -> {
                when (chars[i + 1]) {
                    'L' -> {
                        ignoreNext = true
                        40
                    }
                    'C' -> {
                        ignoreNext = true
                        90
                    }
                    else -> getNum(roman)
                }
            }
            hasNextChar && roman == 'I' -> {
                when (chars[i + 1]) {
                    'V' -> {
                        ignoreNext = true
                        4
                    }
                    'X' -> {
                        ignoreNext = true
                        9
                    }
                    else -> getNum(roman)
                }
            }
            else -> getNum(roman)
        }
        num += temp
    }
    return num
}

fun getNum(c: Char): Int = when (c) {
    'I' -> 1
    'V' -> 5
    'X' -> 10
    'L' -> 50
    'C' -> 100
    'D' -> 500
    'M' -> 1000
    else -> 0
}

fun isPalindrome(x: Int): Boolean {
    val chArr = x.toString().toCharArray()
    var front = 0
    var back = chArr.size - 1
    while (front != back) {
        if (chArr[front] != chArr[back]) {
            return false
        }
        front++
        back--
        if (front > back)
            break
    }
    return true
}

fun reversed(x: Int): Int {
    var rev = 0
    var n = x
    while (n > 0) {
        val last = n % 10
        n /= 10
        rev = rev * 10 + last
    }
    return rev
}

fun isPalindrome1(x: Int): Boolean {
    var rev = 0
    var n = x
    while (n > 0) {
        val last = n % 10
        n /= 10
        rev = rev * 10 + last
    }
    return rev == x
}

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
    val intOrNull = resString.toIntOrNull() ?: return 0
    return intOrNull * sign
}

fun isValid(s: String): Boolean {
    val openAr = arrayOf("(", "{", "[")
    val closeAr = arrayOf(")", "}", "]")
    val chAr = s.toCharArray()
    val opened = arrayOfNulls<String>(chAr.size)
    var lastOpen = 0
    for (c in chAr.withIndex()) {
        val opInd = openAr.indexOf(c.value.toString())
        if (opInd != -1) {
            opened[lastOpen] = openAr[opInd]
            lastOpen++
        }
        val clInd = closeAr.indexOf(c.value.toString())
        if (clInd != -1) {
            val lastInd = lastOpen - 1
            if (lastInd != -1) {
                val last = opened[lastInd]
                val openCh = openAr[clInd]
                if (openCh == last) {
                    opened[lastInd] = null
                    lastOpen--
                } else {
                    return false
                }
            } else {
                return false
            }
        }
    }
    for (op in opened) {
        if (op != null) {
            return false
        }
    }
    return true
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
    while (iterator.hasNext()) {
        val next = iterator.next()
        if (currentChar == null) {
            currentChar = next
            iterator.remove()
            currentCount++
        } else {
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
        if (cur == null) {
            cur = arValue
        } else {
            if (arValue < cur) {
                cur
            }
        }
        ind++
    }
}