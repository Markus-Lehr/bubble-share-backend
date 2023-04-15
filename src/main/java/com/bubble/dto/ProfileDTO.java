package com.bubble.dto;

import com.bubble.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private String uid;
    private String email;
    private String name;
    private String picture;

    public static ProfileDTO from(Profile profile) {
        return new ProfileDTO(profile.uid, profile.email, profile.name, profile.picture);
    }
}
