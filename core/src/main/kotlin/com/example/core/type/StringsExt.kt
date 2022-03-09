package com.example.core.type

import com.example.core.common.hexColorRegex
import java.util.*
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt

fun String.countryCodeToFlagEmoji(): String {
    if (this.length != 2) {
        return this
    }

    if (!this[0].isLetter() || !this[1].isLetter()) {
        return this
    }

    val countryCodeCaps = this.uppercase(Locale.getDefault())
    val firstLetter = Character.codePointAt(countryCodeCaps, 0) - 0x41 + 0x1F1E6
    val secondLetter = Character.codePointAt(countryCodeCaps, 1) - 0x41 + 0x1F1E6

    return firstLetter.unicodeToString() + secondLetter.unicodeToString()
}

fun String?.isValidHexColor() = this?.matches(hexColorRegex) ?: false

fun String.applyHtmlFitScreenWidthHead(): String =
    "<head><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head>$this</body></html>"

fun String.applyHtmlFitScreenWidthImageStyle() =
    "<style>img{display: inline;height: auto;max-width: 100%;}</style>$this"

/**
 * #String #Transformation
 *
 * If string ends with [substring], drop the ending
 */
fun String.dropLast(substring: String, ignoreCase: Boolean = false) =
    if (this.endsWith(substring, ignoreCase)) {
        dropLast(substring.length)
    } else this

/**
 * #String #Transformation
 *
 * Returns a string without all instances of [symbols] at the beginning of it
 */
fun String.dropLeading(vararg symbols: Char): String = dropWhile { it in symbols }

/**
 * #String #Transformation
 *
 * Replaces all spaces in line to non-breaking spaces
 */
fun String.nbsp() = replace(" ", "\u00A0")

fun Int.abbreviateNumber(): String {
    if (this <= 99999) return this.toString()
    val tier = log10(this.toDouble()).roundToInt() / 3 or 0
    if (tier == 0) return this.toString()
    val suffix: String = listOf("", "K", "M", "G", "T", "P", "E")[tier]
    val scale: Double = 10.0.pow(tier * 3.toDouble())
    val scaled: Double = this / scale
    return "${scaled.roundToInt()} $suffix"
}
