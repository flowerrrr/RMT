package org.wicketstuff.jsr303.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wicketstuff.jsr303.JSR303ValidationFactory;
import org.wicketstuff.jsr303.ViolationMessageRenderer;

import javax.validation.Validator;

/**
 * @author flowerrrr
 */
@Configuration
public class JSR303SpringConfig {

    @Autowired
    JSR303ValidationFactory jsr303ValidationFactory;

    @Bean
    JSR303ValidationFactory jsr303ValidationFactory() {
        return new JSR303ValidationFactory();
    }

    @Bean
    Validator wicketValidator() {
        return jsr303ValidationFactory.getValidator();
    }

    @Bean
    ViolationMessageRenderer violationMessageRenderer() {
        return jsr303ValidationFactory.getViolationMessageRenderer();
    }

}
