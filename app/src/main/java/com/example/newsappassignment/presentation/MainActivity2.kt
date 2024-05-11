package com.example.newsappassignment.presentation

import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.newsappassignment.R

/**
 * Activity for displaying a web page.
 */
class MainActivity2 : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Get the article URL from the intent
        val articleUrl = intent.getStringExtra("article_url")

        // Initialize views
        webView = findViewById(R.id.web)
        progressBar = findViewById(R.id.progress_bar)
        text = findViewById(R.id.text)

        // Configure WebView settings
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = CustomWebViewClient()

        // Load the article URL if available, otherwise show an error message
        if (articleUrl != null) {
            webView.loadUrl(articleUrl)
        } else {
            progressBar.visibility = View.GONE
            webView.visibility = View.GONE
            text.visibility = View.VISIBLE
        }
    }

    /**
     * Custom WebViewClient for handling page loading events.
     */
    private inner class CustomWebViewClient : WebViewClient() {
        override fun onPageStarted(
            view: WebView?,
            url: String?,
            favicon: android.graphics.Bitmap?
        ) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            // Show the WebView and hide the progress bar when the page finishes loading
            webView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return false
        }
    }
}
