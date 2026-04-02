package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepTag;
import ru.yandex.practicum.sleeptracker.SleepingSession;
import java.util.List;
import java.util.function.Function;


public class BadSleepCountFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long badSleepCount = sessions.stream()
                .filter(session -> session.getSleepTag().equals(SleepTag.BAD))
                .count();
        return new SleepAnalysisResult("Количество плохих сна", String.valueOf(badSleepCount));
    }
}
