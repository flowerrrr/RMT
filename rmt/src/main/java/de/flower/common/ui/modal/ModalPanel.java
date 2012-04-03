package de.flower.common.ui.modal;


import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.model.IModel;

/**
 * Base class for Panels that are displayed in a modal window.
 * Provides extension points for subclasses to hook into the closing event of the modal window.
 */
public abstract class ModalPanel extends BasePanel {

    public ModalPanel() {
        this(null);
    }

    public ModalPanel(final IModel<?> model) {
        super(ModalWindow.CONTENT_ID, model);
    }

    /**
     * Constructor for subpanels in modal windows. Main panel of modal window must always use
     * predefined id CONTENT_ID. Subpanels are free to use any id.
     *
     * @param id the id
     * @param model the model
     */
    public ModalPanel(final String id, final IModel<?> model) {
        super(id, model);
    }

    /**
     * Close the containing modal window.
     *
     * @param target the target
     */
    protected final void close(final AjaxRequestTarget target) {
        onBeforeClose(target);
        ModalDialogWindow.closeCurrent(target);
        onAfterClose(target);
    }

    /**
     * Subclasses can override this method.
     *
     * @param target the target
     */
    protected void onBeforeClose(final AjaxRequestTarget target) {
    }

    /**
     * Subclasses can override this method.
     *
     * @param target the target
     */
    protected void onAfterClose(final AjaxRequestTarget target) {
    }


}
