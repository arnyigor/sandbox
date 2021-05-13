package patterns.creational.builder

import interfaces.Testable

class BuilderTester : Testable {
    override fun runTest(args: Array<String>?) {
        val needFire: Boolean = false
        val needMoney: Boolean = false
        val needPower: Boolean = false
        val newName: String? = null
        val hero = Hero.Builder(Profession.GNOM, "Gimli")
            .withWeapon(Weapon.AXE)
            .build()
        println("First simple hero:$hero")
        val myHero = Hero.Builder().apply {
            when {
                needFire -> {
                    profession = Profession.ARCHER
                    withWeapon(Weapon.BOW)
                }
                needMoney -> profession = Profession.SELLER
                needPower -> {
                    profession = Profession.GNOM
                    withWeapon(Weapon.SWORD)
                }
            }
            name = newName
        }.build()
        println("Second power hero:$myHero")
    }
}