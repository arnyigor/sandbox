package delegation

class KotlinDelegateTest {
    private var trimmed by StringTrimDelegate()
    private var trimInside by StringTrimInsideDelegate()

    fun testCode(){
        trimmed = "        "
        trimInside = "' Hello      friend'"
        println(trimmed.isBlank())
        println(trimInside)
    }
}