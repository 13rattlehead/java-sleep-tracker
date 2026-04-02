package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {
    private final String title;
    private final String value;

    public SleepAnalysisResult(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return title + ": " + value;
    }
}