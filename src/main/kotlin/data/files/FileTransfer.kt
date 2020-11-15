package data.files

import utils.FileStringUtils
import utils.FilesUtils
import java.io.File

class FileTransfer : IFileTransfer {
    override fun fileToString(absolutePath: String) {
        val path = absolutePath.substringBeforeLast(File.separator)
        val filename = absolutePath.substringAfterLast(File.separator)
        val name = filename.substringBefore(".")
        val inFilePath = "$path/$filename"
        val outFilePath = "$path/$name.txt"
        FileStringUtils.fileToString(inFilePath, outFilePath)
        val file = File(outFilePath)
        println("fileToString...OK...$file...${FilesUtils.formatFileSize(file.length(), 3)}")
    }

    override fun stringToFile(absolutePath: String, newFileName: String) {
        val path = absolutePath.substringBeforeLast(File.separator)
        val filename = absolutePath.substringAfterLast(File.separator)
        val inFilePath = "$path/$filename"
        val outFilePath = "$path/$newFileName"
        FileStringUtils.stringToFile(inFilePath, outFilePath)
        val file = File(outFilePath)
        println("stringToFile...OK...$file...${FilesUtils.formatFileSize(file.length(), 3)}")
    }
}