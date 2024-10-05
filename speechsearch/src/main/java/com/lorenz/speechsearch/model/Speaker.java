package com.lorenz.speechsearch.model;

import jakarta.persistence.*;

@Entity // marks class as JPA entity
public class Speaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // auto increment private key id

    private String name; // name of speaker

    private String role; // type of speaker (president, senator, etc.)

    // one-to-many relationship (speeches stored for each Speaker)
    @OneToMany(mappedBy = "speaker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Speech> speeches;

    // CONSTRUCTORS
    public Speaker() {} // default

    public Speaker(String name, String role) {
        this.name = name;
        this.role = role;
    }

    // GETTERS AND SETTERS
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

}
