package de.flower.common.ui.modal;

import de.flower.common.ui.Css;
import de.flower.common.util.Check;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.util.iterator.ComponentHierarchyIterator;


/**
 * Modal window class. Wraps wickets {@link ModalWindow} and sets some commonly used defaults.
 */
public class ModalDialogWindow extends ModalWindow {
    // Exactly enough to allow for 10 "twitter bootstrap columns" in the modal window.

    private static final int NUM_COLUMNS = 10;

    private static final int COLUMN_WIDTH = 60;

    private static final int ADDITIONAL_WIDTH = 42;

    /**
     * Instantiates a new modal dialog window.
     *
     * @param id the id
     */
    public ModalDialogWindow(final String id) {
        super(id);

        setWindowClosedCallback(null); // no idea why it is enabled by default in wicket
        setCssClassName(Css.MODAL);
        // the next lines will cause javascript errors in IE 8. statements rendered before jquery link is output.
        // add(new CloseOnEscBehavior(this));
        // add(new FocusFirstInputBehavior());
        reset();
    }

    /**
     * Reset the ModalDialogWindow's behavior. Call this to switch a modal dialog window back to default before
     * configuring it and adding new content. (This will automatically be done if you use the showContent() shortcut.)
     *
     * @return this
     */
    public final ModalDialogWindow reset() {
        setBootstrapColumns(NUM_COLUMNS);
        setResizable(false);
        setCookieName(null);
        // setAutoSize(true);
        setUseInitialHeight(false);
        return this;
    }

    /**
     * Obtain the modal window from the InstallerBasePage.
     *
     * @param page the page holding reference to a modal window.
     * @return the current modal window
     */
    public static ModalDialogWindow get(final Page page) {
        final ModalDialogWindow modalDialogWindow = (ModalDialogWindow) new ComponentHierarchyIterator(page)
                .filterByClass(ModalDialogWindow.class).getFirst(false);
        Check.notNull(modalDialogWindow, "No component of type [" + ModalDialogWindow.class.getSimpleName()
                + "] found in page.");
        return modalDialogWindow;
    }

    /**
     * Helper to easily show a modal window.
     *
     * @param parent     the component added to an installer base page wanting to show a modal dialog
     * @param content    the content (panel) to be shown in the modal dialog
     * @param numColumns the required number of bootstrap columns for the content
     */
    public static void showContent(final Component parent, final Component content, final int numColumns) {
        get(parent.getPage()).reset().setContent(content, numColumns).show(AjaxRequestTarget.get());
    }

    /**
     * Set the window width to the specified number of bootstrap columns.
     *
     * @param numColumns the number of bootstrap columns to fit within the window
     * @return this
     */
    public final ModalDialogWindow setBootstrapColumns(final int numColumns) {
        return (ModalDialogWindow) super.setInitialWidth(numColumns * COLUMN_WIDTH + ADDITIONAL_WIDTH);
    }

    /**
     * Sets the content of the modal window.
     *
     * @param component component to set
     * @return this;
     * @deprecated Use #setContent(component, numColumns) instead.
     */
    @Override
    @Deprecated
    public final ModalWindow setContent(final Component component) {
        return super.setContent(component);
    }

    /**
     * Sets the content of the modal window along with the required width.
     *
     * @param component  component to set
     * @param numColumns width in bootstrapColumns the content requires
     * @return this;
     */
    public final ModalDialogWindow setContent(final Component component, final int numColumns) {
        super.setContent(component);
        setBootstrapColumns(numColumns);
        return this;
    }

    /**
     * Fix for https://issues.apache.org/jira/browse/WICKET-1771.
     * <p/>
     * Allows to use
     * <p/>
     * <pre>
     * modal.close();
     * modal.setContent(new Panel());
     * modal.show();
     * </pre>
     * <p/>
     * in one ajax request.
     *
     * @return the show java script
     */
    @Override
    protected final CharSequence getShowJavaScript() {
        final String s = "setTimeout(function() { " + super.getShowJavaScript().toString().replace("\n", "") + "; }, 0);\n";
        return s;
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);
        response.renderJavaScript("Wicket.Window.unloadConfirmation = false;", null);
    }
}
