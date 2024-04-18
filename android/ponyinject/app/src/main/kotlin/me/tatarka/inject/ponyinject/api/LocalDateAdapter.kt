package me.tatarka.inject.ponyinject.api

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.time.LocalDate

/**
 * Converts to and from a [LocalDate], assuming the ISO-8601 format uuuu-MM-dd.
 */
class LocalDateAdapter : JsonAdapter<LocalDate>() {

    override fun fromJson(reader: JsonReader): LocalDate? {
        return if (reader.peek() == JsonReader.Token.NULL) {
            reader.skipValue()
            null
        } else {
            LocalDate.parse(reader.nextString())
        }
    }

    override fun toJson(writer: JsonWriter, value: LocalDate?) {
        if (value == null) {
            writer.nullValue()
        } else {
            writer.value(value.toString())
        }
    }
}