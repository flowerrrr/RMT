package de.flower.rmt.test;

import de.flower.rmt.ui.app.TestRMTApplication;
import org.springframework.context.annotation.*;
import org.wicketstuff.jsr303.spring.JSR303SpringConfig;

/**
 * @author flowerrrr
 */
@Configuration
@ImportResource({"classpath:/applicationContext-base.xml",
        "classpath:/applicationContext-dao.xml",
        "classpath:/applicationContext-service.xml",
        "classpath:/applicationContext-security.xml",
        "classpath:/applicationContext-ui.xml",
        "classpath:/applicationContext-test.xml"})
@ComponentScan(basePackages = {"de.flower.common.test", "de.flower.rmt.test"})
// although defined in an xml-context we have to manually import other javaconfig-files.
@Import({JSR303SpringConfig.class})
public class AbstractRMTIntegrationTestsConfig {


    /**
     * override RMTApplication and use TestApplication instead.
     */
    @Bean
    TestRMTApplication wicketApplication() {
        return new TestRMTApplication();
    }
}
