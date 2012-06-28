package de.flower.rmt.ui.page.base;

import de.flower.common.ui.feedback.AlertMessageFeedbackPanel;
import de.flower.common.ui.markup.html.panel.WrappingPanel;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.ui.app.IPropertyProvider;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.feedback.MessageOfTheDayMessage;
import de.flower.rmt.ui.feedback.PasswordChangeRequiredMessage;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * This class only defines layout but not content.
 * Content is provided by subclasses.
 *
 * @author flowerrrr
 */
public abstract class AbstractBaseLayoutPage extends AbstractBasePage {

    @SpringBean
    private IPropertyProvider propertyProvider;

    @SpringBean
    private ISecurityService securityService;

    private Panel secondaryPanel;

    private Label heading;

    private Label subheading;

    private WebMarkupContainer container;

    public AbstractBaseLayoutPage() {
        this(null);
    }

    public AbstractBaseLayoutPage(final IModel<?> model) {
        super(model);

        container = new WebMarkupContainer("container");
        add(container);
        container.add(AttributeModifier.append("class", BasePanel.getCssClass(getClass())));

        container.add(new AlertMessageFeedbackPanel("alertMessagesPanel") {
            @Override
            public boolean isVisible() {
                return showAlertMessages();
            }
        });

        container.add(heading = new Label("heading", Model.of(getClass().getSimpleName())));
        container.add(subheading = new Label("subheading", Model.of("")));

        add(Links.aboutLink("aboutLink"));
        add(Links.mailLink("adminLink", propertyProvider.getAdminEmail(), false));
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
            info(new MessageOfTheDayMessage());
            if (securityService.isCurrentUserLoggedIn() && securityService.getUser().hasInitialPassword()) {
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
        getSecondaryPanel().add(components);
    }

    public Panel getSecondaryPanel() {
        if (secondaryPanel == null) {
            secondaryPanel = new WrappingPanel("secondaryPanel");
            container.add(secondaryPanel);
        }
        return secondaryPanel;
    }

    /**
     * Allow subclasses to disable alert messages (e.g. Error pages).
     *
     * @return
     */
    protected boolean showAlertMessages() {
        return true;
    }
}
