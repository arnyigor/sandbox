package patterns.structural.adapter

open class RoundPeg(private val radius: Int) {
    open fun getRadius(): Int = radius
}
