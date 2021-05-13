package patterns.structural.bridge

class TV : Device {
    var on = false
    var curVolume = 0

    override fun name(): String {
        return "TV"
    }

    override val enabled: Boolean
        get() = on

    override fun enable() {
        on = true
    }

    override fun disable() {
        on = false
    }

    override val volume: Int
        get() = curVolume

    override fun setVolume(percent: Int) {
        if (!enabled) return
        when {
            curVolume > 30 -> {
                this.curVolume = 30
            }
            volume < 0 -> {
                this.curVolume = 0
            }
            else -> {
                this.curVolume = percent
            }
        }
    }

    override fun printStatus() {
        println("------------------------------------")
        println("| I'm TV.")
        println("| I'm " + if (on) "enabled" else "disabled")
        println("| Current volume is $volume%")
        println("------------------------------------\n")
    }
}
