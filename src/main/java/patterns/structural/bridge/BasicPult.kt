package patterns.structural.bridge

open class BasicPult(private val device: Device) : Remote {

    override fun power() {
        if (!device.enabled) {
            device.enable()
        } else {
            device.disable()
        }
        println("Remote power switch: ${device.name()}")
    }

    override fun volumeDown() {
        device.setVolume(device.volume - 10)
        println("Remote volumeDown: ${device.name()}")
    }

    override fun volumeUp() {
        device.setVolume(device.volume + 10)
        println("Remote volumeUp: ${device.name()}")
    }
}