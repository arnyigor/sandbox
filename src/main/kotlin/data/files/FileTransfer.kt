package data.files

import utils.FileStringUtils
import java.io.File

class FileTransfer: IFileTransfer {
    override fun fileToString(absolutePath: String) {
        val path = absolutePath.substringBeforeLast(File.separator)
        val filename = absolutePath.substringAfterLast(File.separator)
        val name = filename.substringBefore(".")
        val inFilePath = "$path/$filename"
        val outFilePath = "$path/$name.txt"
        FileStringUtils.fileToString(inFilePath, outFilePath)
        println("fileToString...$outFilePath...OK")
    }

    override fun stringToFile(absolutePath: String, newFileName: String) {
        val path = absolutePath.substringBeforeLast(File.separator)
        val filename = absolutePath.substringAfterLast(File.separator)
        val inFilePath = "$path/$filename"
        val outFilePath = "$path/$newFileName"
        FileStringUtils.stringToFile(inFilePath, outFilePath)
        println("stringToFile...OK")
    }
}