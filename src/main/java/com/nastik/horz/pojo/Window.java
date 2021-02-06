package com.nastik.horz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Window {
    private LocalDateTime start;
    private LocalDateTime end;
}
