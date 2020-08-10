package io.github.ovso.concurrency

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.system.measureTimeMillis

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
        val time = measureTimeMillis {
            val job = GlobalScope.launch {
                println("Launch1")
                delay(2000)
                println("Launch2")
            }
            // Wait for it to complete once
            job.join()

            // Restart the Job
            println("Start")
            job.start()
            println("Join")
            job.join()
        }

        println("Took $time ms")
    }
}