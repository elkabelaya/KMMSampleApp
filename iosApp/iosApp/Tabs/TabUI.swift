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
    @ObservedObject
    private var state: ObservableValue<TabState>

    init(_ component: TabComponent) {
        self.component = component
        state = ObservableValue<TabState>(component.state)
    }

    var body: some View {
        VStack {
            TabView(selection: Binding(get: { state.value.selectedTab },
                                       set: {value, _ in
                component.onTabClicked(index: value)


            })){
                ForEach( component.tabs, id:\.self ){ tabConfig in
                    ChildView(child: tabConfig)
                        .tabItem {
                            VStack {
                                /*WTF?
                                 getImage(child:tabConfig)
                                 */
                                Text(getTitle(child:tabConfig))
                            }

                        }
                        .tag(tabConfig.index)
                }
            }
            .accentColor(.purple)
            .onAppear() {
                UITabBar.appearance().tintColor = .purple
                UITabBar.appearance().unselectedItemTintColor = .darkGray
                UITabBarItem.appearance().setTitleTextAttributes([NSAttributedString.Key.font: MR.fontsMikar().mikar.uiFont(withSize: 14)], for: .normal)
                UITabBarItem.appearance().setTitleTextAttributes([NSAttributedString.Key.font: MR.fontsMikar().mikar.uiFont(withSize: 14)], for: .selected)
            }
        }
    }

    func getTitle(child: BaseTabComponentChild) -> String {
        switch child {
        case is BaseTabComponentChild.FirstTab:
            return String(\.tab_first_title)
        case is BaseTabComponentChild.SecondTab:
            return String(\.tab_second_title)
        default:
            return ""
        }
    }

    func getImage(child: BaseTabComponentChild) -> Image {
        switch child {
        case is BaseTabComponentChild.FirstTab:
            return Image(\.frog)
        default:
            return Image(\.tab_second_icon)
        }
    }

}

private struct ChildView: View {
    let child: BaseTabComponentChild

    var body: some View {
        switch child {
        case let child as BaseTabComponentChild.FirstTab:
            ZeroScreenUI(child.component)
        case let child as BaseTabComponentChild.SecondTab:
            Text("TODO")
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


