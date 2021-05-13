package patterns.structural.bridge

class AdvancedPult(private val device: Device) : BasicPult(device) {

    fun mute() {
        device.setVolume(0)
        println("Remote mute: ${device.name()}")
    }
}