package uz.company.digitalactive.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "application")
public record ApplicationProperties(@NestedConfigurationProperty RsaKeyProperties rca) {}
