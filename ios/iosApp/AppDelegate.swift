import SwiftUI
import common
import SwiftUI
import AVKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    let platformVideoPlayer = IOSAppVideoPlayer()

    override init() {
        KoinKt.doInitKoin(baseUrl: "https://api.themoviedb.org/3/")
    }

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
        let rootView = MainSwiftUIView(videoPlayer: platformVideoPlayer)
        let hostingController = UIHostingController(rootView: rootView)
        window?.rootViewController = hostingController
        window?.makeKeyAndVisible()
        return true
    }
}
