package data.astro

import net.iakovlev.timeshape.TimeZoneEngine
import org.apache.commons.io.FileUtils
import org.joda.time.DateTime
import java.io.File
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.ZoneId

class TimeGeoCalc {
    private var engine = TimeZoneEngine.initialize()
    private val astroCalculator: AstroCalculator = AstroCalculator()
    private fun calcHoursOffset(lat: Double, lon: Double): Double {
        return getOffset(getZone(lat, lon)).toDouble() / 3600
    }

    private fun getOffset(zone: ZoneId?) = (zone?.rules?.getStandardOffset(Instant.EPOCH)?.totalSeconds ?: 0)

    private fun getZone(lat: Double, lon: Double) = engine.queryAll(lat, lon).firstOrNull()

    fun readFile(absolutePath: String) {
        val path = absolutePath.substringBeforeLast(File.separator)
        val filename = absolutePath.substringAfterLast(File.separator)
        val inFilePath = "$path/$filename"
        val inFile = File(inFilePath)
        val lines = FileUtils.readLines(inFile, StandardCharsets.UTF_8)
        for (line in lines.filter { it.contains("UU", true) }) {
            val split = line.split(",")
            val code = split[0]
            val name = split[1]
            val lat = split[2].toDoubleOrNull() ?: 0.0
            val lon = split[3].toDoubleOrNull() ?: 0.0
            val elev = split[4]
            val startDay = DateTime().withTimeAtStartOfDay()
            val calc = astroCalculator.calc(startDay.millis, lat, lon, 0.0)
            println("code:$code,name:$name,lat:$lat,lon:$lon,elev:$elev,startDay:$startDay,calc:$calc")
        }
    }
}