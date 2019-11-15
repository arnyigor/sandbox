package patterns.creational.abstract_factory;

public class AndroidFactory  implements DevelopFactory{
    @Override
    public Developer createDeveloper() {
        return new AndroidDeveloper();
    }

    @Override
    public Tester createTester() {
        return new AppTester();
    }

    @Override
    public Manager createManager() {
        return new AndroidManager();
    }
}
