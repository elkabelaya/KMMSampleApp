//
//  FourthScreenUI.swift
//  iosApp
//
//  Created by user on 22.09.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct FourthScreenUI: View {
    let component: FourthScreenComponent

    init(_ component: FourthScreenComponent) {
        self.component = component
    }
    var body: some View {
        VStack {
            Text("This is a forth screen")
            Button("close",
                   action: component.onCloseClick)
            Button("go to first screen",
                   action: component.onNextClick)
        }
        .modifier(
            StackView(
                stackValue: StateValue(component.childSlot),
                getTitle: {_ in  "" },
                onBack: component.onBackClick,
                childContent: {
                    switch $0 {
                    case let first as FourthScreenComponentChild.FirstScreen:
                        FirstScreenUI(first.component)
                    default:
                        EmptyView()
                    }
                },
                alertContent: {_ in 
                    return AlertUI().getAlert(component: nil)
                }
            )
        )
    }
}
