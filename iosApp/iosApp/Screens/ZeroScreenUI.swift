//
//  ZeroScreenUI.swift
//  iosApp
//
//  Created by user on 29.07.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ZeroScreenUI: View {
    let component: ZeroScreenComponent

    init(_ component: ZeroScreenComponent) {
        self.component = component
    }

    var body: some View {
        VStack {
            Button(String(\.first_tab_button_first_title),
                   action: component.onFirstClicked)
            Button(String(\.first_tab_button_second_title),
                   action: component.onSecondClicked)
            Button(String(\.first_tab_button_alert_title),
                   action: component.onAlertClicked)
            Button(String(\.first_tab_button_bs_title),
                   action: component.onBottomSheetClicked)

        }.modifier(
            StackView(
                stackValue: StateValue(component.childSlot),
                getTitle: {_ in  "" },
                onBack: component.onBackClicked,
                childContent: {
                    switch $0 {
                    case let first as ZeroScreenComponentChild.FirstScreen:
                        FirstScreenUI(first.component)
                    case let second as ZeroScreenComponentChild.SecondScreen:
                        SecondScreenUI(second.component)
                    case let bottomSheet as ZeroScreenComponentChild.FirstBottomSheet:
                        ThirdScreenUI(bottomSheet.component)
                    default:
                        EmptyView()
                    }
                },
                alertContent: {
                    switch $0 {
                    case let alert as ZeroScreenComponentChild.FirstAlert:
                        return AlertUI().getAlert(component: alert.component)
                    default:
                        return AlertUI().getAlert(component: nil)
                    }
                }
            )
        )
    }
}

