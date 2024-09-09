package com.TCC.repositories;

import com.TCC.domain.company.Company;
import com.TCC.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, String> {

    public Optional<Event> findById (String id);
}
