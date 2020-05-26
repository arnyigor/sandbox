package domain.main

import KotlinRunnerTestable
import coroutins.CoroutinsTests
import interfaces.Testable
import rx.CompletableTests
import rx.SingleTestsRunnable
import utils.JavaRunnerTestable

class MainInteractor : IMainInteractor {
    override fun runTest() {
        val testable: Testable? = when ("kotlin") {
            "rx" -> SingleTestsRunnable()
            "rxComplete" -> CompletableTests()
            "coroutins" -> CoroutinsTests()
            "java" -> JavaRunnerTestable()
            "kotlin" -> KotlinRunnerTestable()
            else -> null
        }
        testable?.runTest()
    }
}