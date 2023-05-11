package restUi;

import com.cederlid.webserviceweather.buisness.Weather;
import com.cederlid.webserviceweather.buisness.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherRestController {

    @Autowired
    WeatherService weatherService;

    @GetMapping("/rest/bestweather")
    public Weather getBestWeather(){
        return weatherService.getBestWeather();
    }
}
