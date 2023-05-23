package com.cederlid.webserviceweather.data;

import com.cederlid.webserviceweather.buisness.Weather;
import com.cederlid.webserviceweather.data.openMeteo.Hourly;
import com.cederlid.webserviceweather.data.smhi.Parameter;
import com.cederlid.webserviceweather.data.smhi.SmhiWeather;
import com.cederlid.webserviceweather.data.smhi.TimeSeries;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class SmhiWeatherClient {
    WebClient client = WebClient.create("http://127.0.0.1:8080");
    LocalDateTime localDateTime;

    public Weather getWeather(int x) {
        Mono<SmhiWeather> m3 = client
                .get()
                .uri("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0300/lat/59.3110/data.json")
                .retrieve()
                .bodyToMono(SmhiWeather.class);
        SmhiWeather weather = m3.block();
        assert weather != null;
        LocalDateTime afterXHours = LocalDateTime.now().withNano(0).withSecond(0).withMinute(0).plusHours(x);
        int hourIndex = -1;
        List<TimeSeries> timeSeries = weather.getTimeSeries();
        for (int i = 0; i < timeSeries.size(); i++) {
            ZonedDateTime smhiHour = ZonedDateTime.parse(timeSeries.get(i).getValidTime());
            ZonedDateTime smhiHourInSthlm = smhiHour.withZoneSameInstant(ZoneId.of("Europe/Stockholm"));
            LocalDateTime smhiHourLocal = smhiHourInSthlm.toLocalDateTime();
            if (smhiHourLocal.equals(afterXHours)) {
                System.out.println(" smhi " + smhiHourLocal);
                localDateTime = smhiHourLocal;
                hourIndex = i;
            }
        }
        if (hourIndex >= 0) {

            List<Parameter> params = timeSeries.get(hourIndex).getParameters();
            Optional<Integer> temp = Optional.empty();
            Optional<Integer> humid = Optional.empty();
            for (Parameter p : params) {
                if (p.getName().equals("t")) {
                    temp = Optional.of(p.getValues().get(0));
                }
                if (p.getName().equals("r")) {
                    humid = Optional.of(p.getValues().get(0));
                }
            }
            if (temp.isPresent() && humid.isPresent()) {
                return new Weather(temp.get(), humid.get(), "SMHI", localDateTime);
            } else {
                throw new RuntimeException("Couldn't find humidity and temperature");
            }
        } else {
            throw new RuntimeException("Couldn't find hour!");
        }

    }
}
