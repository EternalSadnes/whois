package com.introlabsystems.whois.domainData;

import com.introlabsystems.whois.Utils.CommonUtils;
import org.apache.commons.net.whois.WhoisClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Whois {
    private final int CONNECTION_TRIES_AMOUNT = 10;
    private static final Pattern pattern;
    private static final String WHOIS_SERVER_PATTERN = "WHOIS Server:\\s(.*)";

    static {
        pattern = Pattern.compile(WHOIS_SERVER_PATTERN);
    }

    public String getWhois(String domainName) {
        StringBuilder result = new StringBuilder("");
        WhoisClient whois = new WhoisClient();
        try {
            whois.connect(WhoisClient.DEFAULT_HOST);
            String whoisData = whois.query(domainName);
            result.append(whoisData);
            whois.disconnect();
            String whoisServerUrl = getWhoisServer(whoisData);
            if (!whoisServerUrl.equals("")) {
                String whoisData2 =
                        queryWithWhoisServer(domainName, whoisServerUrl);
                result.append(whoisData2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private String queryWithWhoisServer(String domainName, String whoisServer) {
        String result = "";
        Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("5.189.151.227", 24056));
        WhoisClient whois = new WhoisClient();
        whois.setProxy(proxy);
        whois.setDefaultTimeout(5000);
        boolean flag = false;
        for (int i = 0; i < CONNECTION_TRIES_AMOUNT; i++) {
            try {
                whois.connect(whoisServer);
                try {
                    whois.setSoTimeout(5000);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                result = whois.query(domainName);
                whois.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(domainName + "    asdasdasd     " + whoisServer);
            }
            if (result != null) {
                if (flag)
                    for (int j = 0; j < 100; j++) {
                        System.out.println("nice");
                    }
                break;
            }
            CommonUtils.sleep(1000);
            flag = true;
            System.out.println("Hello there :)");
        }
        return result;
    }

    private String getWhoisServer(String whois) {
        String result = "";
        Matcher matcher = pattern.matcher(whois);
        while (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }
}
