package patterns.structural.adapter

import kotlin.math.sqrt

class SquarePegAdapter(private val peg: SquarePeg) : RoundPeg(peg.width) {
    override fun getRadius(): Int {
        return (peg.width * sqrt(2.0) / 2).toInt()
    }
}