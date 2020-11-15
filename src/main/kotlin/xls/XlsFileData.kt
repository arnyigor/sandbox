package xls

interface XlsFileData {
    operator fun set(index: Int, value: String) {
        when (index) {
            0 -> comp1 = value
            1 -> comp2 = value
            2 -> comp3 = value
            3 -> comp4 = value
            4 -> comp5 = value
            5 -> comp6 = value
            6 -> comp6 = value
            7 -> comp8 = value
            8 -> comp9 = value
            9 -> comp10 = value
            10 -> comp11 = value
        }
    }

    var comp1: String?
    var comp2: String?
    var comp3: String?
    var comp4: String?
    var comp5: String?
    var comp6: String?
    var comp7: String?
    var comp8: String?
    var comp9: String?
    var comp10: String?
    var comp11: String?
}