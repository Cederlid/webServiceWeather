package com.cederlid.webserviceweather.buisness;

import com.cederlid.webserviceweather.data.MetWeatherClient;
import com.cederlid.webserviceweather.data.SmhiWeatherClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    @Autowired
    SmhiWeatherClient smhiWeatherClient;
    @Autowired
    MetWeatherClient metWeatherClient;

    public Weather getBestWeather() {

        if (smhiWeatherClient.getWeather().getTemperature() > metWeatherClient.getWeather().getTemperature()){
            return smhiWeatherClient.getWeather();
        }else {
            return metWeatherClient.getWeather();
        }

    }

}
