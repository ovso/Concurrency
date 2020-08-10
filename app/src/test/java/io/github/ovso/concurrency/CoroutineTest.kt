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
        val deferred = GlobalScope.async {
            println("디펄드")
            TODO("Not implemented yet!")
        }


        try {
            deferred.await()
        } catch (e: Throwable) {
            println("Deferred cancelled due to ${e.message}")
        }
    }
}