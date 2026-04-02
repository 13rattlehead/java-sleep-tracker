package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

import ru.yandex.practicum.sleeptracker.SleepingSessionLoader;

class SleepingSessionLoaderTest {

    private SleepingSessionLoader loader;

    @BeforeEach
    void setUp() {
        loader = new SleepingSessionLoader();
    }

    @Test
    void parseLine_ValidLine_ReturnsSleepingSession() {
        String line = "01.01.23 22:00;02.01.23 06:00;GOOD";

        SleepingSession session = loader.parseLine(line);

        assertEquals(LocalDateTime.of(2023, 1, 1, 22, 0), session.getStartTime());
        assertEquals(LocalDateTime.of(2023, 1, 2, 6, 0), session.getEndTime());
        assertEquals(SleepTag.GOOD, session.getSleepTag());
    }

    @Test
    void parseLine_InvalidFormat_ThrowsIllegalArgumentException() {
        String line = "01.01.23 22:00;02.01.23 06:00";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            loader.parseLine(line);
        });

        assertEquals("Неверный формат строки: " + line, exception.getMessage());
    }

    @Test
    void parseLine_InvalidDateTimeFormat_ThrowsIllegalArgumentException() {
        String line = "invalid_date;02.01.23 06:00;GOOD";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            loader.parseLine(line);
        });

        assertTrue(exception.getMessage().startsWith("Неверный формат даты и времени: "));
    }

    @Test
    void parseLine_InvalidTag_ThrowsIllegalArgumentException() {
        String line = "01.01.23 22:00;02.01.23 06:00;INVALID_TAG";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            loader.parseLine(line);
        });

        assertTrue(exception.getMessage().startsWith("Неверный формат тега: "));
    }
}
