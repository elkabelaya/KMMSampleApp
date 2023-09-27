//
//  StackView.swift
//  iosApp
//
//  Created by user on 30.07.2023.
//  Copyright © 2023 orgName. All rights reserved.
//
import SwiftUI
import UIKit
import shared

@propertyWrapper struct StateValue<T : AnyObject>: DynamicProperty {
     @ObservedObject
     private var obj: ObservableValue<T>

     var wrappedValue: T { obj.value }

     init(_ value: Value<T>) {
         obj = ObservableValue(value)
     }
 }

struct StackView<T: AnyObject, ChildContent: View>: ViewModifier {
    @StateValue
    var stackValue: ChildSlot<AnyObject, T>

    var getTitle: (T) -> String
    var onBack: () -> Void

    @ViewBuilder
    var childContent: (Any?) -> ChildContent

    var alertContent: (Any?) -> Alert

    private var slot: Child<AnyObject, T>? { stackValue.child }

    func body(content: Content) -> some View {
        content
            .background(
                NavigationLink(
                    destination: childContent(stackValue.child?.instance),
                    isActive: Binding(
                        get: {
                            (stackValue.child?.instance as? TypedChild)?.type == ChildType.screen
                        },
                        set: {
                            if !$0 {
                                onBack()
                            }
                        }
                    )
                ) {}
            )
            .alert(
                isPresented: Binding(
                    get: {
                        (stackValue.child?.instance as? TypedChild)?.type == ChildType.alert
                    },
                    set: { if !$0 {
                            onBack()
                        }
                    }
                )
            ) {
                alertContent(stackValue.child?.instance)
            }
            .sheet(
                isPresented: Binding(
                    get: {
                        (stackValue.child?.instance as? TypedChild)?.type == ChildType.bottomsheet
                    },
                    set: { if !$0 {
                            onBack()
                        }
                    }
                )
            ) {
                NavigationView {//to open fullscreen cover from sheet
                    childContent(stackValue.child?.instance)
                }
                .navigationViewStyle(.stack)
            }
            .fullScreenCover(
                isPresented: Binding(
                    get: {
                        (stackValue.child?.instance as? TypedChild)?.type == ChildType.fullscreencover
                    },
                    set: { if !$0 {
                            onBack()
                        }
                    }
                )
            )
            {
                NavigationView {//to navigate in fullScreenCover
                    childContent(stackValue.child?.instance)
                }
                .navigationViewStyle(.stack)
            }
    }
}



private struct StackInteropView<T: AnyObject, Content: View>: UIViewControllerRepresentable {
    var components: [T]
    var getTitle: (T) -> String
    var onBack: (_ toIndex: Int32) -> Void
    var childContent: (T) -> Content

    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }

    func makeUIViewController(context: Context) -> UINavigationController {
        context.coordinator.syncChanges(self)
        let navigationController = UINavigationController(
            rootViewController: context.coordinator.viewControllers.first!)

        return navigationController
    }

    func updateUIViewController(_ navigationController: UINavigationController, context: Context) {
        context.coordinator.syncChanges(self)
        navigationController.setViewControllers(context.coordinator.viewControllers, animated: true)
    }

    private func createViewController(_ component: T, _ coordinator: Coordinator) -> NavigationItemHostingController {
        let controller = NavigationItemHostingController(rootView: childContent(component))
        controller.coordinator = coordinator
        controller.component = component
        controller.onBack = onBack
        controller.navigationItem.title = getTitle(component)
        return controller
    }

    class Coordinator: NSObject {
        var parent: StackInteropView<T, Content>
        var viewControllers = [NavigationItemHostingController]()
        var preservedComponents = [T]()

        init(_ parent: StackInteropView<T, Content>) {
            self.parent = parent
        }

        func syncChanges(_ parent: StackInteropView<T, Content>) {
            self.parent = parent
            let count = max(preservedComponents.count, parent.components.count)

            for i in 0..<count {
                if (i >= parent.components.count) {
                    viewControllers.removeLast()
                } else if (i >= preservedComponents.count) {
                    viewControllers.append(parent.createViewController(parent.components[i], self))
                } else if (parent.components[i] !== preservedComponents[i]) {
                    viewControllers[i] = parent.createViewController(parent.components[i], self)
                }
            }

            preservedComponents = parent.components
        }
    }

    class NavigationItemHostingController: UIHostingController<Content> {
        fileprivate(set) weak var coordinator: Coordinator?
        fileprivate(set) var component: T?
        fileprivate(set) var onBack: ((_ toIndex: Int32) -> Void)?

        override func viewDidAppear(_ animated: Bool) {
            super.viewDidAppear(animated)

            guard let components = coordinator?.preservedComponents else { return }
            guard let index = components.firstIndex(where: { $0 === component }) else { return }

            if (index < components.count - 1) {
                onBack?(Int32(index))
            }
        }
    }
}

// stubs for XCode < 14:
#if compiler(<5.7)
private struct NavigationStack<Path, Root>: View {
    var path: Path
    @ViewBuilder var root: () -> Root
    var body: some View {
        EmptyView()
    }
}

private extension View {
    public func navigationDestination<D, C>(for data: D.Type, @ViewBuilder destination: @escaping (D) -> C) -> some View where D: Hashable, C: View {
        EmptyView()
    }
}
#endif
