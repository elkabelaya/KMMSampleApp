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
    @ObservedObject
    private var childStack: ObservableValue<ChildStack<AnyObject, AppFlowComponentChild>>

    init(_ component: AppFlowComponent) {
        self.component = component
        self.childStack = ObservableValue(component.childStack)
    }

    var body: some View {
        switch childStack.value.active.instance {
        case let first as AppFlowComponentChildFirstScreen:
            FirstScreenUI(first.component)
        case let second as AppFlowComponentChildSecondScreen:
            SecondScreenUI(second.component)
        default:
            EmptyView()
        }
    }
}

