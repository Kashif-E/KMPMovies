import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import javax.swing.JPanel

@Composable
fun DesktopWebView(
    modifier: Modifier = Modifier,
    url: String
) {
    // Create a new JFXPanel to host the WebView
    val jfxPanel = remember { JFXPanel() }

    // Initialize WebView and WebEngine when the composable is first applied
    LaunchedEffect(url) {
        Platform.runLater {
            val webView = WebView()
            val webEngine = webView.engine

            webEngine.userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"
            webEngine.isJavaScriptEnabled = true
            webEngine.load(url)

            val scene = Scene(webView)
            jfxPanel.scene = scene
        }
    }

    // Clean up resources when composable is disposed
    DisposableEffect(url) {
        onDispose {
            Platform.runLater {
                jfxPanel.scene = null // Detach the scene from the JFXPanel
            }
        }
    }

    // Use SwingPanel to integrate the JFXPanel with Compose
    Box(modifier = modifier) {
        SwingPanel(
            factory = { jfxPanel },
            modifier = Modifier.fillMaxSize()
        )
    }
}
