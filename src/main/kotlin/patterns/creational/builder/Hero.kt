package patterns.creational.builder


class Hero private constructor(builder: Builder) {
    private var profession: Profession? = null
    private var name: String? = null
    private var weapon: Weapon? = null

    init {
        this.profession = builder.profession
        this.name = builder.name
        this.weapon = builder.weapon
    }

    class Builder(var profession: Profession?, var name: String?) {
        var weapon: Weapon? = null
        constructor() : this(Profession.GNOM, "")

        fun withWeapon(weapon: Weapon): Builder {
            this.weapon = weapon
            return this
        }

        fun build(): Hero {
            require(!(profession == null || name == null)) { "profession and name can not be null" }
            return Hero(this)
        }
    }

    override fun toString(): String {
        return "Hero(profession=$profession, name=$name, weapon=$weapon)"
    }
}

enum class Profession {
    GNOM,
    ARCHER,
    PROGER,
    SELLER
}

enum class Weapon {
    AXE,
    BOW,
    SWORD
}
