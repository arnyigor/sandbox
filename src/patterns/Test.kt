package patterns

import patterns.factory_method.ShipManager
import patterns.factory_method.TruckManger

fun testFactoryMethod(){
    val truckManger = TruckManger()
    truckManger.deliver()
    val shipManager = ShipManager()
    shipManager.deliver()
}