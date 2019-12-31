package utils

import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

class FileStringUtils {
    companion object {
        fun stringToFile(inFilePath: String, outFilePath: String) {
            val inFile = File(inFilePath)
            val files = inFile.listFiles()
            println("files:${Arrays.toString(files)}")
            val stringByteArray = FileUtils.readFileToString(inFile, StandardCharsets.UTF_8)
            val byteValues = stringByteArray.substring(1, stringByteArray.length - 1).split(",")
            val bytes = ByteArray(byteValues.size)
            var i = 0
            val len: Int = bytes.size
            while (i < len) {
                bytes[i] = byteValues[i].trim { it <= ' ' }.toByte()
                i++
            }
            val file = File(outFilePath)
           /* val exists = file.exists() && file.isFile
            if (!exists) {
                file.createNewFile()
            }*/
            FileUtils.writeByteArrayToFile(file, bytes)
        }

        fun fileToString(inFilePath: String, outFilePath: String) {
            try {
//                val filePath1 = "../KotlinTests/moxy.zip"
//                val fileSave = "../KotlinTests/save.txt"
                val inFile = File(inFilePath)
                val byteArray = FileUtils.readFileToByteArray(inFile)
                val stringByteArray = Arrays.toString(byteArray)
                val outFile = File(outFilePath)
                FileUtils.writeStringToFile(outFile, stringByteArray, StandardCharsets.UTF_8)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}