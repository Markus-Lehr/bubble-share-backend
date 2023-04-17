package com.bubble.dto;

import com.bubble.entity.Bubble;
import com.bubble.entity.Category;
import com.bubble.entity.Profile;
import com.bubble.entity.Shareable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareableDTO {
    private String name;
    private Long categoryId;
    private String ownerId;

    public static ShareableDTO from(Shareable shareable) {
        return new ShareableDTO(shareable.name, shareable.category == null ? null : shareable.category.id, shareable.owner.uid);
    }
}
