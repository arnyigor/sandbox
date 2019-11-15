package patterns.creational.abstract_factory;


public interface DevelopFactory {
    Developer createDeveloper();
    Tester createTester();
    Manager createManager();
}
