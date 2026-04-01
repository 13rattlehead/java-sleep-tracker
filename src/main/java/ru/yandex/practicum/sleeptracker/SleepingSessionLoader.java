package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class SleepingSessionLoader {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public List<SleepingSession> load(String fileName) throws FileNotFoundException {

        Path path = Paths.get(fileName);
        File file = path.toFile();

        if (!file.exists()) {
            throw new FileNotFoundException("Файл не найден: " + fileName);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            List<SleepingSession> sessions = reader.lines()
                    .filter(line -> !line.trim().isEmpty())
                    .map(this::parseLine)
                    .toList();
            return sessions;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при чтении файла: " + fileName, e);
        }

    }

    public SleepingSession parseLine(String line) {
        String[] parts = line.split(";");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверный формат строки: " + line);
        }

        try {
            LocalDateTime startTime = LocalDateTime.parse(parts[0], DATE_TIME_FORMATTER);
            LocalDateTime endTime = LocalDateTime.parse(parts[1], DATE_TIME_FORMATTER);
            SleepTag sleepTag = SleepTag.valueOf(parts[2]);

            return new SleepingSession(startTime, endTime, sleepTag);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Неверный формат даты и времени: " + line, e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неверный формат тега: " + line, e);
        }

    }

}
