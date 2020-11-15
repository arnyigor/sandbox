package xls

import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import java.io.File
import java.io.FileInputStream

class XlsFileReader : MyFileReader {

    override fun readXls(filename: String): List<XlsFileData> {
        val list = mutableListOf<XlsFileData>()
        var data: XlsFileData = XlsData()
        list.add(data)
        readCells(filename) { index, cell ->
            if (index == 0 && !data.comp1.isNullOrBlank()) {
                data = XlsData()
                list.add(data)
            }
            try {
                data[index] = when (cell.cellType) {
                    Cell.CELL_TYPE_NUMERIC -> cell.numericCellValue.toString()
                    Cell.CELL_TYPE_STRING -> cell.stringCellValue.toString()
                    Cell.CELL_TYPE_FORMULA -> cell.cellFormula.toString()
                    else -> cell.toString()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return list
    }

    private fun readCells(filename: String, cellReadFunction: (index: Int, cell: HSSFCell) -> Unit) {
        readRows(HSSFWorkbook(FileInputStream(File(filename))).getSheetAt(0).rowIterator(), cellReadFunction)
    }

    private fun readRows(rowIter: Iterator<*>, cellValueRead: (cellCnt: Int, cell: HSSFCell) -> Unit) {
        for (ind in rowIter.withIndex()) {
            val rowIndex = ind.index
            if (rowIndex > 0) {
                val myRow = ind.value as HSSFRow
                val cellIter = myRow.cellIterator()
                for (indexed in cellIter.withIndex()) {
                    val cellIndex = indexed.index
                    val myCell = indexed.value as HSSFCell
                    val hasBlankLine = cellIndex == 0 && myCell.toString().isBlank()
                    cellValueRead(cellIndex, myCell)
                    if (hasBlankLine) {
                        break
                    }
                }
            }
        }
    }
}