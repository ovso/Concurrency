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
        val headlinesTask = GlobalScope.async {
            getHeadlines()
        }
        headlinesTask.await()
    }

    private fun getHeadlines(): Any {
        println("getHeadlines")
        return "null"
    }


}