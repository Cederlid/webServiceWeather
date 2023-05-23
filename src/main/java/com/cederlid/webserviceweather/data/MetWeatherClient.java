package com.cederlid.webserviceweather.data;

import com.cederlid.webserviceweather.buisness.Weather;
import com.cederlid.webserviceweather.data.met.Data;
import com.cederlid.webserviceweather.data.met.MetWeather;
import com.cederlid.webserviceweather.data.met.Timeseries;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class MetWeatherClient {
    WebClient client = WebClient.create("http://127.0.0.1:8080");
    LocalDateTime localDateTime;

    public Weather getWeather(int x) {
        Mono<MetWeather> m2 = client
                .get()
                .uri("https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=59.3110&lon=18.0300")
                .retrieve()
                .bodyToMono(MetWeather.class);
        MetWeather weather = m2.block();
        assert weather != null;
        LocalDateTime afterXHours = LocalDateTime.now().withNano(0).withSecond(0).withMinute(0).plusHours(x);
        int hourIndex = -1;
        List<Timeseries> timeSeries = weather.getProperties().getTimeseries();

        for (int i = 0; i < timeSeries.size(); i++) {
            ZonedDateTime metHour = ZonedDateTime.parse(timeSeries.get(i).getTime());
            ZonedDateTime metHourInSthlm = metHour.withZoneSameInstant(ZoneId.of("Europe/Stockholm"));
            LocalDateTime metHourLocal = metHourInSthlm.toLocalDateTime();
            if (metHourLocal.equals(afterXHours)) {
                localDateTime = metHourLocal;
                hourIndex = i;
            }
        }
        try {
            Data data = weather.getProperties().getTimeseries().get(hourIndex).getData();
            Double temp = data.getInstant().getDetails().getAirTemperature();
            Double humid = data.getInstant().getDetails().getRelativeHumidity();
            return new Weather(temp, humid, "Met", localDateTime);
        }catch (RuntimeException e){
            throw new RuntimeException("Something went wrong!");
        }

    }

}
