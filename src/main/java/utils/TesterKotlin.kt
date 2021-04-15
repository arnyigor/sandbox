package utils

fun main() {
    val tester = TesterKotlin()
    tester.run()
}

class TesterKotlin {

    fun run() {
        val proger: Proger = AndroidDev()
        proger.writeCode()
//        proger.testCode()
    }

    private inner class AndroidDev : Proger() {
        override fun writeCode() {
            println("AndroidDev write code")
        }

        private fun testCode() {
            println("AndroidDev test code")
        }
    }

    private open inner class Proger {
        open fun writeCode() {
            println("Proger write code")
        }

        private fun testCode() {
            println("Proger test code")
        }
    }
}