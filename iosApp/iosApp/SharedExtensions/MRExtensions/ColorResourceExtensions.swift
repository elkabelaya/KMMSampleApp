//
//  ColorResourceExtensions.swift
//  iosApp
//
//  Created by user on 10.09.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared
import SwiftUI

extension SwiftUI.Color {
     init(_ resource: KeyPath<MR.colors, ColorResource>) {
         self.init(MR.colors()[keyPath: resource].getUIColor())
     }
}
