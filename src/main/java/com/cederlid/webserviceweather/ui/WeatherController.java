package com.cederlid.webserviceweather.ui;

import com.cederlid.webserviceweather.buisness.Weather;
import com.cederlid.webserviceweather.buisness.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/bestweather")
    public String chooseHour(Model m){
       return "weathersite";
    }

    @PostMapping("/bestweather")
    public String getBestWeather(@RequestParam int xhour, Model m){
        m.addAttribute("weather", weatherService.getBestWeather(xhour));
        return "weathersite";
    }
}
