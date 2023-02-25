import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kashif.common.Application
import com.kashif.common.di.initKoin


fun main() = application {
    initKoin("https://api.themoviedb.org/3/")
    Window(onCloseRequest = ::exitApplication) {
       Application()
    }
}
