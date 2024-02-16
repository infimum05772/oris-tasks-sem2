package ru.kpfu.itis.arifulina.httpclient;

import java.util.Map;

public interface HttpClient {
    String get(String url, Map<String, String> params, String token) throws HttpClientException;

    String post(String url, Map<String, String> params, String token) throws HttpClientException;

    String put(String url, Map<String, String> params, String token) throws HttpClientException;

    String delete(String url, Map<String, String> params, String token) throws HttpClientException;
}
