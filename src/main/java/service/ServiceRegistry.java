package com.projectfoundation.core.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceRegistry {

    private final Map<Class<?>, Object> services = new HashMap<>();

    public <T> void register(Class<T> serviceClass, T service) {
        services.put(serviceClass, service);
    }

    public <T> T get(Class<T> serviceClass) {
        Object service = services.get(serviceClass);

        if (service == null) {
            throw new IllegalStateException("Service not registered: " + serviceClass.getName());
        }

        return serviceClass.cast(service);
    }

    public boolean has(Class<?> serviceClass) {
        return services.containsKey(serviceClass);
    }

    public void clear() {
        services.clear();
    }
}