package com.bubble.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Category extends PanacheEntity {
    public String name;
    public String description;
    public String icon;
    @ManyToOne
    @JoinColumn(name = "parent_category")
    public Category parentCategory;
}
