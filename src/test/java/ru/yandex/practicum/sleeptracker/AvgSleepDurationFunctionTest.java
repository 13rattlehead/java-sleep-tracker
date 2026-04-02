package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.AvgSleepDurationFunction;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class AvgSleepDurationFunctionTest {

    @Test
    void apply_MultipleSessions_ReturnsCorrectAverage() {
        AvgSleepDurationFunction function = new AvgSleepDurationFunction();
        List<SleepingSession> sessions = new ArrayList<>();
        LocalDateTime start1 = LocalDateTime.of(2023, Month.MARCH, 15, 22, 0);
        LocalDateTime end1 = LocalDateTime.of(2023, Month.MARCH, 16, 6, 0);
        LocalDateTime start2 = LocalDateTime.of(2023, Month.MARCH, 16, 22, 0);
        LocalDateTime end2 = LocalDateTime.of(2023, Month.MARCH, 17, 5, 0);
        LocalDateTime start3 = LocalDateTime.of(2023, Month.MARCH, 17, 22, 0);
        LocalDateTime end3 = LocalDateTime.of(2023, Month.MARCH, 18, 7, 0);
        sessions.add(new SleepingSession(start1, end1, SleepTag.GOOD));
        sessions.add(new SleepingSession(start2, end2, SleepTag.GOOD));
        sessions.add(new SleepingSession(start3, end3, SleepTag.GOOD));

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Средняя продолжительность сна", result.getTitle());
        assertEquals("480 минут", result.getValue());
    }

    @Test
    void apply_SessionsWithZeroDuration_ReturnsZeroAverage() {
        AvgSleepDurationFunction function = new AvgSleepDurationFunction();
        List<SleepingSession> sessions = new ArrayList<>();
        LocalDateTime start = LocalDateTime.of(2023, Month.MARCH, 15, 22, 0);
        LocalDateTime end = LocalDateTime.of(2023, Month.MARCH, 15, 22, 0);
        sessions.add(new SleepingSession(start, end, SleepTag.GOOD));

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Средняя продолжительность сна", result.getTitle());
        assertEquals("0 минут", result.getValue());
    }
}
