package com.TCC.repositories;

import com.TCC.domain.user.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserEventRepository extends JpaRepository<UserEvent, String> {

    List<UserEvent> findAllByUserId(String userId);

    UserEvent findByEventId(String eventId);

    List<UserEvent> findByEventIdAndUserId(String eventId, String userId);
}
