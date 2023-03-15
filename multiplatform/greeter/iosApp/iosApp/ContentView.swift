import SwiftUI
import shared

struct ContentView: View {
  var body: some View {
    Button {
      // trigger greeting
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
