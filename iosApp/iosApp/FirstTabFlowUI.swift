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
        VStack {
            Button(String(\.first_tab_button_first_title),
                   action: component.onFirstClicked)
            Button(String(\.first_tab_button_second_title),
                   action: component.onSecondClicked)
        }.modifier(
            StackView(
                stackValue: StateValue(component.childSlot),
                getTitle: {_ in  "" },
                onBack: component.onBackClicked,
                childContent: {
                    switch $0 {
                    case let first as FirstTabFlowComponentChildFirstScreen:
                        FirstScreenUI(first.component, onBackClicked: component.onBackClicked)
                    case let second as FirstTabFlowComponentChildSecondScreen:
                        SecondScreenUI(second.component, onBackClicked: component.onBackClicked)
                    default:
                        EmptyView()
                    }
                }

            )
        )
    }
}

