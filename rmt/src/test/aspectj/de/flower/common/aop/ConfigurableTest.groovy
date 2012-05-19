package de.flower.common.aop

import de.flower.rmt.repository.ITeamRepo
import de.flower.rmt.test.AbstractRMTIntegrationTests
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable
import org.testng.annotations.Test
import static org.testng.Assert.assertNotNull

/**
 * 
 * @author flowerrrr
 */
@Deprecated // not working
class ConfigurableTest extends AbstractRMTIntegrationTests {

    @Test(enabled = false)
    def void testConfigurableInjection() {
        // instantiate a simple bean with new and see if spring is autowiring the dependencies.
        def foo = new ConfigurableFoo()
        assertNotNull(foo.getTeamRepo(), "Dependency was not injected.")
    }
}

@Configurable
class ConfigurableFoo {

    @Autowired
    ITeamRepo teamRepo

}