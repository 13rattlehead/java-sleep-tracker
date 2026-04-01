package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;
import ru.yandex.practicum.sleeptracker.functions.SleeplessNightsCountFunction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class SleeplessNightsCountFunctionTest {

    private final SleeplessNightsCountFunction function = new SleeplessNightsCountFunction();

    @Test
    void testEmptySessions() {
        List<SleepingSession> sessions = new ArrayList<>();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals("Бессонных ночей", result.getTitle());
        assertEquals(String.valueOf(0), result.getValue());
    }

    @Test
    void testOneSleeplessNight() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(
                LocalDateTime.of(2023, 10, 10, 22, 0),
                LocalDateTime.of(2023, 10, 11, 6, 0),
                SleepTag.BAD
        ));
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals("Бессонных ночей", result.getTitle());
        assertEquals(String.valueOf(1), String.valueOf(result.getValue()));
    }

    @Test
    void testOverlappingSession() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(
                LocalDateTime.of(2023, 10, 10, 22, 0),
                LocalDateTime.of(2023, 10, 11, 2, 0),
                SleepTag.NORMAL
        ));
        sessions.add(new SleepingSession(
                LocalDateTime.of(2023, 10, 11, 1, 0),
                LocalDateTime.of(2023, 10, 11, 6, 0),
                SleepTag.NORMAL
        ));
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals("Бессонных ночей", result.getTitle());
        assertEquals(String.valueOf(1), result.getValue());
    }

    @Test
    void testSleepOutsideWindow() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(
                LocalDateTime.of(2023, 10, 10, 6, 0),
                LocalDateTime.of(2023, 10, 10, 22, 0),
                SleepTag.NORMAL
        ));
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals("Бессонных ночей", result.getTitle());
        assertEquals("1", result.getValue());
    }
}
