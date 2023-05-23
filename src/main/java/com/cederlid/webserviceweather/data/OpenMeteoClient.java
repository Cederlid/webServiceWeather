package com.cederlid.webserviceweather.data;

import com.cederlid.webserviceweather.buisness.Weather;
import com.cederlid.webserviceweather.data.openMeteo.Hourly;
import com.cederlid.webserviceweather.data.openMeteo.OpenMeteoWeather;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;


@Repository
public class OpenMeteoClient {
    WebClient client = WebClient.create("http://127.0.0.1:8080");

    public Weather getWeather(int x) {
        Mono<OpenMeteoWeather> m = client
                .get()
                .uri("https://api.open-meteo.com/v1/forecast?latitude=59.3110&longitude=18.0300&hourly=temperature_2m,relativehumidity_2m&forecast_days=3&timezone=auto")
                .retrieve()
                .bodyToMono(OpenMeteoWeather.class);
        OpenMeteoWeather weather = m.block();
        assert weather != null;
        Hourly hourly = weather.getHourly();
        LocalDateTime afterXHours = LocalDateTime.now().withNano(0).withSecond(0).withMinute(0).plusHours(x);

        int hourIndex = -1;
        for (int i = 0; i < hourly.getTime().size() ; i++) {
            LocalDateTime openMeteoHour = LocalDateTime.parse(hourly.getTime().get(i));
            if(openMeteoHour.equals(afterXHours))
                hourIndex = i;
        }
        if(hourIndex >= 0){
            double temp = weather.getHourly().getTemperature2m().get(hourIndex);
            double humid = weather.getHourly().getRelativehumidity2m().get(hourIndex);
            LocalDateTime time = LocalDateTime.parse(weather.getHourly().getTime().get(hourIndex));
            return new Weather(temp, humid, "Open meteo", time);
        }
        else {
            throw new RuntimeException("Couldn't find hour!");
        }
    }
}
