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
import ru.kpfu.itis.arifulina.config.CurrencyApiConfig;
import ru.kpfu.itis.arifulina.util.httpclient.HttpClient;
import ru.kpfu.itis.arifulina.util.httpclient.exc.HttpClientException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(ParamsKey.EXCHANGE_RATES_RM)
@RequiredArgsConstructor
public class ExchangeRatesController {

    private final HttpClient httpClient;
    private final CurrencyApiConfig properties;

    @Loggable
    @HttpRequest
    @GetMapping(ParamsKey.NOW_RM)
    public String getExchangeRates() {
        Map<String, String> params = new HashMap<>();
        params.put("apikey", properties.getApikey());
        params.put("base_currency", "RUB");
        params.put("currencies", "EUR,USD");
        try {
            return buildResponseString(httpClient.get(properties.getUrl(), params, null));
        } catch (HttpClientException e) {
            return Messages.DEF_FAILURE_MSG;
        }
    }

    private String buildResponseString(String currencyStr) {
        return "--- EXCHANGE RATES"
                + parseCurrency(currencyStr, "EUR")
                + parseCurrency(currencyStr, "USD")
                + " --- ON " + LocalDate.now() + " ---";
    }

    private String parseCurrency(String currencyStr, String currency) {
        JsonObject resultJson = JsonParser.parseString(currencyStr).getAsJsonObject();
        JsonObject currencyJson = resultJson.get("data").getAsJsonObject().get(currency).getAsJsonObject();
        return String.format(" --- 1 %s = %.3f RUB", currency, 1.0 / currencyJson.get("value").getAsDouble());
    }
}
