package com.TCC.repositories;

import com.TCC.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, String>, JpaSpecificationExecutor<Event>  {

    @Query("SELECT e FROM Event e WHERE "
            + "(6371 * acos(cos(radians(:latitude)) * cos(radians(e.address.latitude)) "
            + "* cos(radians(e.address.longitude) - radians(:longitude)) "
            + "+ sin(radians(:latitude)) * sin(radians(e.address.latitude)))) < :radius")
    List<Event> findEventsNearLocation(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("radius") double radius);
}
