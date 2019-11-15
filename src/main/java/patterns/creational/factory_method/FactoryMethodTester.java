package patterns.creational.factory_method;

/**
 * Фабричный метод
 * Порожающий шаблон проектирования
 * Определяет интерфейс для создания объекта, но позволяет подклассам решать какой класс интанцировать.
 * Позволяет делегировать создание объекта подклассам
 */
public class FactoryMethodTester {
    public void test(){
        final DeliveryFactory deliveryFactory = new DeliveryFactory();
        deliveryFactory.getDelivery("car").deliver();
        deliveryFactory.getDelivery("train").deliver();
    }
}
