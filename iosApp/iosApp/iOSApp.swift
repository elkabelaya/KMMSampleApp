import SwiftUI
import shared

class LifecycleLifecycleTemp: LifecycleLifecycle {
    func subscribe(callbacks: LifecycleLifecycleCallbacks) {
        //
    }

    func unsubscribe(callbacks: LifecycleLifecycleCallbacks) {
        //
    }

    var state: LifecycleLifecycleState = .created
}

@main
struct iOSApp: App {
	var appFlowComponent = RealAppFlowComponent(
        componentContext: DefaultComponentContext(
            lifecycle: LifecycleLifecycleTemp()
        )
    )

	var body: some Scene {
		WindowGroup {
			AppFlowUI(appFlowComponent)
		}
	}
}
