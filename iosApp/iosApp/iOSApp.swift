import SwiftUI
import shared


@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate

    @Environment(\.scenePhase)
    var scenePhase: ScenePhase

    private var ZeroScreenHolder: ZeroScreenHolder { appDelegate.ZeroScreenHolder }

	var body: some Scene {
		WindowGroup {
            TabUI(ZeroScreenHolder.ZeroScreen)
            .onChange(of: scenePhase) { newPhase in
                switch newPhase {
                case .background: LifecycleRegistryExtKt.stop(ZeroScreenHolder.lifecycle)
                case .inactive: LifecycleRegistryExtKt.pause(ZeroScreenHolder.lifecycle)
                case .active: LifecycleRegistryExtKt.resume(ZeroScreenHolder.lifecycle)
                @unknown default: break
                }
            }
		}
	}
}

class AppDelegate: NSObject, UIApplicationDelegate {
    fileprivate var ZeroScreenHolder: ZeroScreenHolder = .init()
}

private class ZeroScreenHolder: ObservableObject {
    let lifecycle: LifecycleRegistry
    let ZeroScreen: TabComponent

    init() {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()

        ZeroScreen = DefaultTabComponent(
            componentContext: DefaultComponentContext(lifecycle: lifecycle)
        )

        LifecycleRegistryExtKt.create(lifecycle)
    }

    deinit {
        // Destroy the root component before it is deallocated
        LifecycleRegistryExtKt.destroy(lifecycle)
    }
}

