package com.bubble.dto;

import com.bubble.entity.Shareable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareableCreationDTO {
    private String name;
    private Long categoryId;

    public static ShareableCreationDTO from(Shareable shareable) {
        return new ShareableCreationDTO(shareable.name, shareable.category.id);
    }
}
