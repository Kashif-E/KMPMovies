package com.kashif.common

import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView

class YoutubeVideoPlayer(private val videoId: String) : JFXPanel() {
    init {
        Platform.runLater(::playYoutubeVideo)
    }

    private fun playYoutubeVideo() {
        val webView = WebView()
        val webEngine = webView.engine

        val embedUrl = "https://www.youtube.com/embed/$videoId"

        // Set the user agent to simulate a browser for YouTube
        webEngine.userAgent =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"

        // Enable JavaScript support for YouTube embed player
        webEngine.isJavaScriptEnabled = true

        // Load the YouTube video using the embed URL
        webEngine.load(embedUrl)
        val scene = Scene(webView)
        setScene(scene)
    }
}
