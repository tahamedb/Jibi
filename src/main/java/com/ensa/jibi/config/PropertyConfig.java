package com.ensa.jibi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("/etc/secrets/secret")
public class PropertyConfig {
}
