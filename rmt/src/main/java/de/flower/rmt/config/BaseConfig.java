package de.flower.rmt.config;

import de.flower.common.spring.SpringApplicationContextBridge;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class BaseConfig {

    @Bean
    public SpringApplicationContextBridge springApplicationContextBridge() {
        return SpringApplicationContextBridge.getInstance();
    }

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setIgnoreResourceNotFound(true);
        configurer.setLocations(new Resource[]{
                        new ClassPathResource("application.properties"),
                        new ClassPathResource("application.target.properties"),
                        new ClassPathResource("credentials.properties")
                }
        );
        return configurer;
    }
}
