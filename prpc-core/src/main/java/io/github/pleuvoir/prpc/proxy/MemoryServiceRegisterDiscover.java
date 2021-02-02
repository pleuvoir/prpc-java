package io.github.pleuvoir.prpc.proxy;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于内存的服务注册与发现
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class MemoryServiceRegisterDiscover implements IServiceRegisterDiscover {

    private final ConcurrentHashMap<String, URL> cache = new ConcurrentHashMap<>();

    private static volatile MemoryServiceRegisterDiscover discover;
    private static final Object MUTEX = new Object();


    public static MemoryServiceRegisterDiscover getInstance() {
        if (discover == null) {
            synchronized (MUTEX) {
                if (discover == null) {
                    discover = new MemoryServiceRegisterDiscover();
                }
            }
        }
        return discover;
    }

    @Override
    public void register(String serviceName, URL url) {
        cache.putIfAbsent(serviceName, url);
    }

    @Override
    public void unregister(String serviceName) {
        cache.remove(serviceName);
    }

    @Override
    public URL getURL(String serviceName) {
        return cache.get(serviceName);
    }

    private MemoryServiceRegisterDiscover() {
    }
}
