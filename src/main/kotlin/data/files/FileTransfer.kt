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

    override fun stringToFile(){
        val properties = System.getProperties()
        val home = properties.getProperty("user.home")
        val inFilePath = "$home/Desktop/flightlogbook.txt"
        val outFilePath = "$home/Desktop/flightlogbook_new.zip"
        FileStringUtils.stringToFile(inFilePath, outFilePath)
        println("stringToFile...OK")
    }
}