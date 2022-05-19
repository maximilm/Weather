package com.example.weather;

import java.util.ArrayList;
import java.util.List;

public class City {
    private String name;
    private String lat;
    private String lon;
    private String tempNow;
    private String sunriseToday;
    private String sunsetToday;
    private String maxTempToday;
    private String minTempToday;
    private String humidityNow;
    private String descriptionNow;
    private String timeNow;
    private String id;
    private List<List<String>> temperatures = new ArrayList<>();
    private List<String> dates = new ArrayList<>();

    public City(){}
    public City(String name, String lat, String lon, String tempNow, String sunriseToday, String sunsetToday, String maxTempToday, String minTempToday, String humidityNow, String descriptionNow, String timeNow, String id, List<List<String>> temperatures, List<String> dates) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.tempNow = tempNow;
        this.sunriseToday = sunriseToday;
        this.sunsetToday = sunsetToday;
        this.maxTempToday = maxTempToday;
        this.minTempToday = minTempToday;
        this.humidityNow = humidityNow;
        this.descriptionNow = descriptionNow;
        this.timeNow = timeNow;
        this.id = id;
        this.temperatures = temperatures;
        this.dates = dates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getTempNow() {
        return tempNow;
    }

    public void setTempNow(String tempNow) {
        this.tempNow = tempNow;
    }

    public String getSunriseToday() {
        return sunriseToday;
    }

    public void setSunriseToday(String sunriseToday) {
        this.sunriseToday = sunriseToday;
    }

    public String getSunsetToday() {
        return sunsetToday;
    }

    public void setSunsetToday(String sunsetToday) {
        this.sunsetToday = sunsetToday;
    }

    public String getMaxTempToday() {
        return maxTempToday;
    }

    public void setMaxTempToday(String maxTempToday) {
        this.maxTempToday = maxTempToday;
    }

    public String getMinTempToday() {
        return minTempToday;
    }

    public void setMinTempToday(String minTempToday) {
        this.minTempToday = minTempToday;
    }

    public String getHumidityNow() {
        return humidityNow;
    }

    public void setHumidityNow(String humidityNow) {
        this.humidityNow = humidityNow;
    }

    public String getDescriptionNow() {
        return descriptionNow;
    }

    public void setDescriptionNow(String descriptionNow) {
        this.descriptionNow = descriptionNow;
    }

    public String getTimeNow() {
        return timeNow;
    }

    public void setTimeNow(String timeNow) {
        this.timeNow = timeNow;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<List<String>> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<List<String>> temperatures) {
        this.temperatures = temperatures;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public String convertArrayToString(String indicator){
        String s = "";
        switch (indicator){
            case "dates":
                for (String i: dates){
                    s += i + ",";
                }
                break;
            case "temperatures":
                for (List<String> i: temperatures){
                    for (String j: i){
                        s += j + ",";
                    }
                    s += ";";
                }
                break;
        }
        return s;
    }

    public static List<String> convertStringtoArray(String s){
        ArrayList<String> d = new ArrayList<String>();
        for (String i: s.split(",")){
            d.add(i);
        }
        return d;
    }

    public static List<List<String>> covertStringtoArrayArray(String s){
        List<List<String>> t = new ArrayList<>();
        int k = 0;
        for (String i: s.split(";")){
            t.add(new ArrayList<String>());
            for (String j: i.split(",")){
                t.get(k).add(j);
            }
            k++;
        }
        return t;
    }
}
