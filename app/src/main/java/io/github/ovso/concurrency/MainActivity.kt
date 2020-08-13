package io.github.ovso.concurrency

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {
    val feeds = listOf(
        "https:/www.daum.net",
        "https://ovso.github.io/feed.xml",
        "https://ovso.github.io/feed.xml",
        "https://ovso.github.io/feed.xml"
    )

    @ObsoleteCoroutinesApi
    private val dispatcher = newFixedThreadPoolContext(2, "IO")
    private val factory = DocumentBuilderFactory.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        asyncLoadNews()
    }

    @SuppressLint("SetTextI18n")
    private fun asyncLoadNews() = GlobalScope.launch(dispatcher) {
        val requests = mutableListOf<Deferred<List<String>>>()
        feeds.mapTo(requests) {
            fetchHeadlinesAsync(it, dispatcher)
        }
        requests.forEach {
            it.join()
        }

        val headlines = requests.filter {
            !it.isCancelled
        }.flatMap {
            it.getCompleted()
        }

        val failed = requests
            .filter { it.isCancelled }
            .size

        launch(Dispatchers.Main) {
            newsCount.text = "Found ${headlines.size} News in ${requests.size} feeds"
            if(failed > 0) {
                warnings.text = "Failed to fetch $failed feeds"
            }
        }
    }

    private fun fetchHeadlinesAsync(feed: String, dispatcher: CoroutineDispatcher) =
        GlobalScope.async(dispatcher) {
            val builder = factory.newDocumentBuilder()
            val xml = builder.parse(feed)
            val entries = xml.getElementsByTagName("entry")

            (0 until entries.length).map {
                entries.item(it).childNodes.item(0).childNodes.item(0).nodeValue
            }.toList()
        }
}