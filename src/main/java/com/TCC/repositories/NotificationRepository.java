package com.TCC.repositories;

import com.TCC.domain.notification.Notification;
import com.TCC.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface NotificationRepository  extends JpaRepository<Notification, String> {
    List<Notification> findByUser(User user);

}
