package collections

fun removeItemList(){
    val list = arrayListOf("1", "2", "3", "4", "5", "6", "7", "8")
    val iterator = list.iterator()
    println(list)
    println(list.size)
    while (iterator.hasNext()){
        val next = iterator.next()
        if (next == "2" || next == "4") {
            iterator.remove()
        }
        println(next)
    }
    println(list)
    println(list.elementAt(1))
}