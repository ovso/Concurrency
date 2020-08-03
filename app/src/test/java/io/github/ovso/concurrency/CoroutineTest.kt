package io.github.ovso.concurrency

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
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
        val task = GlobalScope.async {
            doSomething()
        }
        task.join()
        if (task.isCancelled) {
            val completionExceptionOrNull = task.getCancellationException()
            println("Error with message: ${completionExceptionOrNull.cause}")
        } else {
            println("Success")
        }
    }

    private fun doSomething() {
        throw UnsupportedOperationException("Can't do")
    }
}