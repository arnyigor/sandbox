package patterns.creational.abstract_factory

internal class DevelopAbstractFactory {
    fun getDevFactory(type: String?) = when (type) {
        "app" -> AndroidFactory()
        "cite" -> CiteFactory()
        else -> CiteFactory()
    }
}