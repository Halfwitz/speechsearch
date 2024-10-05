package com.lorenz.speechsearch.repository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lorenz.speechsearch.model.Speaker;

@Repository
public interface SpeakerRepository extends JpaRepository<Speaker, Long>
{
    // query methods

}
