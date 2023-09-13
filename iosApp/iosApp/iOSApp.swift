import SwiftUI
import shared


@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate

    @Environment(\.scenePhase)
    var scenePhase: ScenePhase

    private var FirstTabFlowHolder: FirstTabFlowHolder { appDelegate.FirstTabFlowHolder }

	var body: some Scene {
		WindowGroup {
            TabUI(FirstTabFlowHolder.FirstTabFlow)
            .onChange(of: scenePhase) { newPhase in
                switch newPhase {
                case .background: LifecycleRegistryExtKt.stop(FirstTabFlowHolder.lifecycle)
                case .inactive: LifecycleRegistryExtKt.pause(FirstTabFlowHolder.lifecycle)
                case .active: LifecycleRegistryExtKt.resume(FirstTabFlowHolder.lifecycle)
                @unknown default: break
                }
            }
		}
	}
}

class AppDelegate: NSObject, UIApplicationDelegate {
    fileprivate var FirstTabFlowHolder: FirstTabFlowHolder = .init()
}

private class FirstTabFlowHolder: ObservableObject {
    let lifecycle: LifecycleRegistry
    let FirstTabFlow: TabComponent

    init() {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()

        FirstTabFlow = DefaultTabComponent(
            componentContext: DefaultComponentContext(lifecycle: lifecycle)
        )

        LifecycleRegistryExtKt.create(lifecycle)
    }

    deinit {
        // Destroy the root component before it is deallocated
        LifecycleRegistryExtKt.destroy(lifecycle)
    }
}

