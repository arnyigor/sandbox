package patterns.creational.abstract_factory;

import interfaces.Testable;
import org.jetbrains.annotations.Nullable;

/**
 * Абстрактная фабрика — это порождающий паттерн проектирования, который позволяет создавать семейства связанных объектов, не привязываясь к конкретным классам создаваемых объектов.
 */
public class AbstractFactoryTester implements Testable {

    @Override
    public void runTest(@Nullable String[] args) {
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
