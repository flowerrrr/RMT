package de.flower.common.ui.modal;

import de.flower.common.test.wicket.AbstractWicketUnitTests;
import de.flower.common.test.wicket.WicketTesterHelper;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.WebPage;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class ModalDialogWindowTest extends AbstractWicketUnitTests {

    @Test
    public void testShow() {
        wicketTester.startPage(new TestPage());
        wicketTester.clickLink("link");
        wicketTester.dumpPage();
    }

    public static class TestPage extends WebPage {

        public TestPage() {
            add(new ModalDialogWindow("modalWindow"));
            add(new AjaxLink<Void>("link") {
                @Override
                public void onClick(final AjaxRequestTarget target) {
                    ModalDialogWindow.showContent(this, new TestPanel(), 5);
                }
            });
        }

        @Override
        public Markup getAssociatedMarkup() {
            return WicketTesterHelper.creatPageMarkup("<html><body><div wicket:id='modalWindow'/><a wicket:id='link'>open</a></body></html>", this);
        }
    }

    public static class TestPanel extends ModalPanel {

        @Override
        public Markup getAssociatedMarkup() {
            return Markup.of("<wicket:panel><div>hallo</div></wicket:panel>");
        }
    }
}
