package patterns.creational.factory_method;

class CarDelivery implements Delivery {
    @Override
    public void deliver() {
        System.out.println("Delivery by car");
    }
}
