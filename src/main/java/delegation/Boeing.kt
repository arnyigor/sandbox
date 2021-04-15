package delegation

class Boeing : Airplane {
    override var canFly = false
    override var hasFuel = false
    override var parking = true

    override fun fly() {
        println("Boeing is flying")
    }

    override fun fueling() {
        println("Boeing is fueling")
    }

    override fun parking() {
        println()
    }
}