package utils

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

fun File.isFileExist(): Boolean {
    return fileExist(this)
}

fun File.formattedFileSize(digits: Int = 3): String? {
    return try {
        formatFileSize(this.length(), digits)
    } catch (e: Exception) {
        "0"
    }
}

fun fileExist(file: File): Boolean {
    val hasFile = file.exists() && file.isFile
    if (!hasFile) {
        file.createNewFile()
    }
    return file.exists() && file.isFile
}

@Throws(IOException::class)
fun writeToFile(writeFile: File, content: String, append: Boolean): Boolean {
    val bufferedWriter = BufferedWriter(FileWriter(writeFile, append))
    bufferedWriter.write(content)
    bufferedWriter.close()
    return true
}

fun formatFileSize(size: Long, digits: Int): String? {
    if (size <= 0) return "0"
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
    val digs = StringBuilder()
    for (i in 0 until digits) {
        digs.append("#")
    }
    return (DecimalFormat("#,##0.$digs").format(size / 1024.0.pow(digitGroups.toDouble())) + " " + units[digitGroups])
}