package patterns.creational.factory_method;

import interfaces.Testable;
import org.jetbrains.annotations.Nullable;

/**
 * Фабричный метод
 * Порожающий шаблон проектирования
 * Определяет интерфейс для создания объекта, но позволяет подклассам решать какой класс интанцировать.
 * Позволяет делегировать создание объекта подклассам
 */
public class FactoryMethodTester implements Testable {
    @Override
    public void runTest(@Nullable String[] args) {
        final DeliveryFactory deliveryFactory = new DeliveryFactory();
        deliveryFactory.getDelivery("car").deliver();
        deliveryFactory.getDelivery("train").deliver();
    }
}
