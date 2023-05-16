
package com.cederlid.webserviceweather.data.openMeteo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "time",
    "temperature_2m",
    "relativehumidity_2m"
})
public class HourlyUnits {

    @JsonProperty("time")
    private String time;
    @JsonProperty("temperature_2m")
    private String temperature2m;
    @JsonProperty("relativehumidity_2m")
    private String relativehumidity2m;

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    @JsonProperty("temperature_2m")
    public String getTemperature2m() {
        return temperature2m;
    }

    @JsonProperty("temperature_2m")
    public void setTemperature2m(String temperature2m) {
        this.temperature2m = temperature2m;
    }

    @JsonProperty("relativehumidity_2m")
    public String getRelativehumidity2m() {
        return relativehumidity2m;
    }

    @JsonProperty("relativehumidity_2m")
    public void setRelativehumidity2m(String relativehumidity2m) {
        this.relativehumidity2m = relativehumidity2m;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HourlyUnits.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("time");
        sb.append('=');
        sb.append(((this.time == null)?"<null>":this.time));
        sb.append(',');
        sb.append("temperature2m");
        sb.append('=');
        sb.append(((this.temperature2m == null)?"<null>":this.temperature2m));
        sb.append(',');
        sb.append("relativehumidity2m");
        sb.append('=');
        sb.append(((this.relativehumidity2m == null)?"<null>":this.relativehumidity2m));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
