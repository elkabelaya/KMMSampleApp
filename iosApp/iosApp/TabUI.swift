//
//  TabUI.swift
//  iosApp
//
//  Created by user on 12.09.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TabUI: View {
    let component: TabComponent

    @StateValue
    private var childStack: ChildStack<AnyObject, TabComponentChild>
    private var activeChild: TabComponentChild { childStack.active.instance }
    init(_ component: TabComponent) {
        self.component = component
        _childStack = StateValue(component.childStack)
    }

    var body: some View {
        VStack {
            ChildView(child: activeChild)
                .frame(maxHeight: .infinity)

            HStack(alignment: .bottom, spacing: 16) {
                Spacer()
                Button(action: component.onFirstTabClicked) {
                    Label(title: {
                            Text(String(\.tab_first_title))
                         },
                         icon: {
                             Image(\.tab_first_icon)
                                 .resizable()
                                 .scaledToFit()
                                 .frame(width: 24, height: 24)
                         }
                    )
                    .labelStyle(VerticalLabelStyle())
                    .opacity(activeChild is TabComponentChild.FirstTab ? 1 : 0.5)
                    .font(MR.fontsMikar().mikar.font(14))
                }
                Spacer()
                Button(action: component.onSecondTabClicked) {
                    Label(title: {
                            Text(String(\.tab_second_title))
                         },
                         icon: {
                             Image(\.tab_second_icon)
                             .resizable()
                             .scaledToFit()
                             .frame(width: 24, height: 24)
                         }
                    )
                    .labelStyle(VerticalLabelStyle())
                    .opacity(activeChild is TabComponentChild.SecondTab ? 1 : 0.5)
                    .font(MR.fontsMikar().mikar.font(14))
                }
                Spacer()
            }
        }
    }
}

private struct ChildView: View {
    let child: TabComponentChild

    var body: some View {
        switch child {
        case let child as TabComponentChild.FirstTab:
            FirstTabFlowUI(child.component)
        case let child as TabComponentChild.SecondTab:
            FirstScreenUI(child.component)
        default:
            EmptyView()//недостижимое условие
        }
    }
}

private struct VerticalLabelStyle: LabelStyle {
    func makeBody(configuration: Configuration) -> some View {
        VStack(alignment: .center, spacing: 8) {
            configuration.icon
            configuration.title
        }
    }
}


