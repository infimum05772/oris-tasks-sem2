package ru.kpfu.itis.arifulina.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.arifulina.httpclient.HTTPClientImpl;
import ru.kpfu.itis.arifulina.httpclient.HttpClientException;
import ru.kpfu.itis.arifulina.util.HttpClientProvider;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/exchange-rates")
public class ExchangeRatesController {
    public static final String URL = "https://api.currencyapi.com/v3/latest";
    public static final String API_KEY = "cur_live_oe5zyQLZ8NCBKV5fLhkwqSYdmegXezsAQXn7djum";
    public static final HTTPClientImpl httpClient = (HTTPClientImpl) HttpClientProvider.getHttpClient();

    @GetMapping("/now")
    public String getExchangeRates() {
        Map<String, String> params = new HashMap<>();
        params.put("apikey", API_KEY);
        params.put("base_currency", "RUB");
        params.put("currencies", "EUR,USD");
        try {
            return buildResponseString(httpClient.get(URL, params, null));
        } catch (HttpClientException e) {
            return "Something went wrong :(";
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
