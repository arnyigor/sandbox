package di.transport;

import javax.inject.Inject;

class Car extends AbstractTransport {

    @Inject
    public Car(Engine engine) {
        super(engine);
    }

    @Override
    String getType() {
        return "Car";
    }

    @Override
    protected void go() {
        System.out.println(getType() + " goo");
    }
}
