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
            val stringByteArray = FileUtils.readFileToString(inFile, StandardCharsets.UTF_8)
            val byteValues = decompress(stringByteArray.hexStringToByteArray()).substring(1, stringByteArray.length - 1).split(",")
            val bytes = ByteArray(byteValues.size)
            var i = 0
            val len: Int = bytes.size
            while (i < len) {
                bytes[i] = byteValues[i].trim { it <= ' ' }.toByte()
                i++
            }
            val file = File(outFilePath)
            FileUtils.writeByteArrayToFile(file, bytes)
        }

        fun fileToString(inFilePath: String, outFilePath: String) {
            try {
                val inFile = File(inFilePath)
                val byteArray = FileUtils.readFileToByteArray(inFile)
                val stringByteArray = Arrays.toString(byteArray)
                val outFile = File(outFilePath)
                FileUtils.writeStringToFile(outFile, compress(stringByteArray).toHex(), StandardCharsets.UTF_8)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}