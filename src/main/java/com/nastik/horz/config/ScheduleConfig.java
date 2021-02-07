package com.nastik.horz.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ScheduleConfig {

    @Value("${horz.session.minSessionsNumber}")
    private int minSessionsNumber;

    @Value("${horz.session.minLengthMinutes}")
    private int minLengthMinutes;

    @Value("${horz.session.maxLengthMinutes}")
    private int maxLengthMinutes;

    @Value("${horz.session.slidingWindowMinutes}")
    private int slidingWindowMinutes;
}
