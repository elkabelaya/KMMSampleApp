import SwiftUI
import shared

struct FirstScreenView: View {

   private let component: FirstScreenComponent

   @ObservedObject
   private var state: ObservableValue<FirstScreenState>

   init(_ component: FirstScreenComponent) {
       self.component = component
       state = ObservableValue<FirstScreenState>(component.state)
   }

   var body: some View {
       HStack {
           Text("\(state.value.count)")
           Button(action: { component.onIncrease() }) {
               Text("+")
           }
           Button(action: { component.onDecrease() }) {
               Text("-")
           }
       }
   }
}
