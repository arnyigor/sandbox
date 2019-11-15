import kotlinx.coroutines.runBlocking
import leetcode_problems.sortMassive
import patterns.testBuilderMethod
import utils.CheckCardVerification
import utils.JavaRunner
import utils.async
import utils.dump
import java.math.BigInteger

fun runMethod() {
    JavaRunner.checkImito()
//    coroutinsTest()
//    sortMassive(arrayOf(3,1,4))
//    testBuilderMethod(needFire = true, newName = "Legolas")
//    testBuilderMethod(needMoney = true,newName = "Perto")
//    testBuilderMethod(needPower = true,newName = "Aragorn")
//    testInline()
}


/*private fun coroutinsTest() = runBlocking{
    println("coroutinsTest start " + Thread.currentThread().name)
    println("Start test " + Thread.currentThread().name)
    val res = async({
        println("async operation " + Thread.currentThread().name)
        longOperation()
    }, { it.printStackTrace() })
    println("res:${res} ${Thread.currentThread().name}")
    println("coroutinsTest end " + Thread.currentThread().name)
}*/

fun testInline(){
    val arrayListOf = arrayListOf<String>("1", "2", "3")
    println(arrayListOf.dump { it })
}


/*
private fun longOperation(): Long {
  return  fib(1000000L).toLong()
}

fun fib(n: Long): BigInteger {
    return fibonacci(BigInteger.ZERO, BigInteger.ONE, BigInteger.valueOf(n))
}

private fun fibonacci(a: BigInteger, b: BigInteger, n: BigInteger): BigInteger {
    return if (n == BigInteger.ZERO) a else fibonacci(b, a + b, n - BigInteger.ONE)
}*/
