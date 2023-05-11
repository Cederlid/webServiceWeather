package com.cederlid.webserviceweather.buisness;

public class Weather {
    private double temperature;
    private double humidity;
    private String origin;
    private String timeStamp;

    public Weather(double temperature, double humidity, String origin, String timeStamp) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.origin = origin;
        this.timeStamp = timeStamp;
    }

    public Weather(){

    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "The best weather is: " +
                "temperature = " + temperature +
                " °C" +
                ", humidity = " + humidity +
                " %" +
                ", origin = '" + origin + '\'' +
                ", time = '" + timeStamp + '\'';
    }


}
