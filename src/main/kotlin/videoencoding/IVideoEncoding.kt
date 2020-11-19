package videoencoding

import java.io.File


interface IVideoEncoding {
    fun getMediaData(path: String): String
    fun mergeFiles(pathToListOfFiles: String, fileName: String): File
}
