package com.lorenz.speechsearch.controller;

import com.lorenz.speechsearch.model.Snippet;
import com.lorenz.speechsearch.model.Speaker;
import com.lorenz.speechsearch.model.Speech;
import com.lorenz.speechsearch.repository.SnippetRepository;
import com.lorenz.speechsearch.repository.SpeechRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller
 * <br>Allows performing CRUD operations on <code>Snippet</code> entities via HTTP requests
 */
@RestController
@RequestMapping("/api/snippets")
public class SnippetController
{

    @Autowired
    private SnippetRepository snippetRepository;

    @Autowired
    private SpeechRepository speechRepository;

    // GET: get all snippets
    @GetMapping
    public List<Snippet> getAllSnippets() {
        return snippetRepository.findAll();
    }

    // GET: get a snippet by ID
    @GetMapping("/{id}")
    public ResponseEntity<Snippet> getSnippetById(@PathVariable Long id) {
        Optional<Snippet> snippet = snippetRepository.findById(id);
        return snippet.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: create a new snippet
    @PostMapping
    public ResponseEntity<Snippet> createSnippet(@RequestBody Snippet snippet) {
        // ensure speech exists
        if (snippet.getSpeech() == null || snippet.getSpeech().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Speech> speech = speechRepository.findById(snippet.getSpeech().getId());
        if (!speech.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // save snippet with specified speech
        snippet.setSpeech(speech.get());
        Snippet savedSnippet = snippetRepository.save(snippet);
        return ResponseEntity.ok(savedSnippet);
    }

    // PUT: update an existing snippet
    @PutMapping("/{id}")
    public ResponseEntity<Snippet> updateSnippet(@PathVariable Long id, @RequestBody Snippet snippetDetails) {
        Optional<Snippet> optionalSnippet = snippetRepository.findById(id);
        if (optionalSnippet.isPresent()) {
            Snippet snippet = optionalSnippet.get();
            snippet.setText(snippetDetails.getText());
            snippet.setStartTime(snippetDetails.getStartTime());

            // update speech if provided
            if (snippetDetails.getSpeech() != null && snippetDetails.getSpeech().getId() != null) {
                Optional<Speech> speech = speechRepository.findById(snippetDetails.getSpeech().getId());
                if (speech.isPresent()) {
                    snippet.setSpeech(speech.get());
                } else {
                    return ResponseEntity.badRequest().build();
                }
            }

            // save updated speach
            Snippet updatedSnippet = snippetRepository.save(snippet);
            snippet.setSpeech(snippetDetails.getSpeech());
            return ResponseEntity.ok(updatedSnippet);
        } else { // no snippet provided
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: delete a snippet
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSnippet(@PathVariable Long id) {
        Optional<Snippet> snippet = snippetRepository.findById(id);
        if (snippet.isPresent()) {
            snippetRepository.delete(snippet.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all snippets for a specific speech
    @GetMapping("/speech/{speechId}")
    public ResponseEntity<List<Snippet>> getSnippetsBySpeech(@PathVariable Long speechId) {
        Optional<Speech> speech = speechRepository.findById(speechId);
        if (speech.isPresent()) {
            List<Snippet> snippets = snippetRepository.findBySpeechId(speechId);
            return ResponseEntity.ok(snippets);

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
