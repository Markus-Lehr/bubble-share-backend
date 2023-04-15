package com.bubble;

import com.bubble.entity.Profile;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/hello")
@RequestScoped
public class GreetingResource {

    // https://pratikpc.medium.com/user-auth-using-quarkus-firebase-fdab11d5a845
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String hello(@Context SecurityContext ctx) {
        Profile principal = (Profile) ctx.getUserPrincipal();
        if (principal == null) {
            return "Hello Anonymous User";
        }

        return "hello " + principal;
    }
}
