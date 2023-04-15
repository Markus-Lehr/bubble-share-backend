package com.bubble.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class FirebaseConfig {
    @Inject
    FirebaseProperties properties;

    private static final Logger LOGGER = Logger.getLogger("FirebaseConfig");

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("Initializing Firebase");
        try {
            FirebaseApp.initializeApp(getFirebaseOptions());
        } catch (IllegalStateException e) {
            //noop - Firebase has already been initialized
        }
    }

    private FirebaseOptions getFirebaseOptions() {
        InputStream serviceKey = getClass().getClassLoader().getResourceAsStream(properties.serviceKeyLocation());
        GoogleCredentials googleCredentials;
        try {
            googleCredentials = GoogleCredentials.fromStream(serviceKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new FirebaseOptions.Builder()
                .setCredentials(googleCredentials)
                .build();
    }
}
