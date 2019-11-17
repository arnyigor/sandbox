package di.transport;

import dagger.Binds;
import dagger.Module;

@Module
abstract class WheelsModule {

    @Binds
    abstract Wheels provideCarWheels(CarWeelsImpl weels);

    @Binds
    abstract Wheels provideMotoWheels(MotoWheelsImp weels);
}
