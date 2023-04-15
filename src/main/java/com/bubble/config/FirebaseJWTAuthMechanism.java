package com.bubble.config;

import com.bubble.entity.Profile;
import com.bubble.service.FirebaseProfileService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import io.quarkus.arc.Priority;
import io.quarkus.security.credential.Credential;
import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.smallrye.jwt.runtime.auth.JWTAuthMechanism;
import io.quarkus.smallrye.jwt.runtime.auth.JsonWebTokenCredential;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import io.smallrye.mutiny.vertx.UniHelper;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import java.security.Permission;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Alternative
@Priority(1)
@ApplicationScoped
public class FirebaseJWTAuthMechanism implements HttpAuthenticationMechanism {
    private static final Logger LOGGER = Logger.getLogger(FirebaseJWTAuthMechanism.class);
    @Inject
    Vertx vertx;

    @Inject
    JWTAuthMechanism delegate;
    @Inject
    FirebaseProfileService firebaseProfileService;

    @Override
    public Uni<SecurityIdentity> authenticate(RoutingContext context, IdentityProviderManager identityProviderManager) {
        String authorizationHeader = context.request().getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            return Uni.createFrom().optional(Optional.empty());
        }
        authorizationHeader = authorizationHeader.replace("Bearer", "").trim();

        String finalAuthorizationHeader = authorizationHeader;
        Future<SecurityIdentity> firebaseSecurityIdentityFuture = vertx.executeBlocking(promise -> {
            FirebaseToken firebaseToken = null;
            try {
                firebaseToken = FirebaseAuth.getInstance().verifyIdToken(finalAuthorizationHeader);
            } catch (FirebaseAuthException e) {
                throw new RuntimeException(e);
            }
            Profile profile = firebaseProfileService.getOrLoadProfile(firebaseToken);
            promise.complete(new FirebaseSecurityIdentity(profile, firebaseToken, finalAuthorizationHeader));
        });
        return UniHelper.toUni(firebaseSecurityIdentityFuture);
    }

    @Override
    public Uni<ChallengeData> getChallenge(RoutingContext context) {
        return delegate.getChallenge(context);
    }

    @Override
    public Set<Class<? extends AuthenticationRequest>> getCredentialTypes() {
        return delegate.getCredentialTypes();
    }

    public static class FirebaseSecurityIdentity implements SecurityIdentity {
        private final Profile profile;
        private final FirebaseToken firebaseToken;
        private final String rawToken;

        public FirebaseSecurityIdentity(Profile profile, FirebaseToken firebaseToken, String rawToken) {
            this.profile = profile;
            this.firebaseToken = firebaseToken;
            this.rawToken = rawToken;
        }

        @Override
        public Profile getPrincipal() {
            return profile;
        }

        @Override
        public boolean isAnonymous() {
            return false;
        }

        @Override
        public Set<String> getRoles() {
            return Set.of();
        }

        @Override
        public boolean hasRole(String role) {
            return true;
        }

        @Override
        public <T extends Credential> T getCredential(Class<T> credentialType) {
            if (!credentialType.isAssignableFrom(JsonWebTokenCredential.class)) {
                throw new IllegalArgumentException(credentialType.getCanonicalName());
            }
            return (T) new JsonWebTokenCredential(rawToken);
        }

        @Override
        public Set<Credential> getCredentials() {
            return Set.of(getCredential(JsonWebTokenCredential.class));
        }

        @Override
        public <T> T getAttribute(String name) {
            return (T) firebaseToken.getClaims().get(name);
        }

        @Override
        public Map<String, Object> getAttributes() {
            return firebaseToken.getClaims();
        }

        @Override
        public Uni<Boolean> checkPermission(Permission permission) {
            return Uni.createFrom().item(true);
        }
    }
}
