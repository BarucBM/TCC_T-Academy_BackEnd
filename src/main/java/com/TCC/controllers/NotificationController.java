package com.TCC.controllers;

import com.TCC.domain.notification.Notification;
import com.TCC.repositories.NotificationRepository;
import com.TCC.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    // Recuperar preferências de notificação por userId
    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable String userId) {
        return userRepository.findById(userId)
                .map(user -> ResponseEntity.ok(notificationRepository.findByUser(user)))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/{userId}")
    public ResponseEntity<Notification> saveNotification(@PathVariable String userId, @RequestBody Notification notification) {
        return userRepository.findById(userId)
                .map(user -> {
                    notification.setUser(user);
                    Notification savedNotification = notificationRepository.save(notification);
                    return ResponseEntity.ok(savedNotification);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
