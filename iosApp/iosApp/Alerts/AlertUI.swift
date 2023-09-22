//
//  AlertUI.swift
//  iosApp
//
//  Created by user on 21.09.2023.
//  Copyright © 2023 orgName. All rights reserved.
//
import SwiftUI
import shared

struct AlertUI {
    func getAlert(component: AlertComponent?) -> Alert {
        return Alert(title: Text(component?.title ?? ""),
                     message: Text(component?.message ?? ""),
                     primaryButton: .default(
                        Text(component?.confirm ?? ""),
                        action: {component?.onConfirm()}),
                     secondaryButton: .cancel(
                        Text(component?.dismiss ?? ""),
                        action: {component?.onDismiss()}
                     )
        )
    }
}
