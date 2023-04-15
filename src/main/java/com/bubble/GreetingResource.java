package com.bubble;

import com.google.firebase.auth.FirebaseAuth;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/hello")
public class GreetingResource {

    @Inject
    JsonWebToken jwt;

    // https://pratikpc.medium.com/user-auth-using-quarkus-firebase-fdab11d5a845
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String hello(@Context SecurityContext securityContext) {
        final var user = (DefaultJWTCallerPrincipal) securityContext.getUserPrincipal();
        if (user == null){
            return "Hello Anonymous User";
        }
        final var email = user.getClaim("email");
        return "hello " + email;
    }
}
