package com.bubble.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Shareable extends PanacheEntity {
    public String name;
    @ManyToOne(fetch = FetchType.EAGER)
    public Profile owner;
    @ManyToMany(fetch = FetchType.LAZY)
    public List<Bubble> bubbles;
}
