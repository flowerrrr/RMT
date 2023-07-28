package de.flower.rmt.ui.page.base;

import de.flower.common.ui.alert.AlertMessage;
import de.flower.common.ui.alert.AlertMessagePanel;
import de.flower.rmt.service.ApplicationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;


public class MessageOfTheDayMessage extends AlertMessage {

    @SpringBean
    private ApplicationService applicationService;

    public MessageOfTheDayMessage() {
        super(null, null);
        Injector.get().inject(this); // to make @SpringBean work
        setMessageModel(getMOTDModel());
    }

    @Override
    public boolean onClick(final AlertMessagePanel alertMessagePanel) {
        throw new UnsupportedOperationException("This message does not display a clickable button!");
    }

    @Override
    public boolean isVisible(final AlertMessagePanel alertMessagePanel) {
        return !StringUtils.isBlank(getMessageModel().getObject());
    }

    IModel<String> getMOTDModel() {
        return Model.of(applicationService.getMessageOfTheDay());
    }

    /**
     * Motd might contain html markup.
     * @return
     */
    @Override
    public boolean getEscapeModelStrings() {
        return false;
    }
}
