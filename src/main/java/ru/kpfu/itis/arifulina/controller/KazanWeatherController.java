package ru.kpfu.itis.arifulina.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.arifulina.httpclient.HTTPClientImpl;
import ru.kpfu.itis.arifulina.httpclient.HttpClientException;
import ru.kpfu.itis.arifulina.util.HttpClientProvider;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/kazan-weather")
public class KazanWeatherController {
    public static final String URL = "https://api.openweathermap.org/data/2.5/weather";
    public static final String API_KEY = "d9150eeddee7ab7195229541fcc66ad8";
    public static final HTTPClientImpl httpClient = (HTTPClientImpl) HttpClientProvider.getHttpClient();

    @GetMapping("/now")
    public String getCurrentKazanWeather() {
        Map<String, String> params = new HashMap<>();
        params.put("q", "kazan");
        params.put("appid", API_KEY);
        params.put("units", "metric");
        try {
            return parseWeather(httpClient.get(URL, params, null));
        } catch (HttpClientException e) {
            return "Something went wrong :(";
        }
    }

    private String parseWeather(String jsonString) {
        JsonObject weather = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonObject main = weather.get("main").getAsJsonObject();
        String temp = main.get("temp").getAsString();
        String humidity = main.get("humidity").getAsString();
        String precipitation = weather
                .get("weather")
                .getAsJsonArray()
                .get(0)
                .getAsJsonObject()
                .get("description")
                .getAsString();
        return "--- WEATHER IN KAZAN --- " +
                "TEMPERATURE: " + temp + " C --- " +
                "HUMIDITY: " + humidity + "% --- " +
                "PRECIPITATION: " + precipitation + " ---";
    }
}
