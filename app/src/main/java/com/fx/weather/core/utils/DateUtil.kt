package com.fx.weather.core.utils

import android.annotation.SuppressLint
import android.util.Log
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object DateUtil {
    @SuppressLint("SimpleDateFormat")
    fun get24hoursFormatTime(timeString: String): String? {

        val format12Hrs = SimpleDateFormat("h:mm a")
        val format24Hrs = SimpleDateFormat("HH")

        return try {
            val d1: Date = format12Hrs.parse(timeString)!!
            format24Hrs.format(d1)
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }

    fun get24hoursFormatTime(timeStamp: Int): String {
        val format12Hrs = SimpleDateFormat("h:mm a")
        val format24Hrs = SimpleDateFormat("HH:mm")

        return try {
            val d1 = Date(timeStamp * 1000L)
            format24Hrs.format(d1)
        } catch (e: Exception) {
            Timber.e(e)
            "Error"
        }
    }

    /**
     * Convert date string into date object
     * @param date
     */
    fun getDateFromString(date: String, format: String): Date? {
        val simpleDateFormat = SimpleDateFormat(format)
        Log.e("Date format", "format $simpleDateFormat")
        return try {
            simpleDateFormat.parse(date)
        } catch (e: ParseException) {
            Log.e("Date object", "Error $e")
            null
        }
    }

    /**
     * Convert date object into string object
     * @param date
     * @param format
     */
    fun getFormattedDate(date: Date, format: String): String {
        try {
            val formatter = SimpleDateFormat(format, Locale.getDefault())
            return formatter.format(date)

        } catch (e: Exception) {

        }
        return ""
    }

    /**
     * convert 24 format time to 12 hr format
     * @param time
     * @see SimpleDateFormat
     *
     */
    fun convertTo12Hours(time: String): String {
        try {
            val inputFormat = SimpleDateFormat("HH", Locale.getDefault())
            val outputFormat = SimpleDateFormat("h aa", Locale.getDefault())
            val date = inputFormat.parse(time)
            return outputFormat.format(date!!)
        } catch (e: Exception) {
            Log.e("TAG", "Exception in getting 12 hr format: $e")
        }
        return ""
    }
}
