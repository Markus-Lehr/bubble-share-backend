package com.bubble.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.security.Principal;
import java.util.List;

@Entity
public class Profile extends PanacheEntityBase implements Principal {
    @Id
    public String uid;
    public String email;
    public String name;
    public String picture;
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Shareable> shareables;
    @ManyToMany(fetch = FetchType.EAGER)
    public List<Bubble> bubbles;

    @Override
    public String getName() {
        return email;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
