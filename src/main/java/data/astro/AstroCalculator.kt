package data.astro

import utils.AstroUtils

class AstroCalculator {
    fun calc(timeInMillis: Long, lat: Double, lon: Double, localZone: Double = 0.0): String {
        val sunRise = AstroUtils.getSunRise(timeInMillis, lat, lon, localZone)
        val sunSet = AstroUtils.getSunSet(timeInMillis, lat, lon, localZone)
        return """
            sunRise:$sunRise
            sunSet:$sunSet
            """.trimIndent()
    }
}