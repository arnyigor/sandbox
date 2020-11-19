package domain.main

import KotlinRunnerTestable
import coroutins.CoroutinsTests
import interfaces.Testable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import rx.CompletableTests
import rx.SingleTestsRunnable
import utils.JavaRunnerTestable

class MainInteractor : IMainInteractor {
    @ExperimentalCoroutinesApi
    override fun runTest() {
        val testable: Testable? = when ("rx") {
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