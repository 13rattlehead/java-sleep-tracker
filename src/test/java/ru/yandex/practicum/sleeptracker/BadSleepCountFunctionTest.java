package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepTag;
import ru.yandex.practicum.sleeptracker.SleepingSession;
import ru.yandex.practicum.sleeptracker.functions.BadSleepCountFunction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class BadSleepCountFunctionTest {

    @Test
    void testApplyWithAllBadSleep() {
        // Arrange
        BadSleepCountFunction function = new BadSleepCountFunction();
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusHours(8), SleepTag.BAD));
        sessions.add(new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusHours(7), SleepTag.BAD));

        // Act
        SleepAnalysisResult result = function.apply(sessions);

        // Assert
        assertEquals("Количество плохих сна", result.getTitle());
        assertEquals("2", result.getValue());
    }

    @Test
    void testApplyWithNoBadSleep() {
        BadSleepCountFunction function = new BadSleepCountFunction();
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusHours(8), SleepTag.GOOD));
        sessions.add(new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusHours(7), SleepTag.NORMAL));


        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Количество плохих сна", result.getTitle());
        assertEquals("0", result.getValue());
    }

}
