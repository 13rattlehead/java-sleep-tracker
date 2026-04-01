// SleepTrackerAppTest.java
package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.yandex.practicum.sleeptracker.functions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class SleepTrackerAppTest {

    private SleepTrackerApp app;
    private File tempFile;

    @TempDir
    File tempDir;

    @BeforeEach
    void setUp() throws IOException {
        app = new SleepTrackerApp();
        tempFile = new File(tempDir, "test_sleep_log.txt");
    }

    @Test
    void testRunWithValidFile() throws IOException {
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("01.10.25 23:15;02.10.25 07:30;GOOD\n");
            writer.write("02.10.25 23:15;03.10.25 07:30;NORMAL\n");
        }

        assertDoesNotThrow(() -> app.run(tempFile.getAbsolutePath()));
    }

    @Test
    void testRunWithNonExistentFile() {
        String nonExistentFile = "/non/existent/file.txt";

        assertDoesNotThrow(() -> app.run(nonExistentFile));
    }

}
