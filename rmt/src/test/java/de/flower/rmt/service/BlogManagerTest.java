package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */

public class BlogManagerTest extends AbstractRMTIntegrationTests {

    @Test
    public void testHasUnread() {
        User user = securityService.getUser();
        assertFalse(blogManager.hasUnreadArticleOrComment(user));

        User author = userManager.loadById(3L);
        assertNotEquals(user, author);
        testData.createBlogArticle(author, false);
        assertTrue(blogManager.hasUnreadArticleOrComment(user));

        blogManager.markAllRead(user);
        assertFalse(blogManager.hasUnreadArticleOrComment(user));
    }
}