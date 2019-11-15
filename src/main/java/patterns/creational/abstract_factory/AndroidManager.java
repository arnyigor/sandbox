package patterns.creational.abstract_factory;

class AndroidManager implements Manager {
    @Override
    public void manage() {
        System.out.println("Manage android app");
    }
}
