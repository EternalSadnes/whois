package com.introlabsystems.whois.domainData;

import com.introlabsystems.whois.Utils.Downloader;
import com.introlabsystems.whois.repository.assessment.AssessmentDomainRepository;
import com.introlabsystems.whois.repository.whois.RegistrantInfoRepository;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Slf4j
public class RegistrantInfoSearcher {
    private static final String WHOIS_API_URL = "www.whoisxmlapi.com";
    private static final String WHOIS_SERVER = "whoisserver";
    private static final String WHOIS_SERVICE = "WhoisService";
    private final Downloader downloader;
    private final String whoisApiKey;
    private final ModelMapper modelMapper;
    private final AssessmentDomainRepository domainRepository;
    private final RegistrantInfoRepository registrantInfoRepository;
    private final DomainDataParser domainDataParser;
    private final Whois whois;
    private final int threadsAmount;

    public RegistrantInfoSearcher(Downloader downloader, ModelMapper modelMapper, AssessmentDomainRepository domainRepository,
                                  RegistrantInfoRepository registrantInfoRepository,
                                  DomainDataParser domainDataParser,
                                  Whois whois,
                                  @Value("${threads.amount}") int threadsAmount,
                                  @Value("${whois.api.key}") String whoisApiKey) {
        this.downloader = downloader;
        this.whoisApiKey = whoisApiKey;
        this.modelMapper = modelMapper;
        this.domainRepository = domainRepository;
        this.registrantInfoRepository = registrantInfoRepository;
        this.domainDataParser = domainDataParser;
        this.whois = whois;
        this.threadsAmount = threadsAmount;
    }

    public void retrieveInformationAboutDomain() {
        ExecutorService executorService = Executors.newFixedThreadPool(threadsAmount);
        List<String> domains = getDomainNamesToSearch();
        List<Future> list = new ArrayList<>();
        for (String domain : domains) {
            list.add(executorService.submit(() -> {
                String domainData = downloader.get(buildRequest(domain));
                System.out.println(domainData);
            }));
        }
        int counter = 0;
        for (Future future : list) {
            try {
                log.info("Parsed {} domain out of {} domains", ++counter, domains.size());
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> getDomainNamesToSearch() {
        return domainRepository.findAllByDomainEnding();
    }

    private Request buildRequest(String domain) {
        URL url = new HttpUrl.Builder()
                .scheme("https")
                .host(WHOIS_API_URL)
                .addPathSegment(WHOIS_SERVER)
                .addPathSegment(WHOIS_SERVICE)
                .addQueryParameter("apiKey", whoisApiKey)
                .addQueryParameter("domainName", domain)
                .addQueryParameter("outputFormat", "json")
                .build()
                .url();
        return new Request.Builder().url(url).build();
    }

}
