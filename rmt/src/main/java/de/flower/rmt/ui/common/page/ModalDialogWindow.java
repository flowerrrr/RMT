package de.flower.rmt.ui.common.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

/**
 * @author flowerrrr
 */
public class ModalDialogWindow extends ModalWindow {


    public ModalDialogWindow(String id) {
        super(id);

        setTitle("TODO");
        setResizable(false);
        setCookieName("modal-" + id);

/*
        modal2.setCloseButtonCallback(new ModalWindow.CloseButtonCallback()
        {
            public boolean onCloseButtonClicked(AjaxRequestTarget target)
            {
                setResult("Modal window 2 - close button");
                return true;
            }
        });
*/

        setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
            public void onClose(AjaxRequestTarget target) {
                // target.add(result);
            }
        });

    }
}
