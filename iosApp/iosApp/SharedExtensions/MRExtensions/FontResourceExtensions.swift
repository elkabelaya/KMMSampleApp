//
//  FontResourceExtensions.swift
//  iosApp
//
//  Created by user on 10.09.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared
import SwiftUI

extension FontResource {
    func font(_ size: Double) -> Font {
        return Font(self.uiFont(withSize: size))
    }
}
