package patterns.structural.bridge

interface Device {
    val enabled: Boolean
    fun enable()
    fun disable()
    val volume: Int
    fun setVolume(percent: Int)
    fun printStatus()
    fun name(): String
}
