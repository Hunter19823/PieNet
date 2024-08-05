package pie.ilikepiefoo.pienet.util;

import java.util.function.Supplier;

public class CacheGroup {
    private final Cache<?>[] caches;

    public CacheGroup() {
        this.caches = new Cache<?>[0];
    }

    public <T> Cache<T> lazy(Supplier<T> supplier) {
        Cache<T> cache = new Cache<>(supplier);
        Cache<?>[] newCaches = new Cache<?>[caches.length + 1];
        System.arraycopy(
            caches,
            0,
            newCaches,
            0,
            caches.length
        );
        newCaches[caches.length] = cache;
        return cache;
    }

    public void update() {
        for (Cache<?> cache : caches) {
            cache.update();
        }
    }
}
