package com.introlabsystems.whois.config.props;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class DataSourceCfg {

    private String jdbcUrl;
    private String username;
    private String password;
    private String driverClassName;
    private int poolSize = 10;
    private Map<String, String> properties = new HashMap<>();

}