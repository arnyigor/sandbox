package patterns.creational.factory_method;

class NoDelivery implements Delivery {
    @Override
    public void deliver() {
        System.out.println("No delivery");
    }
}
