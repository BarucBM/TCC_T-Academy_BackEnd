package com.TCC.repositories;

import com.TCC.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, String>, JpaSpecificationExecutor<Event> {

    Optional<Event> findById (String id);

}
