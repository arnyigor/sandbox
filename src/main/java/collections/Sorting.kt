package collections

import utils.Stopwatch
import java.util.*

fun mergeSort() {

    val l1 = arrayListOf<Int>()
    for (i in 1..1_000_000){
        l1.add(Random().nextInt())
    }
    val l2 = arrayListOf<Int>()
    for (i in 1..1_000_000){
        l2.add(Random().nextInt())
    }
    val new = arrayListOf<Int>()
    val stopwatch = Stopwatch(true)
    new.addAll(l1)
    new.addAll(l2)
    println("add time:${stopwatch.formatTime(3)}")
    stopwatch.restart()
    new.sort()
    println(new.size)
    println("sort time:${stopwatch.formatTime(3)}")
}