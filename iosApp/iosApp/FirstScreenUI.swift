import SwiftUI
import shared

struct FirstScreenUI: View {
   private let component: FirstScreenComponent
   @State var isPresented: Bool = false
   @State var isBSPresented: Bool = false
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
           Button(action: { isBSPresented = true }) {
               Text("sheet")
           }
        }
        .padding(.horizontal, 16)
        .toolbar{
            ToolbarItem(placement: .principal) {
                Text(String(\.first_screen_title))
                    .font(MR.fontsMikar().mikar.font(14))
                    .foregroundColor(Color(\.title_color))
            }
        }
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
        .sheet(isPresented: $isBSPresented) {
            NavigationView {
                Text("sheet")
                    .onTapGesture {
                        isPresented = true
                    }
                    .fullScreenCover(isPresented: $isPresented) {
                        Text("fullScreenCover")
                            .onTapGesture {
                                isPresented = false
                            }
                    }
            }
        }

    }
}
