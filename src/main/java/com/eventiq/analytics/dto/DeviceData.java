package com.eventiq.analytics.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceData {
    // Getters
    private String name;
    private int sessions;
    private String os;

    public DeviceData(String name, int sessions) {
        this.name = name;
        this.sessions = sessions;
    }

}
