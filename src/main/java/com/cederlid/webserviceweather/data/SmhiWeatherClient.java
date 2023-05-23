package com.cederlid.webserviceweather.data;

import com.cederlid.webserviceweather.buisness.Weather;
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
import java.util.function.Predicate;

@Repository
public class SmhiWeatherClient {
    WebClient client = WebClient.create("http://127.0.0.1:8080");

    private LocalDateTime convertToLocalDateTime(String validTime) {
        ZonedDateTime smhiHour = ZonedDateTime.parse(validTime);
        ZonedDateTime smhiHourInSthlm = smhiHour.withZoneSameInstant(ZoneId.of("Europe/Stockholm"));
        return smhiHourInSthlm.toLocalDateTime();
    }

    public Weather getWeather(int x) {
        Mono<SmhiWeather> m3 = client
                .get()
                .uri("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0300/lat/59.3110/data.json")
                .retrieve()
                .bodyToMono(SmhiWeather.class);
        SmhiWeather weather = m3.block();
        assert weather != null;
        LocalDateTime afterXHours = LocalDateTime.now().withNano(0).withSecond(0).withMinute(0).plusHours(x);
        List<TimeSeries> timeSeries = weather.getTimeSeries();

        Predicate<TimeSeries> predicate = t -> {
            LocalDateTime smhiHourLocal = convertToLocalDateTime(t.getValidTime());
            return smhiHourLocal.equals(afterXHours);
        };

        Optional<Weather> weather1 = timeSeries.stream()
                .filter(predicate)
                .findFirst()
                .map(t -> {
                    List<Parameter> p = t.getParameters();
                    var temp = p.stream().filter(pr -> pr.getName().equals("t")).findFirst()
                            .flatMap(pr -> Optional.of(pr.getValues().get(0)));
                    var humid = p.stream().filter(pr -> pr.getName().equals("r")).findFirst()
                            .flatMap(pr -> Optional.of(pr.getValues().get(0)));

                    if (temp.isEmpty() || humid.isEmpty())
                        throw new RuntimeException("Couldn't find humidity and temperature");

                    return new Weather(temp.get(), humid.get(), "SMHI", convertToLocalDateTime(t.getValidTime()));
                });

        if (weather1.isEmpty())
            throw new RuntimeException("Couldn't find hour!");

        return weather1.get();

    }


}
