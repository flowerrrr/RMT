package de.flower.rmt.ui.markup.html.form.field;

import de.flower.common.test.wicket.WicketTesterHelper;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.testng.annotations.Test;

import java.util.Arrays;


public class FormFieldPanelTest extends AbstractRMTWicketMockitoTests {

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
            form.add(new CheckBoxPanel("flag"));
        }

        public Markup getAssociatedMarkup() {
            return WicketTesterHelper.creatPageMarkup("<html><body>\n"
                    + "<form wicket:id='form'>\n"
                    // can use whatever tag we like, since the tag is rewritten by the panel
                    + "<foobar wicket:id='name' labelKey='login.username' />\n"
                    + "<rmt:select wicket:id='time' labelKey='login.password' />\n"
                    + "<rmt:checkbox wicket:id='flag' labelKey='login.username' />\n"
                    + "</form>\n"
                    + "</body></html>", this);
        }
    }

    private static class TestEntity {

        private String name;

        private String time;

        private Boolean flag;

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

        public Boolean getFlag() {
            return flag;
        }

        public void setFlag(final Boolean flag) {
            this.flag = flag;
        }
    }
}