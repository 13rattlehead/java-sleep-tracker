package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.Chronotype;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


public class CountChronotypesFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private Map<Chronotype, Integer> map = new HashMap<>();

    private static final LocalTime OWL_START = LocalTime.of(23, 0);
    private static final LocalTime OWL_END = LocalTime.of(9, 0);
    private static final LocalTime LARK_START = LocalTime.of(22, 0);
    private static final LocalTime LARK_END = LocalTime.of(7, 0);


    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Ваш хронотип: ", "не может быть определен.");
        }

        Map<Chronotype, Integer> map = new HashMap<>();
        map.put(Chronotype.OWL, 0);
        map.put(Chronotype.LARK, 0);
        map.put(Chronotype.PIGEON, 0);

        sessions.stream()
                .filter(this::isNightSleep)
                .map(this::determineChronotype)
                .forEach(chronotype -> map.put(chronotype, map.get(chronotype) + 1));

        Chronotype dominant = findDominantChronotype(map);

        return new SleepAnalysisResult("Ваш хронотип: ", dominant.toString());
    }

    private Chronotype determineChronotype(SleepingSession session) {
        LocalTime start = session.getStartTime().toLocalTime();
        LocalTime end = session.getEndTime().toLocalTime();

        if (start.isAfter(OWL_START) && end.isAfter(OWL_END)) {
            return Chronotype.OWL;
        } else if (start.isBefore(LARK_START) && end.isBefore(LARK_END)) {
            return Chronotype.LARK;
        } else {
            return Chronotype.PIGEON;
        }
    }

    private boolean isNightSleep(SleepingSession session) {
        LocalTime start = session.getStartTime().toLocalTime();
        LocalTime end = session.getEndTime().toLocalTime();

        return end.isBefore(start) // через полночь
                || start.isAfter(LocalTime.of(20, 0))
                || end.isBefore(LocalTime.of(10, 0));
    }

    private Chronotype findDominantChronotype(Map<Chronotype, Integer> map) {
        Chronotype dominant = null;

        int owl = map.get(Chronotype.OWL);
        int lark = map.get(Chronotype.LARK);
        int pigeon = map.get(Chronotype.PIGEON);

        if (owl > lark && owl > pigeon) {
            dominant = Chronotype.OWL;
        } else if (lark > pigeon && lark > owl) {
            dominant = Chronotype.LARK;
        } else {
            dominant = Chronotype.PIGEON;
        }

        return dominant;

    }

}
