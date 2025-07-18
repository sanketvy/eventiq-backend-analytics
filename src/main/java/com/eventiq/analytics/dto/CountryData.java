package com.eventiq.analytics.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CountryData {
    // Getters (for Jackson)
    private String name;
    private String code;
    private String flag;
    private int sessions;
    private List<CityData> cities = new ArrayList<>();

    public CountryData(String name) {
        this.name = name;
        this.code = getCountryCode(name);
        this.flag = getCountryFlag(name);
    }

    public void addCity(String cityName, int sessionCount) {
        this.sessions += sessionCount;
        cities.add(new CityData(cityName, sessionCount));
    }

    private String getCountryCode(String name) {
        switch (name.toLowerCase()) {
            case "india": return "IN";
            case "canada": return "CA";
            case "united states": return "US";
            case "united kingdom": return "GB";
            case "germany": return "DE";
            case "france": return "FR";
            case "australia": return "AU";
            default: return "XX";
        }
    }

    private String getCountryFlag(String name) {
        switch (name.toLowerCase()) {
            case "india": return "🇮🇳";
            case "canada": return "🇨🇦";
            case "united states": return "🇺🇸";
            case "united kingdom": return "🇬🇧";
            case "germany": return "🇩🇪";
            case "france": return "🇫🇷";
            case "australia": return "🇦🇺";
            default: return "🏳️";
        }
    }
}