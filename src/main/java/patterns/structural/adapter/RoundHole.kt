package patterns.structural.adapter

data class RoundHole(val radius: Int) {
    fun fits(roundPeg: RoundPeg): Boolean {
        return roundPeg.getRadius() < radius
    }
}