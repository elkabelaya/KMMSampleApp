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

    init(_ component: SecondScreenComponent) {
        self.component = component
        state = ObservableValue<SecondScreenState>(component.state)
    }

    var body: some View {
        Button(action: { component.onBackClick() }) {
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
