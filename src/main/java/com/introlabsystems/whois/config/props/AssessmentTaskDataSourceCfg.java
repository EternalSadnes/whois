package com.introlabsystems.whois.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "datasource.assessment")
public class AssessmentTaskDataSourceCfg extends DataSourceCfg {
}