package com.bubble.dto;

import com.bubble.entity.Bubble;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullBubbleDTO {
    private Long id;
    private String name;
    private List<ProfileDTO> members;

    public static FullBubbleDTO from(Bubble bubble) {
        return new FullBubbleDTO(bubble.id, bubble.name, bubble.members.stream().map(ProfileDTO::from).toList());
    }
}
