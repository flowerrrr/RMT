package de.flower.common.ui.modal;

import de.flower.common.ui.panel.BasePanel;
import org.apache.wicket.markup.html.form.Form;


public class ModalDialogWindowPanel extends BasePanel {

    public ModalDialogWindowPanel() {
        super();
        Form form = new Form("form");
        add(form);
        form.add(new ModalDialogWindow("modalWindow"));
    }


}
