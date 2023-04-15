package com.bubble.endpoint;


import com.bubble.entity.Profile;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/profile")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class ProfileEndpoint {
    @GET
    @PermitAll
    public Profile getOwnProfile(@Context SecurityContext ctx) {
        return (Profile) ctx.getUserPrincipal();
    }
}
