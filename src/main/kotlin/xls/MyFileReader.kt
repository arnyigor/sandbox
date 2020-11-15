package xls

interface MyFileReader {
    fun readXls(filename: String): List<XlsFileData>
}
