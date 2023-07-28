package org.wicketstuff.jsr303.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wicketstuff.jsr303.DefaultViolationMessageRenderer;
import org.wicketstuff.jsr303.JSR303ValidationFactory;

import javax.validation.Validator;

/**
 * @author flowerrrr
 */
@Configuration
public class JSR303SpringConfig {

    @Bean
    JSR303ValidationFactory jsr303ValidationFactory() {
        return new JSR303ValidationFactory();
    }

    @Bean
    Validator wicketValidator() {
        return jsr303ValidationFactory().getValidator();
    }

    @Bean
    DefaultViolationMessageRenderer violationMessageRenderer() {
        return jsr303ValidationFactory().getViolationMessageRenderer();
    }

}
