package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SleeplessNightsCountFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Бессонных ночей", "0");
        }

        Set<LocalDate> relevantNights = sessions.stream()
                .flatMap(session -> {
                    LocalDate startDate = session.getStartTime().toLocalDate();
                    LocalDate endDate = session.getEndTime().toLocalDate();


                    if (startDate.isBefore(endDate.minusDays(1))) {
                        return startDate.datesUntil(endDate.plusDays(1)).collect(Collectors.toSet()).stream();
                    }

                    return Stream.of(startDate, endDate);
                })
                .collect(Collectors.toSet());

        long sleeplessCount = relevantNights.stream()
                .filter(night -> !hasSleepInNightWindow(sessions, night))
                .count();

        return new SleepAnalysisResult("Бессонных ночей", String.valueOf(sleeplessCount));
    }

    private boolean hasSleepInNightWindow(List<SleepingSession> sessions, LocalDate night) {
        LocalDateTime windowStart = LocalDateTime.of(night, LocalTime.MIDNIGHT);        // 00:00
        LocalDateTime windowEnd   = LocalDateTime.of(night, LocalTime.of(6, 0));       // 06:00

        return sessions.stream().anyMatch(session ->
                overlaps(session.getStartTime(), session.getEndTime(), windowStart, windowEnd)
        );
    }

    private boolean overlaps(LocalDateTime sStart, LocalDateTime sEnd,
                             LocalDateTime wStart, LocalDateTime wEnd) {
        return sStart.isBefore(wEnd) && sEnd.isAfter(wStart);
    }
}