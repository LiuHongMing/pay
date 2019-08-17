package com.github.tiger.test.http;

import okhttp3.*;

import java.io.IOException;

public class OkHttpTest {

    public static final MediaType X_WWW_FORM_URLENCODED = MediaType.get(
            "application/x-www-form-urlencoded");

    public static final MediaType JSON = MediaType.get(
            "application/json");

    OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) throws IOException {
        OkHttpTest example = new OkHttpTest();
        String content = example.json("2019-06-20", "2019-06-25");
        String response = example.postJson("http://growth-liu.zhaopin.com/dashboard/batch", content);
        System.out.println(response);
    }

    String post(String url, String urlEncoder) throws IOException {
        RequestBody body = RequestBody.create(X_WWW_FORM_URLENCODED, urlEncoder);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    String postJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    String json(String startDate, String endDate) {
        return String.format("{\"startDate\":\"%s\",\"endDate\":\"%s\"}", startDate, endDate);
    }

    String urlEncoder(String startDate, String endDate) {
        return String.format("startDate=%s&endDate=%s", startDate, endDate);
    }

}
