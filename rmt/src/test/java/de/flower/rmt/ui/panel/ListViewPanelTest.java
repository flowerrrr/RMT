package de.flower.rmt.ui.panel;

import de.flower.common.model.IEntity;
import de.flower.common.test.wicket.AbstractWicketUnitTests;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author flowerrrr
 */
public class ListViewPanelTest extends AbstractWicketUnitTests {

    @Test
    public void testRender() {
        wicketTester.startComponentInPage(new TestPanel(Model.of(Arrays.asList(new TestEntity(), new TestEntity()))));
        wicketTester.dumpComponentWithPage();
        wicketTester.assertInvisible("panel:listContainer:noEntry");
    }

    @Test
    public void testNoEntry() {
        wicketTester.startComponentInPage(new TestPanel(Model.ofList(new ArrayList())));
        wicketTester.dumpComponentWithPage();
        // wicketTester.debugComponentTrees();
        wicketTester.assertVisible("panel:listContainer:noEntry");
    }

    public static class TestPanel extends Panel {

        public TestPanel(IModel<?> listModel) {
            super("panel");

            ListViewPanel listView = new ListViewPanel<IEntity>("listContainer", (IModel<List<IEntity>>) listModel, Model.of("no entry in this list")) {

                // @Override
                protected void populateItem(final ListItem<IEntity> item) {
                    item.add(new Label("label", item.getModelObject().toString()));
                }
            };
            add(listView);
        }

        @Override
        public Markup getAssociatedMarkup() {
            return Markup.of("<wicket:panel><div wicket:id='listContainer'><table><tr><td>Label</td></tr>\n<tr wicket:id='list'><td><span wicket:id='label'/></td>\n</tr>\n</table></div></wicket:panel>");
        }
    }

    public static class TestEntity implements IEntity {

        @Override
        public boolean isNew() {
            return false;
        }

        @Override
        public Long getId() {
            return (long) hashCode();
        }
    }
}