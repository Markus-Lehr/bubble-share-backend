package com.bubble.endpoint;

import com.bubble.dto.BubbleCreationDTO;
import com.bubble.dto.FullBubbleDTO;
import com.bubble.dto.SimpleBubbleDTO;
import com.bubble.entity.Bubble;
import com.bubble.entity.Profile;
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

@Path("/bubble")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class BubbleEndpoint {
    @POST
    @Transactional
    public SimpleBubbleDTO createNewBubble(@Context SecurityContext ctx, @RequestBody BubbleCreationDTO bubbleCreationDTO) {
        Profile profile = (Profile) ctx.getUserPrincipal();
        profile = Profile.findById(profile.uid);
        Bubble bubble = new Bubble();
        bubble.name = bubbleCreationDTO.getName();
        bubble.shareables = Collections.emptyList();
        bubble.persist();

        if (profile.bubbles == null) {
            profile.bubbles = new ArrayList<>();
        }
        profile.bubbles.add(bubble);
        profile.persistAndFlush();

        return SimpleBubbleDTO.from(bubble);
    }

    @Path("{bubbleId}")
    @GET
    @Transactional
    public FullBubbleDTO getById(@Context SecurityContext ctx, @PathParam("bubbleId") Long bubbleId) {
        Profile profile = (Profile) ctx.getUserPrincipal();
        Bubble bubble = Bubble.findById(bubbleId);
        if (bubble != null && bubble.members.stream().map(m -> m.uid).anyMatch(id -> id.equals(profile.uid))) {
            return FullBubbleDTO.from(bubble);
        }
        return null;
    }

    @GET
    @Transactional
    public List<SimpleBubbleDTO> getAll(@Context SecurityContext ctx) {
        Profile profile = (Profile) ctx.getUserPrincipal();
        Stream<Bubble> bubbles = Bubble.stream("?1 MEMBER OF members", profile);
        return bubbles.map(SimpleBubbleDTO::from).toList();
    }
}
