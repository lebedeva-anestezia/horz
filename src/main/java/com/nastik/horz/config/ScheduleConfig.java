package com.nastik.horz.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ScheduleConfig {
    private int minLengthMinutes = 15;
    private int maxLengthMinutes = 60;
    private int minSessionsNumber = 10;
}
