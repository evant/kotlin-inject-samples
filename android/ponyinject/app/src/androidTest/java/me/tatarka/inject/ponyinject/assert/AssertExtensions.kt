package me.tatarka.inject.ponyinject.assert

import android.widget.TextView
import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop

fun Assert<TextView>.hasText(text: String) =
    prop("text") { it.text.toString() }.isEqualTo(text)