package com.TCC.services;

import com.TCC.domain.notification.Notification;
import com.TCC.domain.user.User;
import com.TCC.producers.EmailProducer;
import com.TCC.repositories.NotificationRepository;
import com.TCC.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationsService {

    @Autowired
    private EmailProducer emailProducer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;


    public String sendEmail(String userId){
        try{
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("user not found!"));

            Notification notification = new Notification();
            notification.setTitle("Email enviado pelo microservice do TCC");
            notification.setMessage("teste");
            notification.setUser(user);

            emailProducer.sendEmail(user, notification);

            notification.setSentAt(LocalDateTime.now());
            notification.setStatus("Enviado");

            notificationRepository.save(notification);
            return "Email enviado!";
        }catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}
