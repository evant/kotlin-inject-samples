package com.fredporciuncula.inject.greeter.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.fredporciuncula.inject.greeter.CommonGreeter
import me.tatarka.inject.annotations.Component

@Component
abstract class MainActivityComponent(@Component val parent: ApplicationComponent) {
  abstract val greeter: CommonGreeter
}

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val greeter = MainActivityComponent::class.create(applicationComponent).greeter
    setContent {
      Box(
        modifier = Modifier
          .background(color = Color.White)
          .fillMaxSize(),
        contentAlignment = Alignment.Center,
      ) {
        Button(
          onClick = { greeter.greet() }
        ) {
          Text(text = "Greet!")
        }
      }
    }
  }
}
