package collections

import java.util.*
import kotlin.collections.HashMap

fun main() {
    val count = 1_000_000
    val times = 5
    println("test MAPS")
    println("count:$count")
    println("times:$times")
    println("---HM----")
    repeat(times) {
        testHM(count)
    }
    println("-------")
    println("---TM----")
    repeat(times) {
        testHM(count)
    }
    println("-------")
}

fun testHM(i: Int) {
    val mutableListOf = mutableListOf<Long>()
    repeat(5) {
        mutableListOf.add(HM(i))
    }
    println("average:${mutableListOf.average()}")
}

fun testTM(i: Int) {
    val mutableListOf = mutableListOf<Long>()
    repeat(5) {
        mutableListOf.add(TM(i))
    }
    println("average:${mutableListOf.average()}")
}

private fun TM(i: Int): Long {
    val time = System.currentTimeMillis()
    val map = TreeMap<Comp, String>()
    repeat(i) {
        map.put(Comp(it), it.toString())
    }
    val s = map[Comp(i-1)]
    val l = System.currentTimeMillis() - time
    println("time:$l,map[${i-1}] = $s")
    return l
}

private fun HM(i: Int): Long {
    val time = System.currentTimeMillis()
    val map = HashMap<Comp, String>()
    repeat(i) {
        map.put(Comp(it), it.toString())
    }
    val s = map[Comp(i-1)]
    val l = System.currentTimeMillis() - time
    println("time:$l,map[${i-1}] = $s")
    return l
}

class Comp(val key: Int)

fun testMap() {
    addKey("1")
    addKey("1.")
    addKey("1..")
    addKey("2")
    addKey("2..")
}

private fun addKey(key: String) {
    val hash = hash(key)
    val index = index(16, hash)
    println("key:$key,hash:${hash},index:${index}")
}

fun hash(key: Any?): Int {
    var h: Int
    return if (key == null) 0 else key.hashCode().also { h = it } xor (h ushr 16)
}

private fun index(n: Int, hash: Int) = n - 1 and hash