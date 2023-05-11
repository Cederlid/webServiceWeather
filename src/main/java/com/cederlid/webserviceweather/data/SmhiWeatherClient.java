package com.cederlid.webserviceweather.data;

import com.cederlid.webserviceweather.buisness.Weather;
import com.cederlid.webserviceweather.data.smhi.Parameter;
import com.cederlid.webserviceweather.data.smhi.SmhiWeather;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class SmhiWeatherClient {
    WebClient client = WebClient.create("http://127.0.0.1:8080");

    public Weather getWeather() {
        Mono<SmhiWeather> m3 = client
                .get()
                .uri("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0300/lat/59.3110/data.json")
                .retrieve()
                .bodyToMono(SmhiWeather.class);
        SmhiWeather weather = m3.block();
        List<Parameter> params = weather.getTimeSeries().get(26).getParameters();
        double temp = 0;
        for(Parameter p : params){
            if(p.getName().equals("t")){
              temp = p.getValues().get(0);
            }
        }
        double humid = weather.getTimeSeries().get(26).getParameters().get(5).getValues().get(0);
        String time = weather.getTimeSeries().get(26).getValidTime();
        return new Weather(temp, humid, "SMHI", time );
    }

}
