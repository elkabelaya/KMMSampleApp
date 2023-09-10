//
//  StringResourceExtensions.swift
//  iosApp
//
//  Created by user on 10.09.2023.
//  Copyright © 2023 orgName. All rights reserved.
//
import shared
import SwiftUI

extension String {
     init(_ resource: KeyPath<MR.strings, StringResource>) {
         self.init(MR.strings()[keyPath: resource].desc().localized())
     }
}
