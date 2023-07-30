//
//  AppFlowUI.swift
//  iosApp
//
//  Created by user on 29.07.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct AppFlowUI: View {
    let component: AppFlowComponent

    init(_ component: AppFlowComponent) {
        self.component = component
    }

    var body: some View {
        StackView(
            stackValue: StateValue(component.childStack),
            getTitle: {_ in  "" },
            onBack: component.onBackClicked,
            childContent: {
                switch $0 {
                case let first as AppFlowComponentChildFirstScreen:
                    FirstScreenUI(first.component)
                case let second as AppFlowComponentChildSecondScreen:
                    SecondScreenUI(second.component)
                default:
                    EmptyView()
                }
            }
        )
    }
}

