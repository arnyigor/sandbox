package patterns.factory_method

abstract class TransportManager {
    abstract fun getTransport(): Transport
    fun deliver() {
        getTransport().delivery()
    }
}