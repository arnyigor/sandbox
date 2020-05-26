package coroutins

import interfaces.Testable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import utils.Stopwatch
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class CoroutinsTests : Testable, CoroutineScope, AutoCloseable {
    private lateinit var stopwatch: Stopwatch
    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.IO
    val asFlow = (1..5).asFlow()
    override fun close() {
        coroutineContext.cancel()
    }

    override fun runTest(args: Array<String>?) {
        launch {
            stopwatch = Stopwatch(true)
            testFlow()
        }
    }

    private suspend fun testFlow() {
        asFlow
            .onEach { delay(1000) }
            .transform { tr ->
                emit("initRequest:$tr")
                emit(performRequest(tr))
            }
            .onEach {
                println("it:$it")
            }
            .onStart {
                println("Started")
            }
            .onCompletion {
                println("onCompletion")
            }
            .collect {
                println("testFlow result:$it")
            }
    }

    private suspend fun performRequest(request: Int): String {
        delay((1000 * request).toLong())
        var precision = 3
        if (request == 3) {
            precision = request / (precision - request)
        }
        return "Request $request time:${stopwatch.formatTime(precision)}"
    }
}
