package de.flower.rmt.test;

import de.flower.common.test.mock.MockJavaMailSender;
import de.flower.rmt.config.*;
import de.flower.rmt.ui.app.TestRMTApplication;
import org.springframework.context.annotation.*;

/**
 * @author flowerrrr
 */
@Configuration
@Import({
        BaseConfig.class,
        DaoConfig.class,
        ServiceConfig.class,
        SecurityConfig.class,
        UIConfig.class
})
@ComponentScan(basePackages = {
        "de.flower.common.test",
        "de.flower.rmt.test"
})
public class AbstractRMTIntegrationTestsConfig {

    /**
     * Replace RMTApplication and use TestApplication instead.
     */
    @Bean
    public TestRMTApplication testWicketApplication() {
        return new TestRMTApplication();
    }

    // Overriding mailSender to avoid sending mails by unit tests.
    // Overriding in JavaConfig doesn't seem to work.
    @Bean
    @Primary
    public MockJavaMailSender testMailSender() {
        return new MockJavaMailSender();
    }

}
