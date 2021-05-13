package patterns.structural.bridge

import interfaces.Testable

/*
Мост — это структурный паттерн проектирования,
который разделяет один или несколько классов на две отдельные иерархии — абстракцию и реализацию,
позволяя изменять их независимо друг от друга.

Применимость
 - Когда вы хотите разделить монолитный класс,
который содержит несколько различных реализаций какой-то функциональности
(например, если класс может работать с разными системами баз данных).
 - Когда вы хотите, чтобы реализацию можно было бы изменять во время выполнения программы.
*/
class BridgePatternTester : Testable {
    override fun runTest(args: Array<String>?) {
        testDevice(TV())
        testDevice(Radio())
    }

    fun testDevice(device: Device) {
        println("Tests with basic remote.")
        val basicRemote = BasicPult(device)
        basicRemote.power()
        device.printStatus()
        println("Tests with advanced remote.")
        val advancedRemote = AdvancedPult(device)
        advancedRemote.power()
        advancedRemote.power()
        device.printStatus()
        advancedRemote.volumeUp()
        device.printStatus()
        advancedRemote.mute()
        device.printStatus()
    }
}