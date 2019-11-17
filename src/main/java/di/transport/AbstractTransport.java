package di.transport;


abstract class AbstractTransport implements Transport {
    private Engine engine;
    abstract protected void go();
    abstract String getType();

    protected AbstractTransport(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void startEngine() {
        engine.ignition();
    }

    @Override
    public void run() {
        if (engine.started()) {
            go();
        }else{
            throw new IllegalStateException(getType() + " not started");
        }
    }
}
