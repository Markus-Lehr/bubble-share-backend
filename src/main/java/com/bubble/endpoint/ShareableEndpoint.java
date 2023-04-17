package com.bubble.endpoint;

import com.bubble.dto.*;
import com.bubble.entity.Bubble;
import com.bubble.entity.Category;
import com.bubble.entity.Profile;
import com.bubble.entity.Shareable;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Path("/shareable")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ShareableEndpoint {
    @POST
    @Transactional
    public ShareableDTO createNewShareable(@Context SecurityContext ctx, @RequestBody ShareableCreationDTO shareableCreationDTO) {
        Profile profile = (Profile) ctx.getUserPrincipal();
        Shareable shareable = new Shareable();
        if (shareableCreationDTO.getCategoryId() != null) {
            shareable.category = Category.findById(shareableCreationDTO.getCategoryId());
        }
        shareable.owner = profile;
        shareable.name = shareableCreationDTO.getName();
        shareable.persistAndFlush();

        return ShareableDTO.from(shareable);
    }

    @GET
    @Transactional
    public List<ShareableDTO> getOwnShareables(@Context SecurityContext ctx) {
        Profile profile = (Profile) ctx.getUserPrincipal();
        return profile.shareables.stream().map(ShareableDTO::from).toList();
    }

    @GET
    @Path("category")
    @Transactional
    public List<CategoryDTO> getCategories() {
        List<Category> categories = Category.listAll();
        return categories.stream().map(CategoryDTO::from).toList();
    }
}
