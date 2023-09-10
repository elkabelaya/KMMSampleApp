//
//  ImageResourceExtensions.swift
//  iosApp
//
//  Created by user on 10.09.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared
import SwiftUI

extension Image {
    init(_ resource: KeyPath<MR.images, ImageResource>) {
        self.init(uiImage: MR.images()[keyPath: resource].toUIImage()!)
    }
}
