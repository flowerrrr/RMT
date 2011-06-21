package de.flower.common.ui.ajax.updatebehavior;

import de.flower.common.logging.Slf4jUtil;
import org.apache.wicket.util.tester.WicketTester;
import org.slf4j.Logger;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @author oblume
 */
public class AjaxUpdateBehaviorTest {

    private final static Logger log = Slf4jUtil.getLogger();

    private WicketTester wicketTester = new WicketTester();

    @Test
    public void testFooBar() {

        wicketTester.startPage(new AjaxUpdateBehaviorTestPage());
        log.info(wicketTester.getLastResponse().getDocument());
        wicketTester.debugComponentTrees();
        wicketTester.clickLink("link1");
        String document = wicketTester.getLastResponse().getDocument();
        log.info(document);
        assertTrue(document.contains(">bar<"));

    }

}
