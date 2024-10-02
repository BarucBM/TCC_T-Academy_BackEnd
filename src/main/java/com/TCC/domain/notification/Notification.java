package com.TCC.domain.notification;

import com.TCC.domain.event.Event;
import com.TCC.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Event event;

    @ManyToMany(mappedBy = "notifications")
    private List<User> users;

//  @OneToMany
//  private UserNotification userNotification;

    private String message;

    private LocalDateTime sentAt;

    private String type;

    private String status;

}
