package patterns.creational.factory_method;

class DeliveryFactory {
    Delivery getDelivery(String type) {
        switch (type) {
            case "car":
                return new CarDelivery();
            case "train":
                return new TrainDelivery();
            default:
                return new NoDelivery();
        }
    }
}
