package com.fx.weather.core.utils.extensions

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Double.roundOffDecimal(): Double {
    val df = DecimalFormat("#.#", DecimalFormatSymbols(Locale.ENGLISH))
    df.roundingMode = RoundingMode.CEILING
    return df.format(this).toDouble()
}