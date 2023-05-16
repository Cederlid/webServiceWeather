package com.cederlid.webserviceweather.data;

import com.cederlid.webserviceweather.buisness.Weather;
import com.cederlid.webserviceweather.data.openMeteo.OpenMeteoWeather;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class OpenMeteoClient {
    WebClient client = WebClient.create("http://127.0.0.1:8080");

    public Weather getWeather() {
        Mono<OpenMeteoWeather> m = client
                .get()
                .uri("https://api.open-meteo.com/v1/forecast?latitude=59.3110&longitude=18.0300&hourly=temperature_2m,relativehumidity_2m&forecast_days=3&timezone=auto")
                .retrieve()
                .bodyToMono(OpenMeteoWeather.class);
        OpenMeteoWeather weather = m.block();

        assert weather != null;
        double temp = weather.getHourly().getTemperature2m().get(37);
        double humid = weather.getHourly().getRelativehumidity2m().get(37);
        String time = weather.getHourly().getTime().get(37);
        return new Weather(temp, humid, "Open meteo", time);

    }
}
