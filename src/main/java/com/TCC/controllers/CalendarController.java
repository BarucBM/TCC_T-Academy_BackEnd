package com.TCC.controllers;
import com.TCC.infra.security.GoogleOAuthService;
import com.TCC.services.GoogleCalendarService;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.calendar.model.Event;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.util.List;

@RestController
public class CalendarController {

    @GetMapping("/calendar/events")
    public List<Event> listGoogleCalendarEvents() throws Exception {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = GoogleOAuthService.authorize(httpTransport);


        GoogleCalendarService calendarService = new GoogleCalendarService();
        return calendarService.getUserEvents(credential);
    }
}
