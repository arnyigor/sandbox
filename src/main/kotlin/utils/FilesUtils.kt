package utils

import utils.FilesUtils.formatFileSize
import java.io.File

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
