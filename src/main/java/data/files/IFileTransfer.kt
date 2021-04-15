package data.files

interface IFileTransfer {
    fun fileToString(absolutePath: String)
    fun stringToFile(absolutePath: String, newFileName: String)
}
