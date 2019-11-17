package di.transport;

import dagger.Component;

public class TransportApp {

    @Component(modules = {WheelsModule.class, EngineModule.class})
    interface TransportComponent {
        Car getCar();

        Moto getMoto();
    }

    public static void init() {
        TransportComponent transportComponent = DaggerTransportApp_TransportComponent.builder().build();
        Car car = transportComponent.getCar();
        car.run();
        Moto moto = transportComponent.getMoto();
        moto.startEngine();
        moto.run();
    }
}
