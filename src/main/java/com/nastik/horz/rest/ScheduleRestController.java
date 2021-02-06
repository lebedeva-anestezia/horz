package com.nastik.horz.rest;

import com.nastik.horz.domain.ScheduleController;
import com.nastik.horz.pojo.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleRestController {

    private final ScheduleController scheduleController;

    @Autowired
    public ScheduleRestController(ScheduleController scheduleController) {
        this.scheduleController = scheduleController;
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello";
    }

    @GetMapping("/schedule")
    public Schedule getSchedule() {
        return scheduleController.getSchedule();
    }

    @GetMapping("/reset")
    public void reset() {
        scheduleController.reset();
    }
}
