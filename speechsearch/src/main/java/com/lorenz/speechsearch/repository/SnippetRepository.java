package com.lorenz.speechsearch.repository;

import com.lorenz.speechsearch.model.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnippetRepository extends JpaRepository<Speaker, Long> {
    // query methods

}
