package xls

import java.io.File
import java.nio.charset.Charset


class CsvFileReader : MyFileReader {

    override fun readXls(filename: String): List<XlsFileData> {
        val list = mutableListOf<XlsFileData>()
        var data: XlsFileData = XlsData()
        list.add(data)
        readCsvByBuffered(filename) { index, cell ->
            if (index == 0 && !data.comp1.isNullOrBlank()) {
                data = XlsData()
                list.add(data)
            }
            data[index] = cell
        }
        return list
    }

    private fun readCsvByBuffered(filename: String, cellValueRead: (cellCnt: Int, cell: String) -> Unit) {
        val file = File(filename)
        val lines = file.useLines(Charset.forName("Windows-1251")) { it.toList() }
        for (row in lines.withIndex()) {
            if (row.index > 0) {
                for (iv in row.value.split(";").withIndex().iterator()) {
                    cellValueRead(iv.index, iv.value)
                }
            }
        }
    }
}