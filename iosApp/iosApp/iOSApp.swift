import SwiftUI
import shared

@main
struct iOSApp: App {
	var FirstScreenComponent = DefaultFirstScreenComponent()

	var body: some Scene {
		WindowGroup {
			FirstScreenView(FirstScreenComponent)
		}
	}
}
