//
//  SecondScreenUI.swift
//  iosApp
//
//  Created by user on 30.07.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SecondScreenUI: View {
    private let component: SecondScreenComponent

    @ObservedObject
    private var state: ObservableValue<SecondScreenState>
    private var onBackClicked: () -> Void
    init(_ component: SecondScreenComponent,
         onBackClicked: @escaping () -> Void) {
        self.component = component
        state = ObservableValue<SecondScreenState>(component.state)
        self.onBackClicked = onBackClicked
    }

    var body: some View {
        Button(action: onBackClicked) {
            Text("go back")
        }
        .toolbar {
            ToolbarItem(placement: .principal) {
                Image(systemName: "photo.fill")
                    .foregroundColor(.green)
            }
        }
    }
}
