package com.bubble.dto;

import com.bubble.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String icon;
    private Long parentCategoryId;

    public static CategoryDTO from(Category category) {
        return new CategoryDTO(
                category.id,
                category.name,
                category.description,
                category.icon,
                category.parentCategory == null ? null : category.parentCategory.id);
    }
}
