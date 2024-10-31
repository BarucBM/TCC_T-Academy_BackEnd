package com.TCC.controllers;

import com.TCC.domain.calendar.TokenRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@RestController
@RequestMapping("calendar")
public class CalendarController {

    @PostMapping()
    public ResponseEntity<?> getPrimaryCalendar(@RequestBody TokenRequest tokenRequest) {
        String calendarApiUrl = "https://www.googleapis.com/calendar/v3/users/me/calendarList";

        try {
            String authorizationToken = tokenRequest.getToken();
            if (authorizationToken == null || authorizationToken.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token is missing!");
            }

            URL url = new URL(calendarApiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setRequestProperty("Authorization", "Bearer " + authorizationToken);
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                in.close();

                String primaryCalendarId = findPrimaryCalendarId(content.toString());
                if (primaryCalendarId != null) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Map.of("primaryCalendarId", primaryCalendarId));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Primary calendar not found.");
                }
            } else {
                System.out.println(connection.getResponseMessage());
                return ResponseEntity.status(responseCode).body("Failed to fetch calendar events: " + connection.getResponseMessage());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to fetch calendar events: " + e.getMessage());
        }
    }

    private String findPrimaryCalendarId(String jsonResponse) {
        String primaryId = null;

        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        var jsonObject = parser.parse(jsonResponse).getAsJsonObject();
        var items = jsonObject.getAsJsonArray("items");

        for (var item : items) {
            var calendarItem = item.getAsJsonObject();
            if (calendarItem.get("primary") != null && calendarItem.get("primary").getAsBoolean()) {
                primaryId = calendarItem.get("id").getAsString();
                break;
            }
        }

        return primaryId;
    }
}