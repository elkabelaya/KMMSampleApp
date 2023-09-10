//
//  StringResourceExtensions.swift
//  iosApp
//
//  Created by user on 10.09.2023.
//  Copyright © 2023 orgName. All rights reserved.
//
import shared

extension shared.StringResource {
    func value() -> String {
        return self.desc().localized()
    }
}
