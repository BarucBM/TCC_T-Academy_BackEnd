package com.TCC.domain.notification;

import com.TCC.domain.event.Event;
import com.TCC.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String message;

    private LocalDateTime sentAt;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String status;

    @Column(name = "event_notifications", nullable = false)
    private boolean eventNotifications;

    @Column(name = "reminder_notifications", nullable = false)
    private boolean reminderNotifications;

    @Column(name = "weather_notifications", nullable = false)
    private boolean weatherNotifications;

    @Lob
    @Column(name = "user_data")
    private byte[] userData;

}
