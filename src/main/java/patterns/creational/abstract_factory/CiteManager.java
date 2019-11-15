package patterns.creational.abstract_factory;

class CiteManager implements Manager {
    @Override
    public void manage() {
        System.out.println("Manage site");
    }
}
