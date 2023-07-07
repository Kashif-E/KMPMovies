//
//  VideoPlayerView.swift
//  iosApp
//
//  Created by Kashif.Mehmood on 01/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import UIKit
import AVKit
import common
//
//class VideoPlayerView: UIView {
//    let playerLayer = AVPlayerLayer()
//    
//    init(url: URL) {
//        super.init(frame: .zero)
//        
//        let player = AVPlayer(url: url)
//        playerLayer.player = player
//        layer.addSublayer(playerLayer)
//        
//        player.play()
//    }
//    
//    required init?(coder: NSCoder) {
//        fatalError("init(coder:) has not been implemented")
//    }
//    
//    override func layoutSubviews() {
//        super.layoutSubviews()
//        
//        playerLayer.frame = bounds
//    }
//}
//
//class IOSAppVideoPlayer: NSObject, PlatformVideoPlayer {
//    func renderVideoPlayerView(url: String) {
//        DispatchQueue.main.async {
//            let videoPlayerView = VideoPlayerView(url: URL(string: url)!)
//            let viewController = UIViewController()
//            viewController.view = videoPlayerView
//            UIApplication.shared.keyWindow?.rootViewController?.present(viewController, animated: true, completion: nil)
//        }
//    }
//    
//    func returnVideoPlayerView(url: String) -> UIView {
//        return  VideoPlayerView(url: URL(string: url)!)
//    }
//    
//   
//}
//
//
//
//
