package di.transport;

class EngineImp implements Engine {
    private boolean started;

    @Override
    public boolean started() {
        return started;
    }

    @Override
    public void ignition() {
        started = true;
    }
}
