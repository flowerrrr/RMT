package de.flower.common.aop

import org.testng.annotations.Test
import org.testng.Assert._
import de.flower.rmt.repository.ITeamRepo
import org.springframework.beans.factory.annotation.{Configurable, Autowired}
import de.flower.rmt.test.AbstractIntegrationTests

/**
 * 
 * @author flowerrrr
 */

class ConfigurableTest extends AbstractIntegrationTests {

    @Test
    def testConfigurableInjection() {
        // instantiate a simple bean with new and see if spring is autowiring the dependencies.
        val foo = new ConfigurableFoo()
        assertNotNull(foo.getTeamRepo(), "Dependency was not injected.")
    }
}

@Configurable
class ConfigurableFoo {

    @Autowired
    private var teamRepo: ITeamRepo = _

    def getTeamRepo(): ITeamRepo = {
        return teamRepo
    }

}