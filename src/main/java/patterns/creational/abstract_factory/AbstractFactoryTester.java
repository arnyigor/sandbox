package patterns.creational.abstract_factory;

/**
 * Абстрактная фабрика — это порождающий паттерн проектирования, который позволяет создавать семейства связанных объектов, не привязываясь к конкретным классам создаваемых объектов.
 */
public class AbstractFactoryTester {
    public void test(){
        final DevelopAbstractFactory factoryCreator = new DevelopAbstractFactory();
        startProcess(factoryCreator.getDevFactory("app"));
        startProcess(factoryCreator.getDevFactory("cite"));
        startProcess(factoryCreator.getDevFactory("no"));
    }

    private void startProcess(DevelopFactory factory){
        factory.createDeveloper().develop();
        factory.createTester().test();
        factory.createManager().manage();
    }
}
