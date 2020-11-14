package utils

import java.io.File

fun File.isFileExist(): Boolean {
    return fileExist(this)
}

fun fileExist(file: File): Boolean {
    val hasFile = file.exists() && file.isFile
    if (!hasFile) {
        file.createNewFile()
    }
    return file.exists() && file.isFile
}