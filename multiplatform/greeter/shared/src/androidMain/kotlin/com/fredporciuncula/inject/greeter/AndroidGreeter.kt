package com.fredporciuncula.inject.greeter

import android.content.Context
import android.widget.Toast
import me.tatarka.inject.annotations.Inject

@Inject
class AndroidGreeter(
  private val context: Context,
) : Greeter {
  override fun greet(greeting: String) {
    Toast.makeText(context, greeting, Toast.LENGTH_SHORT).show()
  }
}
