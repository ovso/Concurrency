package io.github.ovso.concurrency

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.ovso.concurrency.data.Article
import io.github.ovso.concurrency.data.Feed
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {
    private val feeds = listOf<Feed>(
        Feed(name = "Jaeho's blog 1", url = "https://ovso.github.io/feed.xml"),
        Feed(name = "Jaeho's blog 2", url = "https://ovso.github.io/feed.xml"),
        Feed("Jaeho's blog 3", url = "https://ovso.github.io/feed.xml"),
        Feed(name = "Daum", url = "https://www.daum.net")
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
        val requests = mutableListOf<Deferred<List<Article>>>()
        feeds.mapTo(requests) {
            fetchArticleAsync(it, dispatcher)
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
            if (failed > 0) {
                warnings.text = "Failed to fetch $failed feeds"
            }
        }
    }

    private fun fetchArticleAsync(feed: Feed, dispatcher: CoroutineDispatcher) =
        GlobalScope.async(dispatcher) {
            val builder = factory.newDocumentBuilder()
            val xml = builder.parse(feed.url)
            val entries = xml.getElementsByTagName("entry")

            (0 until entries.length).map {
                val title = entries.item(it).childNodes.item(0).childNodes.item(0).textContent
                val summary = entries.item(it).childNodes.item(5).childNodes.item(0).textContent
                Article(
                    feed = feed.name,
                    title = title,
                    summary = summary
                )
            }.toList()
        }
}