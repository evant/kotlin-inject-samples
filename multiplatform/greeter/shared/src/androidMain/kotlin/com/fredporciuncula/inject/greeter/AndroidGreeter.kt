package com.fredporciuncula.inject.greeter

import android.content.Context
import android.widget.Toast

class AndroidGreeter(
  private val context: Context,
) : Greeter {
  override fun greet(greeting: String) {
    Toast.makeText(context, greeting, Toast.LENGTH_SHORT).show()
  }
}
