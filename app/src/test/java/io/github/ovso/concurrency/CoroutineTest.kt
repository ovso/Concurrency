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
        GlobalScope.launch {
            TODO("Not implemented yet!")
        }.invokeOnCompletion { cause ->
            cause?.let {
                println("Job cancelled due to ${it.message}")
            }
        }

        delay(2000)
    }


}