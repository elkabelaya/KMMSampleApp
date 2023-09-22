//
//  ThirdScreenUI.swift
//  iosApp
//
//  Created by user on 22.09.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ThirdScreenUI: View {
    let component: ThirdScreenComponent

    init(_ component: ThirdScreenComponent) {
        self.component = component
    }

    var body: some View {
        VStack {
            Text("This is The Third Screen")
            Button("next screen",
                   action: component.onNextScreen)
            Button("show alert",
                   action: component.onShowAlertClick)
        }.modifier(
            StackView(
                stackValue: StateValue(component.childSlot),
                getTitle: {_ in  "" },
                onBack: component.onCloseClick,
                childContent: {
                    switch $0 {
                    case let thirdFullScreen as ThirdScreenComponentChild.ThirdFullScreen:
                        FourthScreenUI(thirdFullScreen.component)
                    default:
                        EmptyView()
                    }
                },
                alertContent: {
                    switch $0 {
                    case let alert as ThirdScreenComponentChild.ThirdAlert:
                        return AlertUI().getAlert(component: alert.component)
                    default:
                        return AlertUI().getAlert(component: nil)
                    }
                }
            )
        )
    }
}
