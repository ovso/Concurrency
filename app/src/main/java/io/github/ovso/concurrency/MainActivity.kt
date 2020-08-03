package io.github.ovso.concurrency

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.newSingleThreadContext

class MainActivity : AppCompatActivity() {
    val netDispatcher = newSingleThreadContext(name = "ServiceCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}