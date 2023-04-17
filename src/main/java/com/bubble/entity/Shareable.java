package com.bubble.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Shareable extends PanacheEntity {
    public String name;
    @ManyToOne(fetch = FetchType.EAGER)
    public Category category;
    @ManyToOne(fetch = FetchType.EAGER)
    public Profile owner;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "shareables", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<Bubble> bubbles;
}
