//
//  FirstTabFlowUI.swift
//  iosApp
//
//  Created by user on 29.07.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct FirstTabFlowUI: View {
    let component: FirstTabFlowComponent

    init(_ component: FirstTabFlowComponent) {
        self.component = component
    }

    var body: some View {
        StackView(
            stackValue: StateValue(component.childStack),
            getTitle: {_ in  "" },
            onBack: component.onBackClicked,
            childContent: {
                switch $0 {
                case let first as FirstTabFlowComponentChildFirstScreen:
                    FirstScreenUI(first.component)
                case let second as FirstTabFlowComponentChildSecondScreen:
                    SecondScreenUI(second.component)
                default:
                    EmptyView()
                }
            }
        )
    }
}

