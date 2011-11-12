package de.flower.rmt.ui.common.page;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * This class only defines layout but not content.
 * Content is provided by subclasses.
 *
 * @author flowerrrr
 */
public abstract class AbstractBaseLayoutPage extends AbstractBasePage {

    public AbstractBaseLayoutPage(final IModel<?> model) {
        super(model);
    }

    protected void addHeading(String headingResourceKey) {
        add(new Label("heading", new ResourceModel(headingResourceKey)));
        add(new Label("subheading", new ResourceModel(headingResourceKey + ".sub")));
    }

    protected void addHeading(String headingResourceKey, final String subHeadingResourceKey) {
        add(new Label("heading", new ResourceModel(headingResourceKey)));
        add(new Label("subheading", new ResourceModel(subHeadingResourceKey)) {
            @Override
            public boolean isVisible() {
                return subHeadingResourceKey != null;
            }
        });
    }

    protected void addMainPanel(Component ... components) {
        add(new WrappingPanel("mainPanel", components));
    }

    protected void addSecondaryPanel(Component ... components) {
        add(new WrappingPanel("secondaryPanel", components));
    }

}
