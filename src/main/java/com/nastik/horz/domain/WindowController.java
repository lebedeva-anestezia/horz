package com.nastik.horz.domain;

import com.nastik.horz.pojo.Window;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class WindowController {

    private Map<DayOfWeek, Pair<LocalTime, LocalTime>> defaultConfig;
    private Window lastWindow;

    @PostConstruct
    private void init() {
        defaultConfig = new TreeMap<>();
        defaultConfig.put(DayOfWeek.MONDAY,
                Pair.of(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        defaultConfig.put(DayOfWeek.TUESDAY,
                Pair.of(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        defaultConfig.put(DayOfWeek.WEDNESDAY,
                Pair.of(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        defaultConfig.put(DayOfWeek.THURSDAY,
                Pair.of(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        defaultConfig.put(DayOfWeek.FRIDAY,
                Pair.of(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        defaultConfig.put(DayOfWeek.SATURDAY,
                Pair.of(LocalTime.of(11, 0), LocalTime.of(22, 0)));
        defaultConfig.put(DayOfWeek.SUNDAY,
                Pair.of(LocalTime.of(11, 0), LocalTime.of(22, 0)));

        reset();
    }

    public Window nextWindow() {
        LocalDate nextDay = lastWindow.getEnd().plusDays(1).toLocalDate();
        DayOfWeek nextDayOfWeek = nextDay.getDayOfWeek();
        Pair<LocalTime, LocalTime> dayConfig = defaultConfig.get(nextDayOfWeek);

        LocalDateTime start = nextDay.atTime(dayConfig.getLeft());
        LocalDateTime end = nextDay.atTime(dayConfig.getRight());

        lastWindow = new Window(start, end);
        return lastWindow;
    }

    public void reset() {
        lastWindow = new Window(LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(1));
    }
}
