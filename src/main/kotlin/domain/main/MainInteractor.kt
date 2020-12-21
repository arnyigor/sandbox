package domain.main

import KotlinRunnerTestable
import coroutins.CoroutinsTests
import rx.CompletableTests
import rx.SingleTestsRunnable
import utils.JavaRunnerTestable

class MainInteractor : IMainInteractor {
    override fun runTest(type: TestType) {
        when (type) {
            TestType.RX -> SingleTestsRunnable()
            TestType.RXComplete -> CompletableTests()
            TestType.COROUTINS -> CoroutinsTests()
            TestType.JAVA -> JavaRunnerTestable()
            TestType.KOTLIN -> KotlinRunnerTestable()
        }.runTest()
    }
}