package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class SleepingSessionLoaderTest {

    @TempDir
    Path tempDir;

    @Test
    void testLoadValidFile() throws IOException {
        // Создаем временный файл с корректными данными
        File tempFile = tempDir.resolve("sessions.csv").toFile();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("01.01.23 22:00;02.01.23 06:00;GOOD\n");
            writer.write("02.01.23 23:00;03.01.23 07:00;NORMAL\n");
        }

        SleepingSessionLoader loader = new SleepingSessionLoader();

        List<SleepingSession> sessions = loader.load(tempFile.getAbsolutePath());

        assertEquals(2, sessions.size());
        assertEquals(LocalDateTime.of(2023, 1, 1, 22, 0), sessions.get(0).getStartTime());
        assertEquals(LocalDateTime.of(2023, 1, 2, 6, 0), sessions.get(0).getEndTime());
        assertEquals(SleepTag.GOOD, sessions.get(0).getSleepTag());
        assertEquals(LocalDateTime.of(2023, 1, 2, 23, 0), sessions.get(1).getStartTime());
        assertEquals(LocalDateTime.of(2023, 1, 3, 7, 0), sessions.get(1).getEndTime());
        assertEquals(SleepTag.NORMAL, sessions.get(1).getSleepTag());
    }

    @Test
    void testLoadWithEmptyLines() throws IOException {

        File tempFile = tempDir.resolve("sessions_with_empty_lines.csv").toFile();
        try(FileWriter writer = new FileWriter(tempFile)) {
            writer.write("01.01.23 22:00;02.01.23 06:00;GOOD\n");
            writer.write("\n");
            writer.write("02.01.23 23:00;03.01.23 07:00;NORMAL\n");
            writer.write("\n");
        }
    }

    @Test
    void testLoadWithInvalidFormat() throws IOException {
        File tempFile = tempDir.resolve("invalid_format.txt").toFile();
        try(FileWriter writer = new FileWriter(tempFile)) {
            writer.write("01.01.23 22:00;02.01.23 06:00\n");
        }

        SleepingSessionLoader loader = new SleepingSessionLoader();
        assertThrows(IllegalArgumentException.class,
                () -> loader.load(tempFile.getAbsolutePath()));
    }

    @Test
    void testLoadFileWithInvalidDateTimeFormat() throws IOException {
        // Создаем временный файл с некорректным форматом даты
        File tempFile = tempDir.resolve("invalid_datetime.csv").toFile();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("2023-01-01 22:00;02.01.23 06:00;GOOD\n");
        }

        SleepingSessionLoader loader = new SleepingSessionLoader();
        assertThrows(java.time.format.DateTimeParseException.class, () -> {
            loader.load(tempFile.getAbsolutePath());
        });
    }

}
