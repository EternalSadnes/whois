package com.introlabsystems.whois.config;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

@Configuration
public class DownloaderConfig {

    private static final int TIMEOUT_SECONDS = 60;

    private final boolean proxyEnabled;
    private final String proxyHost;
    private final int proxyPort;

    public DownloaderConfig(@Value("${downloader.proxy.host}") String proxyHost,
                            @Value("${downloader.proxy.port}") int proxyPort,
                            @Value("${downloader.proxy.enabled:false}") boolean proxyEnabled
    ) {
        this.proxyEnabled = proxyEnabled;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
    }

    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        if (proxyEnabled) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(proxyHost, proxyPort);
            builder.proxy(new Proxy(Proxy.Type.HTTP, inetSocketAddress));
        }
        return builder.build();
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return createOkHttpClient();
    }

}