package com.TCC;

import com.TCC.infra.security.GoogleOAuthService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.calendar.Calendar;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.api.services.calendar.model.CalendarList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SkyPlanApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkyPlanApplication.class, args);
	}
}
