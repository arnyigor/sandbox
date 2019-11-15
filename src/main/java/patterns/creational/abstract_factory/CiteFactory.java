package patterns.creational.abstract_factory;

public class CiteFactory implements DevelopFactory {
    @Override
    public Developer createDeveloper() {
        return new PHPDeveloper();
    }

    @Override
    public Tester createTester() {
        return new CiteTester();
    }

    @Override
    public Manager createManager() {
        return new CiteManager();
    }
}
