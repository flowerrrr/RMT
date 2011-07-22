package de.flower.rmt.service

import de.flower.test.AbstractIntegrationTests
import org.testng.annotations.Test
import org.testng.Assert._
import org.hibernate.LazyInitializationException
import de.flower.rmt.model.{Users, Users_}

/**
 * 
 * @author oblume
 */

class UserManagerTest extends AbstractIntegrationTests {

    /**
     * Test if clients of service layer can specify
     * eager fetching of domain objects to avoid lazy init execptions.
     */
    @Test
    def testEagerFetching() {
        var users = userManager.findAll(Users_.roles)
        assertTrue(users.size() > 0)
        var user = users.get(0)
        log.info(user.getRoles().get(0).getAuthority())
    }

    /**
     * Test that forces layz init ex.
     */
    @Test
    def testLazyInitException() {
        var users = userManager.findAll()
        assertTrue(users.size() > 0)
        var user = users.get(0)
        intercept[LazyInitializationException] {
            log.info(user.getRoles().get(0).getAuthority())
        }
    }

    @Test
    def testSaveNewUser() {
        val user = userManager.newPlayerInstance();
        user.setEmail("foo@bar.com")
        user.setFullname("Foo Bar")
        userManager.save(user)
    }




}