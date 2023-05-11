package com.cederlid.webserviceweather.data;

import com.cederlid.webserviceweather.buisness.Weather;
import com.cederlid.webserviceweather.data.met.MetWeather;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Repository
public class MetWeatherClient {
    WebClient client = WebClient.create("http://127.0.0.1:8080");

    public Weather getWeather() {
        Mono<MetWeather> m2 = client
                .get()
                .uri("https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=59.3110&lon=18.0300")
                .retrieve()
                .bodyToMono(MetWeather.class);
        MetWeather weather = m2.block();

        assert weather != null;
        double temp = weather.getProperties().getTimeseries().get(27).getData().getInstant().getDetails().getAirTemperature();
        double humid = weather.getProperties().getTimeseries().get(27).getData().getInstant().getDetails().getRelativeHumidity();
        String time = weather.getProperties().getTimeseries().get(27).getTime();
        return new Weather(temp, humid, "MET", time);
    }

}
