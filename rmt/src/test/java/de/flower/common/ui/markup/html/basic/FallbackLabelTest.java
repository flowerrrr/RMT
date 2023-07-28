package de.flower.common.ui.markup.html.basic;

import de.flower.common.test.wicket.AbstractWicketUnitTests;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;


public class FallbackLabelTest extends AbstractWicketUnitTests {

    @Test
    public void testFallbackLabel() {
        IModel<String> emptyModel = Model.of("");
        IModel<String> defaultModel = Model.of("This is the default value");
        wicketTester.startComponentInPage(new FallbackLabel("label", emptyModel, defaultModel));
        wicketTester.dumpComponentWithPage();
        wicketTester.assertContains(defaultModel.getObject());
    }

    @Test
    public void testWithCompoundPropertyModel() {
        IModel<String> defaultModel = Model.of("This is the default value");
        wicketTester.startComponentInPage(new TestPanel(new TestEntity(), defaultModel));
        wicketTester.dumpComponentWithPage();
        wicketTester.assertContains(defaultModel.getObject());
    }

    private static class TestPanel extends Panel {

        public TestPanel(TestEntity entity, final IModel<String> defaultModel) {
            super("panel", new CompoundPropertyModel(entity));
            add(new FallbackLabel("name", defaultModel));
        }

        @Override
        public Markup getAssociatedMarkup() {
            return Markup.of("<wicket:panel><span wicket:id='name' /></wicket:panel>");
        }
    }

    private static class TestEntity {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }
    }

}
