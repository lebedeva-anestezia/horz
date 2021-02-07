package com.nastik.horz.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Session {
    //@JsonSerialize(using = DateTimeSerializer.class)
    public LocalDateTime begin;
    //@JsonSerialize(using = DateTimeSerializer.class)
    public LocalDateTime end;
}
