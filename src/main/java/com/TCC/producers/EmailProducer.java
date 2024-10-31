package com.TCC.producers;

import com.TCC.domain.notification.Notification;
import com.TCC.domain.notification.NotificationDTO;
import com.TCC.domain.user.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value(value = "${spring.rabbitmq.queue}" )
    private String routingKey;

    @Value("${email.ms}")
    private String emailFrom;

    public void sendEmail(User user, NotificationDTO notification){

        notification.setEmailTo(user.getEmail());
        notification.setEmailFrom(emailFrom);
        notification.setOwnerRef("Baruc");

        rabbitTemplate.convertAndSend("", routingKey, notification);
    }

}
