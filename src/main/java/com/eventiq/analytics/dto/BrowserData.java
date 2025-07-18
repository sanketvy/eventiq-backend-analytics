package com.eventiq.analytics.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BrowserData {
    private String name;
    private String version;
    private String icon;
    private int sessions;
    private List<DeviceData> devices = new ArrayList<>();

    public BrowserData(String name) {
        this.name = name;
        this.icon = getBrowserIcon(name);
    }

    public void addDevice(String name, int sessions) {
        this.sessions += sessions;
        devices.add(new DeviceData(name, sessions));
    }

    private String getBrowserIcon(String name) {
        return switch (name.toLowerCase()) {
            case "chrome" -> "🌐";
            case "safari" -> "🧭";
            case "firefox" -> "🔥";
            case "edge" -> "🌊";
            case "opera" -> "🎭";
            case "samsung internet" -> "📱";
            default -> "🖥️";
        };
    }
}