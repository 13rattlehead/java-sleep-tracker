package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.functions.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.function.Function;


public class SleepTrackerApp {

    // Список всех аналитических функций
    private final List<Function<List<SleepingSession>, SleepAnalysisResult>> analyses = List.of(
            new AvgSleepDurationFunction(),
            new BadSleepCountFunction(),
            new MinSleepDurationFunction(),
            new MaxSleepDurationFunction(),
            new SleeplessNightsCountFunction(),
            new CountChronotypesFunction()
    );

    public void run(String fileName) {
        try {
            List<SleepingSession> sessions = new SleepingSessionLoader().load(fileName);

            System.out.println(" АНАЛИЗ СНА \n");
            System.out.println("Файл: " + fileName);
            System.out.println("Всего сессий сна: " + sessions.size() + "\n");

            // Выполняем все функции
            analyses.stream()
                    .map(function -> function.apply(sessions))
                    .forEach(System.out::println);

        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: Файл не найден — " + fileName);
        } catch (Exception e) {
            System.err.println("Произошла ошибка при анализе:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String fileName = "/Users/denisryndin/Desktop/IT/Java/java-sleep-tracker/src/main/resources/sleep_log.txt";

        new SleepTrackerApp().run(fileName);
    }
}