package utils

import java.io.*
import java.util.*

fun saveAppSettings(
    properties: List<Pair<String, String?>> = emptyList(),
    pathName: String = "config.properties",
    comments: String? = null
) {
    val configFile = File(System.getProperty("user.dir"), pathName)
    try {
        val props = Properties()
        properties.forEach { (key, value) ->
            props.setProperty(key, value)
        }
        val writer = FileWriter(configFile)
        props.store(writer, comments)
        writer.close()
    } catch (ex: FileNotFoundException) {
        ex.printStackTrace()
    } catch (ex: IOException) {
        ex.printStackTrace()
    }
}

fun loadAppSettings(
    pathName: String = "config.properties",
    vararg propertiesKeys: String?
): Map<String, String> {
    val configFile = File(System.getProperty("user.dir"), pathName)
    val result = mutableMapOf<String, String>()
    try {
        val reader = FileReader(configFile)
        val props = Properties()
        props.load(reader)
        propertiesKeys.forEach { key ->
            key?.let {
                props.getProperty(key)?.let { property ->
                    result[key] = property
                }
            }
        }
        reader.close()
    } catch (ex: FileNotFoundException) {
        ex.printStackTrace()
    } catch (ex: IOException) {
        ex.printStackTrace()
    }
    return result
}