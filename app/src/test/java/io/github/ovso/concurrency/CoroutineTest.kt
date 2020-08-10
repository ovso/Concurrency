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
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Job cancelled due to ${throwable.message}")
        }

        GlobalScope.launch(exceptionHandler) {
            TODO("Not implemented yet!")
        }

        delay(2000)
    }


}