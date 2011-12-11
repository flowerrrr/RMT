package de.flower.rmt.ui.common.form.field;

import de.flower.rmt.test.AbstractWicketTests;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * @author flowerrrr
 */
public class FormFieldPanelTest extends AbstractWicketTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new FormFieldPanelTestPage());
        wicketTester.dumpPage();
        wicketTester.assertContains("fooName");
    }

    private static class FormFieldPanelTestPage extends WebPage {

        public FormFieldPanelTestPage() {
            TestEntity entity = new TestEntity("fooName", "fooTime");
            Form form = new Form<TestEntity>("form", new CompoundPropertyModel<TestEntity>(entity));
            add(form);
            form.add(new TextFieldPanel("name"));
            form.add(new DropDownChoicePanel<String>("time", Arrays.asList("a", "b", "c")));
        }

        public Markup getAssociatedMarkup() {
            return Markup.of("<html><body>\n"
                    + "<form wicket:id='form'>\n"
                    // can use whatever tag we like, since the tag is rewritten by the panel
                    + "<foobar wicket:id='name' labelKey='label.name' />\n"
                    + "<rmt:select wicket:id='time' labelKey='label.time' />\n"
                    + "</form>\n"
                    + "</body></html>");
        }
    }

    private static class TestEntity {

        private String name;

        private String time;

        private TestEntity(final String name, final String time) {
            this.name = name;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(final String time) {
            this.time = time;
        }
    }
}