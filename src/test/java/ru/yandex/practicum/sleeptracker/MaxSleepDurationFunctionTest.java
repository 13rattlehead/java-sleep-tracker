package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;
import ru.yandex.practicum.sleeptracker.SleepTag;
import ru.yandex.practicum.sleeptracker.functions.MaxSleepDurationFunction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class MaxSleepDurationFunctionTest {

    @Test
    void apply_withEmptyList_shouldReturnZeroDuration() {
        MaxSleepDurationFunction function = new MaxSleepDurationFunction();
        List<SleepingSession> sessions = new ArrayList<>();

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Минимальная продолжительность сна", result.getTitle());
        assertEquals("0 минут", result.getValue());
    }

    @Test
    void apply_withSingleSession_shouldReturnThatSessionDuration() {
        MaxSleepDurationFunction function = new MaxSleepDurationFunction();
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusHours(8), SleepTag.GOOD)); // 480 минут

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Максимальная продолжительность сна", result.getTitle());
        assertEquals("480 минут", result.getValue());
    }

    @Test
    void apply_withMultipleSessions_shouldReturnMaxDuration() {
        MaxSleepDurationFunction function = new MaxSleepDurationFunction();
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusHours(8), SleepTag.GOOD)); // 480 минут
        sessions.add(new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusHours(5), SleepTag.GOOD)); // 300 минут
        sessions.add(new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusHours(10), SleepTag.GOOD)); // 600 минут

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Максимальная продолжительность сна", result.getTitle());
        assertEquals("600 минут", result.getValue());
    }

    @Test
    void apply_withSessionsHavingSameDuration_shouldReturnThatDuration() {
        MaxSleepDurationFunction function = new MaxSleepDurationFunction();
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusHours(5), SleepTag.GOOD));
        sessions.add(new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusHours(5), SleepTag.GOOD));

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Максимальная продолжительность сна", result.getTitle());
        assertEquals("300 минут", result.getValue());
    }
}
