package com.bubble.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Profile extends PanacheEntity {
    public String email;
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Shareable> shareables;
    @ManyToMany(fetch = FetchType.EAGER)
    public List<Bubble> bubbles;
}
