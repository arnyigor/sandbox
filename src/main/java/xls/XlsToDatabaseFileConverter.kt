package xls

class XlsToDatabaseFileConverter(private val list: List<XlsFileData>) {
    fun convert(): String {
        val stringBuilder = StringBuilder()
        for (data in list) {
            stringBuilder.append(
                "INSERT INTO airports(" +
                        "iata,icao,name_rus,name_eng,city_rus,city_eng,country_rus,country_eng,latitude,longitude,elevation" +
                        ") VALUES("
            )
            stringBuilder.append("'${data.comp1 ?: ""}'")
            stringBuilder.append(",'${data.comp2 ?: ""}'")
            stringBuilder.append(",'${data.comp3 ?: ""}'")
            stringBuilder.append(",'${data.comp4 ?: ""}'")
            stringBuilder.append(",'${data.comp5 ?: ""}'")
            stringBuilder.append(",'${data.comp6 ?: ""}'")
            stringBuilder.append(",'${data.comp7 ?: ""}'")
            stringBuilder.append(",'${data.comp8 ?: ""}'")
            stringBuilder.append(",'${data.comp9 ?: ""}'")
            stringBuilder.append(",'${data.comp10 ?: ""}'")
            stringBuilder.append(",'${data.comp11 ?: ""}'")
            stringBuilder.append(");\n")
        }
        return stringBuilder.toString()
    }

}