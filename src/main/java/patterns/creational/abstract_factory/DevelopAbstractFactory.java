package patterns.creational.abstract_factory;

class DevelopAbstractFactory {
    DevelopFactory getDevFactory(String type) {
        switch (type) {
            case "app": return  new AndroidFactory();
            case "cite": return new CiteFactory();
            default: return new CiteFactory();
        }
    }
}
