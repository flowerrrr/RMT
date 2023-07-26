package de.flower.rmt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        BaseConfig.class,
        DaoConfig.class,
        ServiceConfig.class,
        SecurityConfig.class,
        UIConfig.class,
        TaskConfig.class
})
public class SpringConfig {
}
