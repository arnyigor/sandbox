package patterns.factory_method

class TruckManger:TransportManager() {
    override fun getTransport(): Transport {
        return Truck()
    }
}