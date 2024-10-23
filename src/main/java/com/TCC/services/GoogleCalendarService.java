package com.TCC.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.util.List;

public class GoogleCalendarService {


    private static Calendar getCalendarService(Credential credential) throws Exception {
        return new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), new GsonFactory(), credential)
                .setApplicationName("SkyPlan")
                .build();
    }


    public List<Event> getUserEvents(Credential credential) throws IOException, Exception {

        Calendar service = getCalendarService(credential);


        DateTime now = new DateTime(System.currentTimeMillis());


        Events events = service.events().list("primary")
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        return events.getItems();
    }
}
