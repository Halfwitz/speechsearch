package com.lorenz.speechsearch.repository;

import com.lorenz.speechsearch.model.Speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpeechRepository extends JpaRepository<Speech, Long> {
    List<Speech> findBySpeakerId(Long speakerId); // retrieve speeches by speaker id
    // query methods

}
