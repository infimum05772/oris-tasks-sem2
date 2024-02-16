package ru.kpfu.itis.arifulina.util;

import ru.kpfu.itis.arifulina.httpclient.HTTPClientImpl;
import ru.kpfu.itis.arifulina.httpclient.HttpClient;

public class HttpClientProvider {
    private static HttpClient client;
    public static HttpClient getHttpClient() {
        if (client == null) {
            client = new HTTPClientImpl();
        }
        return client;
    }
}
