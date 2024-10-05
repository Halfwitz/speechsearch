package com.lorenz.speechsearch.model;

import jakarta.persistence.*;
import java.time.LocalTime;

/**
 * Represents a portion of a speech in which a specific speech occurs. Associated with one speech
 */
@Entity
public class Snippet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "speech_id", nullable = false)
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
