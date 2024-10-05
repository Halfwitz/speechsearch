package com.lorenz.speechsearch.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents a single speech attached to a specific Speaker. Speeches are made of Snippets with time stamps to specific
 * portions where the speech in the snippet occurs.
 */
@Entity
public class Speech {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private LocalDate date;

    @Lob
    private String transcript;

    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "speaker_id")
    @JsonBackReference // do not serialize
    private Speaker speaker;

    @OneToMany(mappedBy = "speech", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // serialize normally
    private List<Snippet> snippets;

    // CONSTRUCTORS
    public Speech() {}

    public Speech(String title, LocalDate date, String transcript, String videoUrl, Speaker speaker) {
        this.title = title;
        this.date = date;
        this.transcript = transcript;
        this.videoUrl = videoUrl;
        this.speaker = speaker;
    }

    // GETS & SETS
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public List<Snippet> getSnippets() {
        return snippets;
    }

    public void setSnippets(List<Snippet> snippets) {
        this.snippets = snippets;
    }
}
