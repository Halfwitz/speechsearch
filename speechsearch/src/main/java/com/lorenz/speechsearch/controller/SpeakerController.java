package com.lorenz.speechsearch.controller;

import com.lorenz.speechsearch.model.Speaker;
import com.lorenz.speechsearch.repository.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller
 * Allows performing CRUD operations on <b><code>Speaker</code></b> entities via HTTP requests
 */
@RestController
@RequestMapping("/api/speakers")
public class SpeakerController {

    @Autowired
    private SpeakerRepository speakerRepository;

    // GET: get all speakers
    @GetMapping
    public List<Speaker> getAllSpeakers() {
        return speakerRepository.findAll();
    }

    // GET: get a speaker by ID
    @GetMapping("/{id}")
    public ResponseEntity<Speaker> getSpeakerById(@PathVariable Long id) {
        Optional<Speaker> speaker = speakerRepository.findById(id);
        return speaker.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: create a new speaker
    @PostMapping
    public Speaker createSpeaker(@RequestBody Speaker speaker) {
        return speakerRepository.save(speaker);
    }

    // PUT: update an existing speaker
    @PutMapping("/{id}")
    public ResponseEntity<Speaker> updateSpeaker(@PathVariable Long id, @RequestBody Speaker speakerDetails) {
        Optional<Speaker> optionalSpeaker = speakerRepository.findById(id);
        if (optionalSpeaker.isPresent()) {
            Speaker speaker = optionalSpeaker.get();
            speaker.setName(speakerDetails.getName());
            speaker.setRole(speakerDetails.getRole());
            Speaker updatedSpeaker = speakerRepository.save(speaker);
            return ResponseEntity.ok(updatedSpeaker);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: delete a speaker
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpeaker(@PathVariable Long id) {
        Optional<Speaker> speaker = speakerRepository.findById(id);
        if (speaker.isPresent()) {
            speakerRepository.delete(speaker.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
