package de.flower.rmt.ui.page.blog;

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class ArticlePageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        testData.createBlogArticles(1);
        Long id = blogManager.findAllArticles(0, 1).get(0).getId();
        wicketTester.startPage(ArticlePage.class, ArticlePage.getPageParams(id));
        wicketTester.dumpPage();
    }
}
