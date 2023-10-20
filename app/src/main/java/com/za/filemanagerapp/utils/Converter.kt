package com.za.filemanagerapp.utils

object Converter {
    private var kilo: Long = 1024
    private var mega = kilo * kilo
    private var giga = mega * kilo
    private var tera = giga * kilo
    @JvmStatic
    fun main(args: Array<String>) {
        for (arg in args) {
            try {
                println(getSize(arg.toLong()))
            } catch (e: NumberFormatException) {
                println("$arg is not a long")
            }
        }
    }

    fun getSize(size: Long): String {
        var s = ""
        val kb = size.toDouble() / kilo
        val mb = kb / kilo
        val gb = mb / kilo
        val tb = gb / kilo
        s = if (size < kilo) {
            "$size Bytes"
        } else if (size in kilo until mega) {
            String.format("%.2f", kb) + " KB"
        } else if (size in mega until giga) {
            String.format("%.2f", mb) + " MB"
        } else if (size in giga until tera) {
            String.format("%.2f", gb) + " GB"
        } else {
            String.format("%.2f", tb) + " TB"
        }
        return s
    }
}