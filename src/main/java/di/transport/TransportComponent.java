package di.transport;

import dagger.Component;

@Component(modules = {WheelsModule.class, EngineModule.class})
interface TransportComponent {
    Car getCar();

    Moto getMoto();
}
