package de.flower.rmt.ui.common.page;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * This class only defines layout but not content.
 * Content is provided by subclasses.
 *
 * @author flowerrrr
 */
public abstract class AbstractBaseLayoutPage extends AbstractBasePage {

    private Panel secondaryPanel;

    public AbstractBaseLayoutPage(final IModel<?> model) {
        super(model);

        add(new WelcomeMessagesPanel());
    }

    /**
     * In case no secondary panel was added use default panel.
     */
    @Override
    protected void onInitialize() {
        super.onInitialize();
        if (secondaryPanel == null) {
            addSecondaryPanel(new DefaultSecondaryPanel());
        }
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

    protected void addHeadingText(String text) {
        add(new Label("heading", text));
        add(new Label("subheading", "").setVisible(false)); // currently not used
    }

    protected void addMainPanel(Component ... components) {
        add(new WrappingPanel("mainPanel", components));
    }

    protected void addSecondaryPanel(Component ... components) {
        secondaryPanel = new WrappingPanel("secondaryPanel", components);
        add(secondaryPanel);
    }

    public Panel getSecondaryPanel() {
        return secondaryPanel;
    }
}
