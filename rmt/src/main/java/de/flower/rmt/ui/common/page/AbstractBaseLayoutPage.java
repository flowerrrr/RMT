package de.flower.rmt.ui.common.page;

import de.flower.rmt.ui.common.panel.AlertMessagesPanel;
import de.flower.rmt.ui.common.panel.PasswordChangeRequiredMessage;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

/**
 * This class only defines layout but not content.
 * Content is provided by subclasses.
 *
 * @author flowerrrr
 */
public abstract class AbstractBaseLayoutPage extends AbstractBasePage {

    private Panel secondaryPanel;

    private Label heading;

    private Label subheading;

    public AbstractBaseLayoutPage(final IModel<?> model) {
        super(model);

        add(new AlertMessagesPanel("alertMessagesPanel"));

        add(heading = new Label("heading", Model.of(getClass().getSimpleName())));
        add(subheading = new Label("subheading", Model.of("")));
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

    @Override
    protected void onBeforeRender() {
        // makes messages back-button save
        info(new PasswordChangeRequiredMessage());
        super.onBeforeRender();
    }

    protected void setHeading(String headingResourceKey) {
        setHeading(headingResourceKey, headingResourceKey + ".sub");
    }

    protected void setHeading(String headingResourceKey, final String subHeadingResourceKey) {
        heading.setDefaultModel(new ResourceModel(headingResourceKey));
        if (subHeadingResourceKey != null) {
            subheading.setDefaultModel(new ResourceModel(subHeadingResourceKey));
        }
    }

    protected void setHeadingText(String text) {
        heading.setDefaultModel(Model.of(text));
    }

    protected void setSubheadingText(String text) {
        subheading.setDefaultModel(Model.of(text));
    }

    protected void addMainPanel(Component... components) {
        add(new WrappingPanel("mainPanel", components));
    }

    protected void addSecondaryPanel(Component... components) {
        secondaryPanel = new WrappingPanel("secondaryPanel", components);
        add(secondaryPanel);
    }

    public Panel getSecondaryPanel() {
        return secondaryPanel;
    }

}
