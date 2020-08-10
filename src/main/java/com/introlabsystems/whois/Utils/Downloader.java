package com.introlabsystems.whois.Utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class Downloader {

    private final OkHttpClient okHttpClient;

    public Downloader(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public String get(Request request) {
        log.info("Getting {}", request.url());
        Response response;
        response = getResponse(request);
        try {
            return response.body().string();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private Response getResponse(Request request) {
        try {
            return okHttpClient.newCall(request).execute();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
