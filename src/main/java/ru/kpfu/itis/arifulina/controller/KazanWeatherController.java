package ru.kpfu.itis.arifulina.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.arifulina.aspect.annotation.HttpRequest;
import ru.kpfu.itis.arifulina.aspect.annotation.Loggable;
import ru.kpfu.itis.arifulina.base.Messages;
import ru.kpfu.itis.arifulina.base.ParamsKey;
import ru.kpfu.itis.arifulina.config.OpenWeatherConfig;
import ru.kpfu.itis.arifulina.util.httpclient.HttpClient;
import ru.kpfu.itis.arifulina.util.httpclient.exc.HttpClientException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(ParamsKey.KAZAN_WEATHER_RM)
@RequiredArgsConstructor
public class KazanWeatherController {

    private final HttpClient httpClient;
    private final OpenWeatherConfig properties;

    @Loggable
    @HttpRequest
    @GetMapping(ParamsKey.NOW_RM)
    public String getCurrentKazanWeather() {
        Map<String, String> params = new HashMap<>();
        params.put("q", "kazan");
        params.put("appid", properties.getAppid());
        params.put("units", "metric");
        try {
            return parseWeather(httpClient.get(properties.getUrl(), params, null));
        } catch (HttpClientException e) {
            return Messages.DEF_FAILURE_MSG;
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
