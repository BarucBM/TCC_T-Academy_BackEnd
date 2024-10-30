package com.TCC.domain.notification;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationDTO{
        @NotBlank
        String text;

        @NotBlank
        String subject ;

        @NotBlank
        String emailTo;

        @NotBlank
        @Email
        String emailFrom;

        @NotBlank
        String ownerRef;

        @NotBlank
        String eventId;


}
