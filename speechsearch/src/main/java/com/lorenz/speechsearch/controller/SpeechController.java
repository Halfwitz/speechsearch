package com.lorenz.speechsearch.controller;

import com.lorenz.speechsearch.model.Speaker;
import com.lorenz.speechsearch.model.Speech;
import com.lorenz.speechsearch.repository.SpeakerRepository;
import com.lorenz.speechsearch.repository.SpeechRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller
 * <br>Allows performing CRUD operations on <code>Speech</code> entities via HTTP requests
 */
@RestController
@RequestMapping("/api/speeches")
public class SpeechController
{

    @Autowired
    private SpeechRepository speechRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

    // GET: get all speeches
    @GetMapping
    public List<Speech> getAllSpeeches() {
        return speechRepository.findAll();
    }

    // GET: get a speech by ID
    @GetMapping("/{id}")
    public ResponseEntity<Speech> getSpeechById(@PathVariable Long id) {
        Optional<Speech> speech = speechRepository.findById(id);
        return speech.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: create a new speech
    @PostMapping
    public ResponseEntity<Speech> createSpeech(@RequestBody Speech speech) {
        // ensure speaker exists
        if (speech.getSpeaker() == null || speech.getSpeaker().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Speaker> speaker = speakerRepository.findById(speech.getSpeaker().getId());
        if (!speaker.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // save speech with specified speaker
        speech.setSpeaker(speaker.get());
        Speech savedSpeech = speechRepository.save(speech);
        return ResponseEntity.ok(savedSpeech);
    }

    // PUT: update an existing speech
    @PutMapping("/{id}")
    public ResponseEntity<Speech> updateSpeech(@PathVariable Long id, @RequestBody Speech speechDetails) {
        Optional<Speech> optionalSpeech = speechRepository.findById(id);
        if (optionalSpeech.isPresent()) {
            Speech speech = optionalSpeech.get();
            speech.setTitle(speechDetails.getTitle());
            speech.setDate(speechDetails.getDate());
            speech.setTranscript(speechDetails.getTranscript());
            speech.setVideoUrl(speechDetails.getVideoUrl());

            // if speaker provided
            if (speechDetails.getSpeaker() != null && speechDetails.getSpeaker().getId() != null) {
                Optional<Speaker> speaker = speakerRepository.findById(speechDetails.getSpeaker().getId());
                if (speaker.isPresent()) {
                    speech.setSpeaker(speaker.get());
                } else {
                    return ResponseEntity.badRequest().build();
                }
            }

            // save updated speach
            Speech updatedSpeech = speechRepository.save(speech);
            speech.setSpeaker(speechDetails.getSpeaker());
            return ResponseEntity.ok(updatedSpeech);
        } else { // no speech provided
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: delete a speech
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpeech(@PathVariable Long id) {
        Optional<Speech> speech = speechRepository.findById(id);
        if (speech.isPresent()) {
            speechRepository.delete(speech.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all speeches by a specific speaker
    @GetMapping("/speaker/{speakerId}")
    public ResponseEntity<List<Speech>> getSpeechesBySpeaker(@PathVariable Long speakerId) {
        Optional<Speaker> speaker = speakerRepository.findById(speakerId);
        if (speaker.isPresent()) {
            List<Speech> speeches = speechRepository.findBySpeakerId(speakerId);
            return ResponseEntity.ok(speeches);

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
