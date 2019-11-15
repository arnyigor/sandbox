package patterns.creational.factory_method;

class TrainDelivery implements Delivery {
    @Override
    public void deliver() {
        System.out.println("Delivery by train");
    }
}
