package com.za.filemanagerapp.utils

import java.util.concurrent.TimeUnit

object Utils {
    fun formateDuration(duration: Long):String{
        val minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) -
                minutes* TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES))
        return String.format("%02d:%02d", minutes, seconds)
    }
    fun convertBytesToMegabytes(bytes: Long): Double {
        val megabyte = 1024 * 1024.0
        return bytes / megabyte
    }
}