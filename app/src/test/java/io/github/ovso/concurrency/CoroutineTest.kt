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
        val task = GlobalScope.launch {
//            doSomething()
            printCurrentThread()
        }
        task.join()
    }

    private fun printCurrentThread() {
        println("Running in thread [${Thread.currentThread().name}]")
    }

    private fun doSomething() {
        throw UnsupportedOperationException("Can't do")
    }
}