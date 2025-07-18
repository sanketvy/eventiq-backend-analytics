package com.eventiq.analytics.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityData {
    // Getters (for Jackson)
    private String name;
    private int sessions;

    public CityData(String name, int sessions) {
        this.name = name;
        this.sessions = sessions;
    }

}
