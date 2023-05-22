package com.cederlid.webserviceweather.buisness;

import com.cederlid.webserviceweather.data.MetWeatherClient;
import com.cederlid.webserviceweather.data.OpenMeteoClient;
import com.cederlid.webserviceweather.data.SmhiWeatherClient;
import com.cederlid.webserviceweather.data.openMeteo.OpenMeteoWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    @Autowired
    SmhiWeatherClient smhiWeatherClient;
    @Autowired
    MetWeatherClient metWeatherClient;
    @Autowired
    OpenMeteoClient openMeteoClient;

    public Weather getBestWeather(int xHour) {
        Weather smhiWeather = smhiWeatherClient.getWeather(xHour);
        Weather metWeather = metWeatherClient.getWeather(xHour);
        Weather openMeteoWeather = openMeteoClient.getWeather(xHour);
        System.out.println("smhi " + smhiWeather);
        System.out.println("met " + metWeather);
        System.out.println("open meteo " + openMeteoWeather);

        if (smhiWeather.getTemperature() > metWeather.getTemperature() && smhiWeather.getTemperature() > openMeteoWeather.getTemperature()){
            return smhiWeather;
        }else if (metWeather.getTemperature() > smhiWeather.getTemperature() && metWeather.getTemperature() > openMeteoWeather.getTemperature()){
            return metWeather;
        } else{
            return openMeteoWeather;
        }

    }


}
