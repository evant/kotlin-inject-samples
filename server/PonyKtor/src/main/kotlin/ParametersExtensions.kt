/**
 * Extensions to convert parameters into specific types.
 */
package me.tatarka.inject

import io.ktor.features.ParameterConversionException
import io.ktor.http.Parameters

/**
 * A typealias to make it clear we are accepting query parameters.
 */
typealias QueryParameters = Parameters

/**
 * Returns the parameter with the given name as an `Int` or [default] if it is missing.
 * @throws ParameterConversionException if the parameter was found but cannot be converted to an `Int`.
 */
fun Parameters.getAsInt(name: String, default: Int = 0): Int {
    val value = get(name) ?: return default
    return value.asInt(name)
}

/**
 * Attempts to convert the string to an `Int`.
 * @throws ParameterConversionException if the parameter was found but cannot be converted to an `Int`.
 */
fun String.asInt(name: String): Int {
    try {
        return toInt()
    } catch (e: NumberFormatException) {
        throw ParameterConversionException(name, "Int", e)
    }
}