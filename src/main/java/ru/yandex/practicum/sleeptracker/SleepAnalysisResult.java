package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {
    private final String title;   // понятное название для пользователя
    private final String value;   // результат в виде строки

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