package io.github.ovso.concurrency

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {
    @ObsoleteCoroutinesApi
    private val dispatcher = newSingleThreadContext(name = "ServiceCall")
    private val factory = DocumentBuilderFactory.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        asyncLoadNews()
    }

    @SuppressLint("SetTextI18n")
    private fun asyncLoadNews() = GlobalScope.launch(dispatcher) {
        val headlines = fetchRssHeadlines()
        launch(Dispatchers.Main) {
            newsCount.text = "Found ${headlines.count()} News"
        }
    }

    private fun fetchRssHeadlines(): List<String> {
        val builder = factory.newDocumentBuilder()
        val xml = builder.parse("https://ovso.github.io/feed.xml")
        val entries = xml.getElementsByTagName("entry")
        return (0 until entries.length).map {
            entries.item(it).childNodes.item(0).childNodes.item(0).nodeValue
        }.toList()
    }
}