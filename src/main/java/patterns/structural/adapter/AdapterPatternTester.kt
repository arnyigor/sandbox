package patterns.structural.adapter

import interfaces.Testable

/*
* Адаптер — это структурный паттерн проектирования,
* который позволяет объектам с несовместимыми интерфейсами работать вместе.
*Применимость
* - Когда вы хотите использовать сторонний класс, но его интерфейс не соответствует остальному коду приложения.
*  -  Когда вам нужно использовать несколько существующих подклассов,
* но в них не хватает какой-то общей функциональности, причём расширить суперкласс вы не можете.
* */

class AdapterPatternTester : Testable {
    override fun runTest(args: Array<String>?) {
        val hole = RoundHole(5)
        val roundPeg = RoundPeg(4)
        println("1->${hole.fits(roundPeg)}")
        val smallSq = SquarePeg(5)
        val largeSq = SquarePeg(10)
        println("2->${hole.fits(SquarePegAdapter(smallSq))}")
        println("3->${hole.fits(SquarePegAdapter(largeSq))}")
    }
}