package com.projectfoundation.core.engine;

import com.projectfoundation.core.service.FoundationService;
import com.projectfoundation.core.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;

public class FoundationEngine {

    private final ServiceRegistry serviceRegistry = new ServiceRegistry();
    private final List<FoundationService> services = new ArrayList<>();

    public ServiceRegistry services() {
        return serviceRegistry;
    }

    public <T extends FoundationService> void registerService(Class<T> serviceClass, T service) {
        serviceRegistry.register(serviceClass, service);
        services.add(service);
    }

    public void start() {
        for (FoundationService service : services) {
            service.start();
        }
    }

    public void stop() {
        for (int i = services.size() - 1; i >= 0; i--) {
            services.get(i).stop();
        }

        services.clear();
        serviceRegistry.clear();
    }
}