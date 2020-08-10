package com.introlabsystems.whois.domainData;

import com.introlabsystems.whois.dto.RegistrantInfoDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class DomainDataParser {

    public RegistrantInfoDTO parseDomainData(String domainData) {
        RegistrantInfoDTO registrantInfoDTO = new RegistrantInfoDTO();
        registrantInfoDTO.setDomainName(parseDomainName(domainData, "Domain Name: "));
        registrantInfoDTO.setRegistrantName(parseDomainName(domainData, "Registrant Name: "));
        registrantInfoDTO.setOrganization(parseDomainName(domainData, "Registrant Organization: "));
        registrantInfoDTO.setStreet(parseDomainName(domainData, "Registrant Street: "));
        registrantInfoDTO.setCity(parseDomainName(domainData, "Registrant City: "));
        registrantInfoDTO.setState(parseDomainName(domainData, "Registrant State/Province: "));
        registrantInfoDTO.setPostalCode(parseDomainName(domainData, "Registrant Postal Code: "));
        registrantInfoDTO.setCountry(parseDomainName(domainData, "Registrant Country: "));
        registrantInfoDTO.setPhone(parseDomainName(domainData, "Registrant Phone: "));
        registrantInfoDTO.setEmail(parseDomainName(domainData, "Registrant Email: "));
        return registrantInfoDTO;
    }

    private String parseDomainName(String response,String lineStart) {
        String[] lines = response.split("\n");
        for (String line : lines) {
            if (StringUtils.startsWithIgnoreCase(line,lineStart))
                return StringUtils.substringAfter(line, lineStart).trim();
        }
        return null;
    }

}
