package ru.kpfu.itis.arifulina.util.httpclient.impl;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.arifulina.util.httpclient.HttpClient;
import ru.kpfu.itis.arifulina.util.httpclient.exc.HttpClientException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class HTTPClientImpl implements HttpClient {

    @Override
    public String get(String url, Map<String, String> params, String token) throws HttpClientException {
        try {
            if (params != null && !params.isEmpty()) {
                StringBuilder urlParams = new StringBuilder();
                params.forEach((key, value) -> urlParams.append(key).append("=").append(value).append("&"));
                url = url + "?" + urlParams.substring(0, urlParams.length() - 1);
            }

            URL getUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            if (token != null) {
                connection.setRequestProperty("Authorization", "Bearer " + token);
            }

            String result = getResponse(connection);

            connection.disconnect();
            return result;
        } catch (IOException e) {
            throw new HttpClientException(e.getMessage());
        }
    }

    @Override
    public String post(String url, Map<String, String> params, String token) throws HttpClientException {
        return makeRequestWithBody(url, params, "POST", token);
    }

    @Override
    public String put(String url, Map<String, String> params, String token) throws HttpClientException {
        return makeRequestWithBody(url, params, "PUT", token);
    }

    @Override
    public String delete(String url, Map<String, String> params, String token) throws HttpClientException {
        return makeRequestWithBody(url, params, "DELETE", token);
    }

    private String makeRequestWithBody(String url, Map<String, String> params, String methodName, String token) throws HttpClientException {
        try {
            URL putUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) putUrl.openConnection();

            connection.setRequestMethod(methodName);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            if (token != null) {
                connection.setRequestProperty("Authorization", "Bearer " + token);
            }

            connection.setDoOutput(true);

            Gson gson = new Gson();
            String jsonInput = gson.toJson(params);

            try (OutputStream out = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                out.write(input, 0, input.length);
                out.flush();
            }

            String result = getResponse(connection);

            connection.disconnect();
            return result;
        } catch (IOException e) {
            throw new HttpClientException(e.getMessage());
        }
    }

    private String getResponse(HttpURLConnection connection) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String input;
            while ((input = reader.readLine()) != null) {
                content.append(input);
            }
        }
        return content.toString();
    }
}
