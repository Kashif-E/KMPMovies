package com.kashif.common

import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView

class WebView(private val url: String) : JFXPanel() {
    init {
        Platform.runLater(::launchWebView)
    }

    private fun launchWebView() {
        val webView = WebView()
        val webEngine = webView.engine

        // Set the user agent to simulate a browser for YouTube
        webEngine.userAgent =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"

        // Enable JavaScript support for YouTube embed player
        webEngine.isJavaScriptEnabled = true

        // Load the YouTube video using the embed URL
        webEngine.load(url)
        val scene = Scene(webView)
        setScene(scene)
    }
}
