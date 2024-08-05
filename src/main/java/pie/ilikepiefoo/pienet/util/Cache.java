package pie.ilikepiefoo.pienet.util;

import java.util.function.Supplier;

public class Cache<T> {
    private final Supplier<T> supplier;
    private T value;
    private boolean dirty;

    public Cache(Supplier<T> supplier) {
        this.value = null;
        this.supplier = supplier;
        this.dirty = true;
    }

    public synchronized T get() {
        if (dirty) {
            this.value = supplier.get();
            dirty = false;
        }
        return value;
    }

    public synchronized void update() {
        this.dirty = true;
    }
}
