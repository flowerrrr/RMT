package de.flower.rmt.ui.page.base;

import de.flower.common.ui.feedback.AlertMessageFeedbackPanel;
import de.flower.common.ui.markup.html.panel.WrappingPanel;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.panel.BasePanel;
import de.flower.rmt.ui.panel.feedback.PasswordChangeRequiredMessage;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
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

    WebMarkupContainer container;

    public AbstractBaseLayoutPage() {
        this(null);
    }

    public AbstractBaseLayoutPage(final IModel<?> model) {
        super(model);

        container = new WebMarkupContainer("container");
        add(container);
        container.add(AttributeModifier.append("class", BasePanel.getCssClass(getClass())));

        container.add(new AlertMessageFeedbackPanel("alertMessagesPanel"));

        container.add(heading = new Label("heading", Model.of(getClass().getSimpleName())));
        container.add(subheading = new Label("subheading", Model.of("")));

        add(Links.aboutLink("aboutLink"));
        add(Links.adminMailLink("adminLink", false));
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
        super.onBeforeRender();
        // makes messages back-button and reload-save
        if (showAlertMessages()) {
            if (isCurrentUserLoggedIn()) {
                info(new PasswordChangeRequiredMessage());
            }
        }
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
        container.add(new WrappingPanel("mainPanel", components));
    }

    protected void addSecondaryPanel(Component... components) {
        secondaryPanel = new WrappingPanel("secondaryPanel", components);
        container.add(secondaryPanel);
    }

    public Panel getSecondaryPanel() {
        return secondaryPanel;
    }

    protected boolean showAlertMessages() {
        return true;
    }
}
