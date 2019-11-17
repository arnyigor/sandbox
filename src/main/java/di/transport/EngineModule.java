package di.transport;

import dagger.Module;
import dagger.Provides;

@Module
class EngineModule {
    @Provides
    Engine provideEngine() {
        return new EngineImp();
    }
}
