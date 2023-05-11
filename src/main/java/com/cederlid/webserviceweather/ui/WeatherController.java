package com.cederlid.webserviceweather.ui;

import com.cederlid.webserviceweather.buisness.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/bestweather")
    public String getBestWeather(Model m){
       m.addAttribute("weather", weatherService.getBestWeather());
       return "weathersite";
    }
}
