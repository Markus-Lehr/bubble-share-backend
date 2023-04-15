package com.bubble.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "bubbleshare")
public interface FirebaseProperties {

    @WithName("serviceKeyLocation")
    String serviceKeyLocation();
}
