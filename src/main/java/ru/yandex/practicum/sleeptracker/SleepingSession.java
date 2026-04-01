package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;


public class SleepingSession {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private SleepTag sleepTag;

    public SleepingSession(LocalDateTime startTime, LocalDateTime endTime, SleepTag sleepTag) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.sleepTag = sleepTag;
    }

    public SleepingSession() {

    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public SleepTag getSleepTag() {
        return sleepTag;
    }

    public int getDurationInMinutes() {
        return (int) Duration.between(startTime, endTime).toMinutes();
    }

    public int getDurationInHours() {
        return (int) Duration.between(startTime, endTime).toHours();
    }

    public void setStartTime(LocalDateTime of) {
        this.startTime = of;
    }

    public void setEndTime(LocalDateTime of) {
        this.endTime = of;
    }
}
