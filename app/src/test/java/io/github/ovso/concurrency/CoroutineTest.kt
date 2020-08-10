package io.github.ovso.concurrency

import kotlinx.coroutines.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@InternalCoroutinesApi
class CoroutineTest {
    @Test
    fun solution() {
        main()
    }

    private fun main() = runBlocking {
        val job = GlobalScope.launch {
            delay(5000)
        }

        delay(2000)

        // cancel
        job.cancel(cause = CancellationException("Tired of waiting"))

        val cancellation = job.getCancellationException()
        println(cancellation.message)
    }


}