package com.bubble.service;

import com.bubble.entity.Profile;
import com.google.firebase.auth.FirebaseToken;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
public class FirebaseProfileService {
    @Transactional
    public Profile getOrLoadProfile(FirebaseToken token) {
        Optional<Profile> profile = Profile.findByIdOptional(token.getUid());
        return profile.orElseGet(() -> createProfile(token));
    }

    private Profile createProfile(FirebaseToken token) {
        Profile profile = new Profile();
        profile.uid = token.getUid();
        profile.name = token.getName();
        profile.email = token.getEmail();
        profile.picture = token.getPicture();
        profile.persistAndFlush();
        return profile;
    }
}
