package com.nastik.horz.domain;

import com.nastik.horz.config.ScheduleConfig;
import com.nastik.horz.pojo.Schedule;
import com.nastik.horz.pojo.Session;
import com.nastik.horz.pojo.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ScheduleController {

    private final WindowController windowController;
    private final ScheduleConfig scheduleConfig;
    private final Random random = new Random();
    private final List<Session> sessions = new LinkedList<>();

    @Autowired
    public ScheduleController(WindowController windowController, ScheduleConfig scheduleConfig) {
        this.windowController = windowController;
        this.scheduleConfig = scheduleConfig;
    }

    public Schedule getSchedule() {
        removeOldSessions();
        while (sessions.size() < scheduleConfig.getMinSessionsNumber()) {
            generateSessions(windowController.nextWindow());
            removeOldSessions();
        }
        return new Schedule(sessions);
    }

    public void reset() {
        sessions.clear();
        windowController.reset();
    }

    void generateSessions(Window window) {
        LocalDateTime start = window.getStart();
        LocalDateTime end = window.getEnd();

        Optional<Session> sessionOptional;

        do {
            sessionOptional = generateSession(start, end);
            if (sessionOptional.isPresent()) {
                sessions.add(sessionOptional.get());
                start = sessionOptional.get().getEnd();
            }
        } while (sessionOptional.isPresent());
    }

    Optional<Session> generateSession(LocalDateTime start, LocalDateTime end) {
        int lengthMinutes = scheduleConfig.getMinLengthMinutes() +
                random.nextInt(scheduleConfig.getMaxLengthMinutes() - scheduleConfig.getMinLengthMinutes());

        LocalDateTime maxEnd = end.minusMinutes(lengthMinutes);

        if (maxEnd.isBefore(start)) {
            return Optional.empty();
        }

        LocalDateTime sessionStart = generateRandomBetween(start, maxEnd);
        LocalDateTime sessionEnd = sessionStart.plusMinutes(lengthMinutes);

        return Optional.of(new Session(sessionStart, sessionEnd));
    }

    private LocalDateTime generateRandomBetween(LocalDateTime start, LocalDateTime end) {
        long startEpochSeconds = start.toEpochSecond(ZoneOffset.UTC);
        long endEpochSeconds = end.toEpochSecond(ZoneOffset.UTC);

        long randomValue = startEpochSeconds + random.nextInt((int) (endEpochSeconds - startEpochSeconds));
        return LocalDateTime.ofEpochSecond(randomValue, 0, ZoneOffset.UTC);
    }

    private void removeOldSessions() {
        while (!sessions.isEmpty() && sessions.get(0).getEnd().isBefore(LocalDateTime.now())) {
            sessions.remove(0);
        }
    }
}
