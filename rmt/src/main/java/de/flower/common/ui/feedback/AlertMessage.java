package de.flower.common.ui.feedback;

import org.apache.wicket.model.IModel;

import java.io.Serializable;

/**
 * @author flowerrrr
 */
public abstract class AlertMessage implements Serializable {

    private IModel<String> messageModel;

    private IModel<String> buttonLabelModel;

    /**
     *
     * @param messageModel
     * @param buttonLabelModel if null, no link is rendered.
     */
    public AlertMessage(final IModel<String> messageModel, IModel<String> buttonLabelModel) {
        this.messageModel = messageModel;
        this.buttonLabelModel = buttonLabelModel;
    }

    /**
     *
     * @param alertMessagePanel
     * @return true if message should be dismissed after clicking button.
     */
    protected abstract boolean onClick(final AlertMessagePanel alertMessagePanel);

    public IModel<String> getMessageModel() {
        return messageModel;
    }

    public void setMessageModel(final IModel<String> messageModel) {
        this.messageModel = messageModel;
    }

    public IModel<String> getButtonLabelModel() {
        return buttonLabelModel;
    }

    protected boolean isVisible(final AlertMessagePanel alertMessagePanel) {
        return isVisible();
    }

    protected boolean isVisible() {
        return true;
    }

    /**
     * Returns string identifying this message. Key is used to mark messages as closed
     * for the current session. By default returns messageModel.getObject().
     * Subclasses can override if messages with same messageModel need to be handled separately.
     *
     * @return
     */
    public String getSessionKey() {
        return messageModel.getObject();
    }

    /**
     * Subclasses can override if they want to contribute html markup in the message.
     * @return
     */
    public boolean getEscapeModelStrings() {
        return true;
    }
}
