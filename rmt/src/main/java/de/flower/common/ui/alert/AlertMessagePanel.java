package de.flower.common.ui.alert;

import de.flower.common.ui.ajax.markup.html.AjaxLink;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.protocol.http.WebSession;

import java.lang.reflect.Method;


public class AlertMessagePanel extends Panel {

    private AlertMessage alertMessage;

    public AlertMessagePanel(String id, final AlertMessage alertMessage) {
        super(id);
        setOutputMarkupId(true);
        this.alertMessage = alertMessage;

        Label messageLabel = new Label("message", alertMessage.getMessageModel());
        messageLabel.setEscapeModelStrings(alertMessage.getEscapeModelStrings());
        add(messageLabel);

        Link link = new Link("link") {
            @Override
            public void onClick() {
                // dismiss message for this session
                boolean hideMessage = alertMessage.onClick(AlertMessagePanel.this);
                if (hideMessage) {
                    setHideMessage(getSessionKey(), true);
                }
            }

            @Override
            public boolean isVisible() {
                return alertMessage.getButtonLabelModel() != null;
            }

            /**
             * When clicking this link the feedback message is already gone from
             * the session, so this link and its surrounding component are invisible
             * and wouldn't allow processing the link.
             */
            @Override
            public boolean canCallListenerInterface(final Method method) {
                return true;
            }
        };

        add(link);
        link.add(new Label("label", alertMessage.getButtonLabelModel()));

        AjaxLink closeButton = new AjaxLink("closeButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                setHideMessage(getSessionKey(), true);
                target.add(AlertMessagePanel.this);
            }

            /**
             * When clicking this link the feedback message is already gone from
             * the session, so this link and its surrounding component are invisible
             * and wouldn't allow processing the link.
             */
            @Override
            public boolean canCallListenerInterface(final Method method) {
                return true;
            }
        };
        closeButton.add(AttributeModifier.replace("title", new ResourceModel("alert.message.close.button.title")));
        add(closeButton);

    }

    @Override
    public boolean isVisible() {
        return alertMessage.isVisible(this) && !isHideMessage(getSessionKey());
    }

    private String getSessionKey() {
        return "ALERTMESSAGE_KEY_" + alertMessage.getSessionKey();
    }

    private void setHideMessage(String key, boolean hide) {
        WebSession.get().setAttribute(key, hide);
    }

    private boolean isHideMessage(String key) {
        Boolean hide = (Boolean) WebSession.get().getAttribute(key);
        return (hide != null && hide);
    }

}
