package patterns.factory_method

class ShipManager :TransportManager(){
    override fun getTransport(): Transport {
        return Ship()
    }
}