package com.TCC.domain.event;

import com.TCC.domain.calendar.CalendarId;
import com.TCC.domain.calendar.TokenRequest;

public class UserEventRequestDTO {
    private CancelEventRequestDTO requestDTO;
    private TokenRequest tokenRequest;
    private CalendarId calendarId;

    public CancelEventRequestDTO getRequestDTO() {
        return requestDTO;
    }

    public void setRequestDTO(CancelEventRequestDTO requestDTO) {
        this.requestDTO = requestDTO;
    }

    public TokenRequest getTokenRequest() {
        return tokenRequest;
    }

    public void setTokenRequest(TokenRequest tokenRequest) {
        this.tokenRequest = tokenRequest;
    }

    public CalendarId getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(CalendarId calendarId) {
        this.calendarId = calendarId;
    }
}
