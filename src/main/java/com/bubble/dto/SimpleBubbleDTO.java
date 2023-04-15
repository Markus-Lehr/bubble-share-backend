package com.bubble.dto;

import com.bubble.entity.Bubble;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleBubbleDTO {
    private Long id;
    private String name;

    public static SimpleBubbleDTO from(Bubble bubble) {
        return new SimpleBubbleDTO(bubble.id, bubble.name);
    }
}
