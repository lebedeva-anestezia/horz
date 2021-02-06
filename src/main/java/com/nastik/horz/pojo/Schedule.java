package com.nastik.horz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Schedule {
    public List<Session> sessions;
}
