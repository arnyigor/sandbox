package domain.main

import interfaces.Testable
import rx.CompletableTests
import utils.JavaRunnerTestable
import utils.ParsingUtils
import utils.dump
import java.math.BigDecimal
import java.math.RoundingMode

class KotlinRunnerTestable : Testable {
    override fun runTest(args: Array<String>?) {
        ParsingUtils().readFile()
    }

    private fun isRateZero(stringRate: String) =
            BigDecimal(stringRate) == BigDecimal.ZERO

    @JvmOverloads
    fun compareDecimalWithDouble(decimal: BigDecimal, doubleValue: Double, scale: Int = 2): Int {
        return decimal.setScale(scale, RoundingMode.HALF_UP).compareTo(BigDecimal(doubleValue))
    }

    private fun BigDecimal?.notEquals(other: BigDecimal?): Boolean = this?.compareTo(other) != 0

    fun testCompletable() {
        val rxCompletableTests = CompletableTests()
        rxCompletableTests.runTest()
    }

    fun runMethod() {
        JavaRunnerTestable.checkImito()
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
    fun testInline() {
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
}