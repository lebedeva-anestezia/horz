package com.nastik.horz.domain;

import com.nastik.horz.config.ScheduleConfig;
import com.nastik.horz.pojo.Schedule;
import com.nastik.horz.pojo.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class ScheduleController {

    private final WindowController windowController;
    private final ScheduleConfig scheduleConfig;
    private final SessionGenerator sessionGenerator;
    private final List<Session> sessions = new LinkedList<>();

    @Autowired
    public ScheduleController(WindowController windowController, ScheduleConfig scheduleConfig, SessionGenerator sessionGenerator) {
        this.windowController = windowController;
        this.scheduleConfig = scheduleConfig;
        this.sessionGenerator = sessionGenerator;
    }

    public Schedule getSchedule() {
        removeOldSessions();
        while (sessions.size() < scheduleConfig.getMinSessionsNumber()) {
            sessions.addAll(sessionGenerator.generateSessions(windowController.nextWindow()));
            removeOldSessions();
        }
        return new Schedule(sessions);
    }

    public void reset() {
        sessions.clear();
        windowController.reset();
    }

    private void removeOldSessions() {
        while (!sessions.isEmpty() && sessions.get(0).getEnd().isBefore(LocalDateTime.now())) {
            sessions.remove(0);
        }
    }
}
