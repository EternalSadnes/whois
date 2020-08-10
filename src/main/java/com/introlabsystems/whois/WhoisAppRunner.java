package com.introlabsystems.whois;

import com.introlabsystems.whois.domainData.RegistrantInfoSearcher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class WhoisAppRunner implements ApplicationRunner {
    private final RegistrantInfoSearcher registrantInfoSearcher;

    public WhoisAppRunner(RegistrantInfoSearcher registrantInfoSearcher) {
        this.registrantInfoSearcher = registrantInfoSearcher;

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        registrantInfoSearcher.retrieveInformationAboutDomain();
    }
}
