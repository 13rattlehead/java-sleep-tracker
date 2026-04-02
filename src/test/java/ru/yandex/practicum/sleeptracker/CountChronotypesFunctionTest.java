package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.CountChronotypesFunction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CountChronotypesFunctionTest {

    @Test
    void testApplyWithEmptyList() {
        CountChronotypesFunction function = new CountChronotypesFunction();
        List<SleepingSession> sessions = new ArrayList<>();

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Ваш хронотип: ", result.getTitle());
        assertEquals("не может быть определен.", result.getValue());
    }

    @Test
    void testApplyWithOwlChronotype() {
        CountChronotypesFunction function = new CountChronotypesFunction();
        List<SleepingSession> sessions = new ArrayList<>();

        // OWL: начинает спать после 23:00
        SleepingSession owlSession = new SleepingSession();
        owlSession.setStartTime(LocalDateTime.of(2023, 1, 1, 23, 30));
        owlSession.setEndTime(LocalDateTime.of(2023, 1, 2, 10, 0));
        sessions.add(owlSession);

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Ваш хронотип: ", result.getTitle());
        assertEquals(Chronotype.OWL.toString(), result.getValue());
    }

    @Test
    void testApplyWithLarkChronotype() {
        CountChronotypesFunction function = new CountChronotypesFunction();
        List<SleepingSession> sessions = new ArrayList<>();

        // LARK: начинает спать до 22:00
        SleepingSession larkSession = new SleepingSession();
        larkSession.setStartTime(LocalDateTime.of(2023, 1, 1, 21, 0));
        larkSession.setEndTime(LocalDateTime.of(2023, 1, 2, 6, 0));
        sessions.add(larkSession);

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Ваш хронотип: ", result.getTitle());
        assertEquals(Chronotype.LARK.toString(), result.getValue());
    }

    @Test
    void testApplyWithPigeonChronotype() {
        CountChronotypesFunction function = new CountChronotypesFunction();
        List<SleepingSession> sessions = new ArrayList<>();

        // PIGEON: начинает спать после 22:00 и до 23:00
        SleepingSession pigeonSession = new SleepingSession();
        pigeonSession.setStartTime(LocalDateTime.of(2023, 1, 1, 22, 30));
        pigeonSession.setEndTime(LocalDateTime.of(2023, 1, 2, 7, 30));
        sessions.add(pigeonSession);

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Ваш хронотип: ", result.getTitle());
        assertEquals(Chronotype.PIGEON.toString(), result.getValue());
    }

    @Test
    void testApplyWithDaySleepFilteredOut() {
        CountChronotypesFunction function = new CountChronotypesFunction();
        List<SleepingSession> sessions = new ArrayList<>();

        // Дневной сон, который должен быть отфильтрован
        SleepingSession daySleepSession = new SleepingSession();
        daySleepSession.setStartTime(LocalDateTime.of(2023, 1, 1, 12, 0));
        daySleepSession.setEndTime(LocalDateTime.of(2023, 1, 1, 15, 0));
        sessions.add(daySleepSession);

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Ваш хронотип: ", result.getTitle());
        assertEquals(Chronotype.PIGEON.toString(), result.getValue()); // Нет подходящих сессий, поэтому по умолчанию PIGEON
    }

    @Test
    void testApplyWithMultipleSessions() {
        CountChronotypesFunction function = new CountChronotypesFunction();
        List<SleepingSession> sessions = new ArrayList<>();

        // Добавляем несколько сессий разных типов
        SleepingSession owlSession = new SleepingSession();
        owlSession.setStartTime(LocalDateTime.of(2023, 1, 1, 23, 30));
        owlSession.setEndTime(LocalDateTime.of(2023, 1, 2, 10, 0));
        sessions.add(owlSession);

        SleepingSession larkSession = new SleepingSession();
        larkSession.setStartTime(LocalDateTime.of(2023, 1, 2, 21, 0));
        larkSession.setEndTime(LocalDateTime.of(2023, 1, 3, 6, 0));
        sessions.add(larkSession);

        SleepingSession pigeonSession = new SleepingSession();
        pigeonSession.setStartTime(LocalDateTime.of(2023, 1, 3, 22, 30));
        pigeonSession.setEndTime(LocalDateTime.of(2023, 1, 4, 7, 30));
        sessions.add(pigeonSession);

        // Добавляем еще одну сессию OWL для того, чтобы OWL стал доминирующим
        SleepingSession owlSession2 = new SleepingSession();
        owlSession2.setStartTime(LocalDateTime.of(2023, 1, 4, 23, 45));
        owlSession2.setEndTime(LocalDateTime.of(2023, 1, 5, 10, 30));
        sessions.add(owlSession2);

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Ваш хронотип: ", result.getTitle());
        assertEquals(Chronotype.OWL.toString(), result.getValue());
    }
}
