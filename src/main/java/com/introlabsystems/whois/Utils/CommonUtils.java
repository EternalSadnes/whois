package com.introlabsystems.whois.Utils;

import org.springframework.stereotype.Service;

@Service
public class CommonUtils {

    public static void sleep(int mils) {
        try {
            Thread.sleep(mils);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
