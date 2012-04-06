package de.flower.common.ui.modal;

import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.ajax.markup.html.form.AjaxSubmitLink;
import de.flower.common.util.Check;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * Base class for Panels that are displayed in a modal window.
 * Provides default bootstrap-layout and extension points for subclasses to hook into the closing event of the modal window.
 */
public abstract class ModalPanel<T> extends BasePanel<T> {

    private Label heading;

    private Form<T> form;

    private String submitLabelResourceKey = "button.ok";

    public ModalPanel() {
        this(null);
    }

    public ModalPanel(final IModel<T> model) {
        this(ModalWindow.CONTENT_ID, model);
    }

    /**
     * Constructor for subpanels in modal windows. Main panel of modal window must always use
     * predefined id CONTENT_ID. Subpanels are free to use any id.
     *
     * @param id    the id
     * @param model the model
     */
    public ModalPanel(final String id, final IModel<T> model) {
        super(id, model);
        add(heading = new Label("heading", "use #setHeading to provide custom heading"));
        add(new AjaxLink<Void>("closeButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                close(target);
            }
        });

        add(new AjaxLink<Void>("cancelButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                close(target);
            }
        });
    }

    /**
     * Have to delay adding of submit button until subclass has been initialized and form
     * instance is created.
     */
    @Override
    protected void onInitialize() {
        super.onInitialize();
        Check.notNull(form, "Must call #setForm.");
        add(new AjaxSubmitLink("submitButton", form) {
            {
                add(new Label("submitLabel", new ResourceModel(submitLabelResourceKey)));
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
                if (ModalPanel.this.onSubmit(target, (Form<T>) form)) {
                    close(target);
                }
            }
        });
    }

    protected abstract boolean onSubmit(AjaxRequestTarget target, Form<T> form);

    /**
     * Close the containing modal window.
     *
     * @param target the target
     */
    protected final void close(final AjaxRequestTarget target) {
        ModalDialogWindow.closeCurrent(target);
    }

    protected void setHeading(String headingResourceKey) {
        heading.setDefaultModel(new ResourceModel(headingResourceKey));
    }

    protected void addForm(Form<T> form) {
        this.form = form;
        add(form);
    }

    protected void setSubmitLabel(String resourceKey) {
        this.submitLabelResourceKey = resourceKey;
    }
}
