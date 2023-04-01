//
//  VideoPlayerView.swift
//  iosApp
//
//  Created by Kashif.Mehmood on 01/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import AVKit
import common

struct VideoPlayerView: View {
    let url: URL

    var body: some View {
        VideoPlayer(player: AVPlayer(url: url))
            .aspectRatio(contentMode: .fit)
            .edgesIgnoringSafeArea(.all)
    }
}

class IOSAppVideoPlayer: NSObject, PlatformVideoPlayer {
    @objc
    func renderVideoPlayerView(url: String) {
        DispatchQueue.main.async {
            let videoPlayerView = VideoPlayerView(url: URL(string: url)!)
            let hostingController = UIHostingController(rootView: videoPlayerView)
        }
    }
}
