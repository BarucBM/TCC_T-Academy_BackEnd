package com.TCC.domain.event;

import java.time.LocalDate;

public class CalendarEventDTO {
    private EventDate start;
    private EventDate end;
    private String summary;

    public CalendarEventDTO(LocalDate start, LocalDate end, String summary) {
        this.start = new EventDate(start);
        this.end = new EventDate(end);
        this.summary = summary;
    }

    // Getters and Setters

    public EventDate getStart() {
        return start;
    }

    public EventDate getEnd() {
        return end;
    }

    public String getSummary() {
        return summary;
    }

    public static class EventDate {
        private String date;

        public EventDate(LocalDate localDate) {
            this.date = localDate.toString(); // Formato "YYYY-MM-DD"
        }

        // Getter
        public String getDate() {
            return date;
        }
    }
}
