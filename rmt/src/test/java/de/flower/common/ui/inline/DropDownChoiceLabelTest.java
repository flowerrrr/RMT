package de.flower.common.ui.inline;

import de.flower.common.test.wicket.AbstractWicketUnitTests;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author flowerrrr
 */
public class DropDownChoiceLabelTest extends AbstractWicketUnitTests {

    @Override
    @Test
    public void testRender() {
        List<String> choices = Arrays.asList("a", "b", "c");
        wicketTester.startComponentInPage(new TestDropDownChoiceLabel("foo", Model.of((String) null), choices));
        wicketTester.dumpComponentWithPage();
    }

    @Test
    public void testRenderStringPreSelected() {
        List<String> choices = Arrays.asList("a", "b", "c");
        IModel<String> model = Model.of("b");
        wicketTester.startComponentInPage(new TestDropDownChoiceLabel("foo", model, choices));
        wicketTester.dumpComponentWithPage();
    }

    /**
     * Same as #testRenderStringPreSelected but with Object as model type.
     */
    @Test
    public void testRenderObject() {
        List<TestEnum> choices = Arrays.asList(TestEnum.values());
        IModel<TestEnum> model = Model.of(TestEnum.OPTION3);
        wicketTester.startComponentInPage(new TestDropDownChoiceLabel("foo", model, choices));
        wicketTester.dumpComponentWithPage();
        wicketTester.assertContains("<option selected=\"selected\" value=\"2\">" + model.getObject().toString() + "</option>");
    }

    @Test
    public void testRenderObjectWithChoiceRender() {
        List<TestEnum> choices = Arrays.asList(TestEnum.values());
        IModel<TestEnum> model = Model.of(TestEnum.OPTION3);
        TestEnumRenderer renderer = new TestEnumRenderer();
        wicketTester.startComponentInPage(new TestDropDownChoiceLabel("foo", model, choices, renderer));
        wicketTester.dumpComponentWithPage();
        wicketTester.assertContains("\\Q<option selected=\"selected\" value=\"2\">" + renderer.getDisplayValue(model.getObject()) + "</option>\\E");
    }

    @Test
    public void testSelectionChanged() {
        List<String> choices = Arrays.asList("a", "b", "c");
        IModel<String> model = Model.of((String) null);
        wicketTester.startComponentInPage(new TestDropDownChoiceLabel("foo", model, choices));
        wicketTester.dumpComponentWithPage();
        wicketTester.executeAjaxEvent("select", "onchange");
        wicketTester.dumpAjaxResponse();
    }

    private static class TestDropDownChoiceLabel<T> extends DropDownChoiceLabel<T> {

        public TestDropDownChoiceLabel(String id, IModel<T> model, final List<? extends T> choices) {
            super(id, model, choices);
        }

        public TestDropDownChoiceLabel(final String id, final IModel<T> model, final List<T> choices, final IChoiceRenderer<T> renderer) {
            super(id, model, choices, renderer);
        }

        @Override
        protected void onSelectionChanged(final T newSelection) {
            log.info("New selection: [{}]", newSelection);
        }
    }

    private static enum TestEnum {
        OPTION1,
        OPTION2,
        OPTION3;

        public String getDisplayString() {
            return "display-value-for (" + name() + ")";
        }
    }

    private static class TestEnumRenderer implements IChoiceRenderer<TestEnum> {

        @Override
        public Object getDisplayValue(final TestEnum object) {
            return object.getDisplayString();
        }

        @Override
        public String getIdValue(final TestEnum object, final int index) {
            return "" + index;
        }
    }

}
