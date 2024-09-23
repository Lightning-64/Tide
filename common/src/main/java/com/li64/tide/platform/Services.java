package com.li64.tide.platform;

import com.li64.tide.Tide;

import java.util.ServiceLoader;

public class Services {
    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Tide.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}