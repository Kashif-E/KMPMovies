//
// Created by Kashif.Mehmood on 31/03/2023.
// Copyright (c) 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import common
import AVKit


struct MainSwiftUIView: View {
 

    var body: some View {
       
            MainViewControllerWrapper()
    }
}

struct MainViewControllerWrapper: UIViewControllerRepresentable {
   

    func makeUIViewController(context: Context) -> UIViewController {
        Main_iosKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        // Implement this function if needed
    }
}

