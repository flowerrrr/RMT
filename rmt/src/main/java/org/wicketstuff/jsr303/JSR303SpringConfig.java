package org.wicketstuff.jsr303;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validator;

/**
 * @author flowerrrr
 */
@Configuration
public class JSR303SpringConfig {

    @Bean
    Validator wicketValidator() {
        return JSR303Validation.getValidator();
    }

}
