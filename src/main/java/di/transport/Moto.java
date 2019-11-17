package di.transport;

import javax.inject.Inject;

class Moto extends AbstractTransport {

    @Inject
    Moto(Engine engine) {
        super(engine);
    }

    @Override
    String getType() {
        return "Moto";
    }

    @Override
    protected void go() {
        System.out.println(getType() + " go");
    }
}
