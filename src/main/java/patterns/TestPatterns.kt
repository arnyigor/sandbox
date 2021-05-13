package patterns

import patterns.behavioral.ChanPetternTest
import patterns.creational.abstract_factory.AbstractFactoryTester
import patterns.creational.builder.BuilderTester
import patterns.creational.factory_method.FactoryMethodTester
import patterns.structural.adapter.AdapterPatternTester
import patterns.structural.bridge.BridgePatternTester

fun main() {
    when ("ChanPetternTest") {
        "FactoryMethodTester" -> FactoryMethodTester()
        "AbstractFactoryTester" -> AbstractFactoryTester()
        "BuilderTester" -> BuilderTester()
        "AdapterPatternTester" -> AdapterPatternTester()
        "BridgePatternTester" -> BridgePatternTester()
        "ChanPetternTest" -> ChanPetternTest()
        else -> FactoryMethodTester()
    }.runTest()
}
