package patterns

import patterns.creational.abstract_factory.AbstractFactoryTester
import patterns.creational.builder.Hero
import patterns.creational.builder.Profession
import patterns.creational.builder.Weapon
import patterns.creational.factory_method.FactoryMethodTester

fun testFactoryMethod(){
    val tester = FactoryMethodTester()
    tester.test()
}
fun testAbstractFactoryMethod(){
    val tester = AbstractFactoryTester()
    tester.test()
}

fun testBuilderMethod(needFire:Boolean = false,needMoney:Boolean = false,needPower:Boolean = false,newName:String? = null){
    val hero = Hero.Builder(Profession.GNOM, "Gimli")
        .withWeapon(Weapon.AXE)
        .build()
    println("First simple hero:$hero")
    val builder = Hero.Builder()
    when{
        needFire->{
            builder.profession = Profession.ARCHER
            builder.withWeapon(Weapon.BOW)
        }
        needMoney->builder.profession = Profession.SELLER
        needPower->{
            builder.profession = Profession.GNOM
            builder.withWeapon(Weapon.SWORD)
        }
    }
    builder.name = newName
    val myHero = builder.build()
    println("Second power hero:$myHero")
}