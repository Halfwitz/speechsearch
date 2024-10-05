package com.lorenz.speechsearch.repository;

import com.lorenz.speechsearch.model.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnippetRepository extends JpaRepository<Snippet, Long> {
    // query methods

}
