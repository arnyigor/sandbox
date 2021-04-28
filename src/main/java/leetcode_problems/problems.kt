package leetcode_problems

sealed class PROBLEM {
    data class CountChars(val type: Int) : PROBLEM()
    object MergeSortedArray : PROBLEM()
    object MergeTwoLists : PROBLEM()
    object RemoveDuplicates : PROBLEM()
    object StrStr : PROBLEM()
    object SearchInsert : PROBLEM()
    object LengthOfLastWord : PROBLEM()
    object PlusOne : PROBLEM()
    object NONE : PROBLEM()
}

fun run(problem: PROBLEM) {
    when (problem) {
        is PROBLEM.CountChars -> {
            when (problem.type) {
                0 -> {
                    for (i in 1..20) {
                        val generateNums = generateNums(10000)
                        countTime { countChars(generateNums) }
                    }
                }
                else -> {
                    for (i in 1..20) {
                        val generateNums = generateNums(10000)
                        countTime { countCharsMap(generateNums) }
                    }
                }
            }
        }
        is PROBLEM.MergeSortedArray -> {
            val a = intArrayOf(4, 6, 8, 9, 10)
            val b = intArrayOf(3, 4, 5, 6, 7, 8, 9)
            println(mergeToSortedArray(a, b).joinToString())
        }
        PROBLEM.NONE -> {
            println(countChar1("ssss",'d'))
        }
        PROBLEM.MergeTwoLists -> {
            val ln4 = ListNode(3)
            val ln2 = ListNode(2).apply { next = ln4 }
            val ln1 = ListNode(1).apply { next = ln2 }

            val ln44 = ListNode(7)
            val ln3 = ListNode(5).apply { next = ln44 }
            val ln11 = ListNode(3).apply { next = ln3 }

            println("ln1:$ln1")
            println("ln11:$ln11")

            println(mergeTwoLists(ln1, ln11))
        }
        PROBLEM.RemoveDuplicates -> {
            val nums = intArrayOf(1, 1, 2)
            println(nums.joinToString())
            println(removeDuplicates(nums))
            println(nums.joinToString())
        }
        PROBLEM.StrStr -> {
            //Input: haystack = "hello", needle = "ll"
            //Output: 2
            //Input: haystack = "aaaaa", needle = "bba"
            //Output: -1
            //Input: haystack = "", needle = ""
            //Output: 0
            println(strStr("hello", "ll"))
            println(strStr("aaaaa", "bba"))
            println(strStr("", "a"))
        }
        PROBLEM.SearchInsert -> {
            // [1,3,5,6], target = 5 -> 2
            // [1,3,5,6], target = 2 -> 1
            // [1,3,5,6], target = 7 -> 4
            // [1,3,5,6], target = 0 -> 0
            // [1], target = 0 -> 0
            println(searchInsert(intArrayOf(1, 3, 5, 6), 5))
            println(searchInsert(intArrayOf(1, 3, 5, 6), 2))
            println(searchInsert(intArrayOf(1, 3, 5, 6), 7))
            println(searchInsert(intArrayOf(1, 3, 5, 6), 0))
            println(searchInsert(intArrayOf(1), 0))
        }
        PROBLEM.LengthOfLastWord -> {
            // "Hello World"->5
            println(lengthOfLastWord("Hello World"))
            println(lengthOfLastWord("a "))
        }
        PROBLEM.PlusOne -> {
            println(plusOne(intArrayOf(9)).joinToString())
            println(plusOne(intArrayOf(9, 9)).joinToString())
        }
    }
}

fun main() {
    run(PROBLEM.NONE)
}

fun plusOne(digits: IntArray): IntArray {
    var intArray = digits
    for ((i, num) in digits.withIndex()) {
        if (i == digits.size - 1) {
            val newNum = num + 1
            if (newNum > 9) {
                val newSize = digits.size + 1
                val newArray = intArray.copyOf(newSize)
                intArray = newArray
                intArray[i] = newNum / 10
                intArray[i + 1] = newNum % 10
            } else {
                intArray[i] = newNum
            }
        } else {
            intArray[i] = num
        }
    }
    return intArray
}

fun lengthOfLastWord(s: String): Int {
    val split = s.trim().split(" ")
    if (split.isNotEmpty()) {
        return split.last().length
    }
    return 0
}

fun searchInsert(nums: IntArray, target: Int): Int {
    for ((i, num) in nums.withIndex()) {
        if (num == target) return i
        if (i > 0 && nums[i - 1] < target && nums[i] > target) return i
        if (i == nums.size - 1 && nums[i] < target) return i + 1
    }
    return 0
}

fun strStr(haystack: String, needle: String): Int {
    if (needle.isEmpty()) return 0
    return haystack.indexOf(needle)
}

fun removeDuplicates(nums: IntArray): Int {
    var j = 0
    for (i in nums.indices) {
        if (nums[i] != nums[j]) {
            nums[++j] = nums[i]
        }
    }
    return ++j
}

fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
    if (l1 == null) return l2
    if (l2 == null) return l1
    return if (l1.`val` < l2.`val`) {
        l1.next = mergeTwoLists(l1.next, l2)
        l1
    } else {
        l2.next = mergeTwoLists(l1, l2.next)
        l2
    }
}

fun mergeToSortedArray(a: IntArray, b: IntArray): IntArray {
    val n = a.size
    val m = b.size
    val c = IntArray(n + m)
    var i = 0
    var j = 0
    var k = 0
    while (i < n && j < m) {
        if (a[i] < b[j]) {
            c[k++] = a[i++]
        } else {
            c[k++] = b[j++]
        }
    }
    while (i < n) {
        c[k++] = a[i++];
    }
    while (j < m) {
        c[k++] = b[j++];
    }
    return c
}

fun generateNums(count: Int): String {
    return StringBuilder().apply {
        var iter = 1
        for (i in 1..count) {
            if (iter == 10) {
                iter = 1
            }
            append(iter)
            iter++
        }
    }.toString()
}

fun <T> countTime(block: () -> T): T {
    val timeStart = System.nanoTime()
    val invoke = block.invoke()
    val diff = (System.nanoTime() - timeStart) / 1000
    println("Block time:$diff mcs")
    return invoke
}

fun countChars(text: String): HashMap<Char, Int> {
    val map = hashMapOf<Char, Int>()
    for (c in text) {
        map[c] = text.count { it == c }
    }
    return map
}

// найти сколько раз встречается str
fun countChar(input: String, str: Char): Int {
    return countCharsMap(input)[str] ?: 0
}

fun countChar1(input: String, str: Char): Int {
    val inputCharsList = input.toCharArray()
    var count = 0
    inputCharsList.forEach{
        if(it == str) count++
    }
    return count
}

fun countCharsMap(text: String): HashMap<Char, Int> {
    val map = hashMapOf<Char, Int>()
    for (c in text) {
        val i = map[c]
        if (i == null) {
            map[c] = 1
        } else {
            map[c] = i + 1
        }
    }
    return map
}

data class ListNode(var `val`: Int, var next: ListNode? = null) {
    override fun toString(): String {
        return "$`val`->$next"
    }
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