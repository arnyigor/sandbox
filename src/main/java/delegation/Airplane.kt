package delegation

interface Airplane {
    var canFly: Boolean
    var hasFuel: Boolean
    var parking: Boolean
    fun fly()
    fun fueling()
    fun parking()
}