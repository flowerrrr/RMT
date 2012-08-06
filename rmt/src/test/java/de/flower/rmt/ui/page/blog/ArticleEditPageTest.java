package de.flower.rmt.ui.page.blog;

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import de.flower.rmt.ui.model.BArticleModel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class ArticleEditPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new ArticleEditPage(new BArticleModel(blogManager.newArticle(securityService.getUser()))));
        wicketTester.dumpPage();
        wicketTester.dumpComponentWithPage();
    }
}
