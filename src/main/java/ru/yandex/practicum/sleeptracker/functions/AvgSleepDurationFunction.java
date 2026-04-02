package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;


public class AvgSleepDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Средняя продолжительность сна", "0 минут");
        }
        int avgDuration = sessions.stream()
                .mapToInt(SleepingSession::getDurationInMinutes)
                .sum() / sessions.size();
        return new SleepAnalysisResult("Средняя продолжительность сна", avgDuration + " минут");
    }
}
