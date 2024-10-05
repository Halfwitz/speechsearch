package com.lorenz.speechsearch.controller;

import com.lorenz.speechsearch.model.Snippet;
import com.lorenz.speechsearch.model.Snippet;
import com.lorenz.speechsearch.repository.SnippetRepository;

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
    public Snippet createSnippet(@RequestBody Snippet snippet) {
        return snippetRepository.save(snippet);
    }

    // PUT: update an existing snippet
    @PutMapping("/{id}")
    public ResponseEntity<Snippet> updateSnippet(@PathVariable Long id, @RequestBody Snippet snippetDetails) {
        Optional<Snippet> optionalSnippet = snippetRepository.findById(id);
        if (optionalSnippet.isPresent()) {
            Snippet snippet = optionalSnippet.get();
            snippet.setSpeech(snippetDetails.getSpeech());
            snippet.setText(snippetDetails.getText());
            snippet.setStartTime(snippetDetails.getStartTime());
            Snippet updatedSnippet = snippetRepository.save(snippet);
            return ResponseEntity.ok(updatedSnippet);
        } else {
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
}
