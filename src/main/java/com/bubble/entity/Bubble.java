package com.bubble.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Bubble extends PanacheEntity {
    public String name;
    @ManyToMany(fetch = FetchType.LAZY)
    public List<Shareable> shareables;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "bubbles")
    public List<Profile> members;
}
