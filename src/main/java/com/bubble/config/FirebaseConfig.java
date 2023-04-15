package com.bubble.config;

import com.google.firebase.FirebaseApp;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class FirebaseConfig {
    private static final Logger LOGGER = Logger.getLogger("FirebaseConfig");

    void onStart(@Observes StartupEvent ev) {
        LOGGER.warn("Initializing Firebase");
        FirebaseApp.initializeApp();
    }
}
