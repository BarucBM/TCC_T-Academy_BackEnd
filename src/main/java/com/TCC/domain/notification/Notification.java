package com.TCC.domain.notification;

import com.TCC.domain.event.Event;
import com.TCC.domain.preferences.NotificationType;
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

    @ManyToOne
    private Event event;

    private String title;

//  @ManyToMany(mappedBy = "notifications")
//  private List<User> users;

//  @OneToMany
//  private UserNotification userNotification;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private User user;

    private String message;

    private LocalDateTime sentAt;

    private String type;

    private String status;

    private NotificationType notificationType;

}
