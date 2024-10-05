package com.lorenz.speechsearch.model;

import jakarta.persistence.*;

import java.util.List;

@Entity // marks class as JPA entity
public class Speaker {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;

    private String name; // name of speaker

    private String role; // type of speaker (president, senator, etc.)

    // one-to-many relationship with Speech ('speaker' owns relationship, all operations cascaded to related entities)
    @OneToMany(mappedBy = "speaker", cascade = CascadeType.ALL, orphanRemoval = true) // removes orphaned Speech entities
    private List<Speech> speeches;

    // CONSTRUCTORS
    public Speaker() {} // default

    public Speaker(String name, String role) {
        this.name = name;
        this.role = role;
    }

    // GETTERS AND SETTERS
    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Speech> getSpeeches() {
        return speeches;
    }

    public void setSpeeches(List<Speech> speeches) {
        this.speeches = speeches;
    }

    @Override
    public String toString() {
        return "Speaker{id =" + id + ", name='" + name + "', role='"
        + role + "'}";
    }


}
