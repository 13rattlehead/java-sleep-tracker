package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepingSession;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepTag;
import ru.yandex.practicum.sleeptracker.functions.MinSleepDurationFunction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.sleeptracker.SleepTag.GOOD;


public class MinSleepDurationFunctionTest {

    @Test
    void testApplyWithEmptyList() {
        MinSleepDurationFunction function = new MinSleepDurationFunction();
        List<SleepingSession> sessions = new ArrayList<>();

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Минимальная продолжительность сна", result.getTitle());
        assertEquals("0 минут", result.getValue());
    }

    @Test
    void testApplyWithSessionsHavingSameDuration() {
        MinSleepDurationFunction function = new MinSleepDurationFunction();
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusHours(5), GOOD));
        sessions.add(new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusHours(5), GOOD));

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Минимальная продолжительность сна", result.getTitle());
        assertEquals("300 минут", result.getValue());
    }
}
