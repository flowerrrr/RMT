package de.flower.rmt.ui.page.blog;

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class BlogPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        testData.createBlogArticles(10);
        wicketTester.startPage(BlogPage.class);
        wicketTester.dumpPage();
    }
}
