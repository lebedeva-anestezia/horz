package com.nastik.timer;

import com.nastik.horz.config.ScheduleConfig;
import com.nastik.horz.domain.ScheduleController;
import com.nastik.horz.domain.WindowController;
import com.nastik.horz.pojo.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleControllerTest {

    @Mock
    WindowController windowController;

    @Mock
    ScheduleConfig scheduleConfig;

    @InjectMocks
    ScheduleController scheduleController;

    @BeforeEach
    public void init() {
        when(scheduleConfig.getMinLengthMinutes()).thenReturn(10);
        when(scheduleConfig.getMaxLengthMinutes()).thenReturn(15);
    }


    @Test
    public void testGenerateSession() {
        Optional<Session> session = scheduleController
                .generateSession(LocalDateTime.now(), LocalDateTime.now().plusHours(2));

        assert(session.isPresent());
    }

    @Test
    public void testGenerateSessionTimeIsCorrect() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(2);

        Optional<Session> session = scheduleController.generateSession(start, end);

        assert(session.isPresent());
        assert(session.get().getBegin().isAfter(start));
        assert(session.get().getEnd().isBefore(end));
    }

}