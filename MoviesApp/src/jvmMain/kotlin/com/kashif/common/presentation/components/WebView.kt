import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.kashif.common.presentation.components.JavaFXPanel
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView
import java.awt.BorderLayout
import javax.swing.JPanel


@Composable
fun DesktopWebView(
    modifier: Modifier = Modifier,
    url: String
) {
    val jfxPanel = remember { JFXPanel() }
    var jPanel: JPanel? = remember { JPanel(BorderLayout()) }
    DisposableEffect(url) {
        onDispose {
            Platform.runLater {
                jPanel = null
                jfxPanel.scene = null
            }
        }
    }
    JavaFXPanel(
        container = jPanel!!,
        panel = jfxPanel,
        modifier = modifier
    ) {
        Platform.runLater {
            // Create a WebView and set its scene
            val webView = WebView()
            val webEngine = webView.engine

            val scene = Scene(webView)

            webEngine.apply {
                userAgent =
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"
                isJavaScriptEnabled = true
                load(url)
            }

            jfxPanel.scene = scene
            webEngine.setOnVisibilityChanged {
                webEngine.load(null)
            }
        }

    }
}

