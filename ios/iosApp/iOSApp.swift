import SwiftUI
import common


@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    override init() {

        KoinKt.doInitKoin(baseUrl:  "https://api.themoviedb.org/3/")
    }

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {

        window = UIWindow(frame: UIScreen.main.bounds)
        let mainViewController = Main_iosKt.MainViewController()
        window?.rootViewController = mainViewController
        window?.makeKeyAndVisible()
        return true
    }
}
