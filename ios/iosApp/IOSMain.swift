//
// Created by Kashif.Mehmood on 31/03/2023.
// Copyright (c) 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import common
import AVKit


struct MainSwiftUIView: View {
    let videoPlayer: IOSAppVideoPlayer

    var body: some View {

        MainViewControllerWrapper(videoPlayer: IOSAppVideoPlayer())
    }
}

struct MainViewControllerWrapper: UIViewControllerRepresentable {
    let videoPlayer: IOSAppVideoPlayer

    func makeUIViewController(context: Context) -> UIViewController {
        return Main_iosKt.MainViewController(videoPlayer: videoPlayer)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
