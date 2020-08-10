package com.introlabsystems.whois.dto;

import lombok.Data;

@Data
public class RegistrantInfoDTO {

    private String domainName;

    private String registrantName;

    private String organization;

    private String street;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private String phone;

    private String email;
}
