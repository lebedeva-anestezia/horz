package com.nastik.horz.domain;

import com.nastik.horz.config.ScheduleConfig;
import com.nastik.horz.pojo.Session;
import com.nastik.horz.pojo.Window;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@PropertySource("classpath:application.properties")
public class SessionGenerator {

    private final Random random = new Random();
    private final ScheduleConfig scheduleConfig;

    public SessionGenerator(ScheduleConfig scheduleConfig) {
        this.scheduleConfig = scheduleConfig;
    }

    public List<Session> generateSessions(Window window) {
        LocalDateTime start = window.getStart();
        LocalDateTime end = window.getEnd();

        List<Session> sessions = new ArrayList<>();

        Optional<Session> sessionOptional;

        do {
            sessionOptional = generateSession(start, end);
            if (sessionOptional.isPresent()) {
                sessions.add(sessionOptional.get());
                start = sessionOptional.get().getEnd();
            }
        } while (sessionOptional.isPresent());

        return sessions;
    }

    public Optional<Session> generateSession(LocalDateTime start, LocalDateTime end) {
        int lengthMinutes = scheduleConfig.getMinLengthMinutes() +
                random.nextInt(scheduleConfig.getMaxLengthMinutes() - scheduleConfig.getMinLengthMinutes());

        long maxEndEpoch = Math.min(end.minusMinutes(lengthMinutes).toEpochSecond(ZoneOffset.UTC),
                start.plusMinutes(scheduleConfig.getSlidingWindowMinutes()).toEpochSecond(ZoneOffset.UTC));

        LocalDateTime maxEnd = LocalDateTime.ofEpochSecond(maxEndEpoch, 0, ZoneOffset.UTC);

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
}
