import SwiftUI
import shared

struct FirstScreenUI: View {
   private let component: FirstScreenComponent

   @ObservedObject
   private var state: ObservableValue<FirstScreenState>

   init(_ component: FirstScreenComponent) {
       self.component = component
       state = ObservableValue<FirstScreenState>(component.state)
   }

    func getCounterText(quantity: Int32) -> String {
        let counter =  MR.plurals().counter_plural.format(number: quantity, args_: [quantity])
        return MR.strings().first_screen_current_count.format(args_: [counter]).localized()
    }

   var body: some View {
       VStack(spacing: 16) {
           Image(\.frog)
               .resizable()
               .scaledToFit()
               .frame(width: 100, height: 100)
           Text(String(\.first_screen_title))
               .font(MR.fontsMikar().mikar.font(14))
               .foregroundColor(Color(\.title_color))
           Text(getCounterText(quantity: state.value.count))
           HStack {
               Text("\(state.value.count)")
               Button(action: { component.onIncrease() }) {
                   Text("+")
               }
               Button(action: { component.onDecrease() }) {
                   Text("-")
               }

           }
           TextField("Text",
                     text: Binding(get: { state.value.text },
                                   set: component.onChangeText))
           .padding(4)
           .border(.black, width: 2)
           Button(action: { component.onShowAlertClick() }) {
               Text("show Alert")
           }

           Button(action: { component.onNextScreen(value: "\(state.value.count)") }) {
               Text("go next")
           }
        }
        .padding(.horizontal, 16)
        .alert(isPresented: .constant(state.value.showAlert)) {
              Alert(
                  title: Text("title"),
                  message: Text("text"),
                  primaryButton: .default(
                    Text("Got it!"),
                    action: {
                        component.onCloseAlertClick()
                    }),
                  secondaryButton: .default(
                    Text("close"),
                    action: {
                        component.onCloseAlertClick()
                    })
              )
          }
    }
}
