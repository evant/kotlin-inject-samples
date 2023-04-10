import SwiftUI
import shared

struct ContentView: View {
  private let greeter = ApplicationComponent.companion.create().greeter

  // Or we can also access the generated implementation directly and drop our companion object
//  private let greeter = InjectApplicationComponent().greeter

  var body: some View {
    Button {
      greeter.greet()
    } label: {
      Text("Greet!")
    }
  }
}

struct ContentView_Previews: PreviewProvider {
  static var previews: some View {
    ContentView()
  }
}
