package com.lorenz.speechsearch.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.time.LocalTime;

/**
 * Represents a portion of a speech in which a specific speech occurs. Associated with one speech
 */
@Entity
@JsonIdentityInfo( // handle serializaion and prevent infinite recursion with object identifiers
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Snippet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "speech_id", nullable = false)
    //@JsonBackReference // do not serialize
    private Speech speech;

    @Lob
    private String text;

    private LocalTime startTime;

    // CONSTRUCTS
    public Snippet() {}

    public Snippet(Speech speech, String text, LocalTime startTime) {
        this.speech = speech;
        this.text = text;
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Speech getSpeech() {
        return speech;
    }

    public void setSpeech(Speech speech) {
        this.speech = speech;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Snippet{id =" + id + ", text='" + text + "', startTime=" + startTime + "}";
    }
}
