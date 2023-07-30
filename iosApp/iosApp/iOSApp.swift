import SwiftUI
import shared


@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate

    @Environment(\.scenePhase)
    var scenePhase: ScenePhase

    private var appFlowHolder: AppFlowHolder { appDelegate.appFlowHolder }

	var body: some Scene {
		WindowGroup {
            AppFlowUI(appFlowHolder.appFlow)
            .onChange(of: scenePhase) { newPhase in
                switch newPhase {
                case .background: LifecycleRegistryExtKt.stop(appFlowHolder.lifecycle)
                case .inactive: LifecycleRegistryExtKt.pause(appFlowHolder.lifecycle)
                case .active: LifecycleRegistryExtKt.resume(appFlowHolder.lifecycle)
                @unknown default: break
                }
            }
		}
	}
}

class AppDelegate: NSObject, UIApplicationDelegate {
    fileprivate var appFlowHolder: AppFlowHolder = .init()
}

private class AppFlowHolder: ObservableObject {
    let lifecycle: LifecycleRegistry
    let appFlow: AppFlowComponent

    init() {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()

        appFlow = DefaultAppFlowComponent(
            componentContext: DefaultComponentContext(lifecycle: lifecycle)
        )

        LifecycleRegistryExtKt.create(lifecycle)
    }

    deinit {
        // Destroy the root component before it is deallocated
        LifecycleRegistryExtKt.destroy(lifecycle)
    }
}

