package de.flower.rmt.ui.common.page.error;

import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.markup.MarkupException;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.pages.ExceptionErrorPage;

/**
 * Copied from wickets ExceptionErrorPage.
 * @author flowerrrr
 */
public class StacktracePanel extends BasePanel {

    private Throwable throwable;

    public StacktracePanel(Exception e) {
        this.throwable = e;

		// Add exception label
		add(new MultiLineLabel("exception", getErrorMessage(throwable)));

		add(new MultiLineLabel("stacktrace", getStackTrace(throwable)));

		// Get values
		String resource = "";
		String markup = "";
		MarkupStream markupStream = null;

		if (throwable instanceof MarkupException)
		{
			markupStream = ((MarkupException)throwable).getMarkupStream();

			if (markupStream != null)
			{
				markup = markupStream.toHtmlDebugString();
				resource = markupStream.getResource().toString();
			}
		}

		// Create markup label
		final MultiLineLabel markupLabel = new MultiLineLabel("markup", markup);

		markupLabel.setEscapeModelStrings(false);

		// Add container with markup highlighted
		final WebMarkupContainer markupHighlight = new WebMarkupContainer("markupHighlight");

		markupHighlight.add(markupLabel);
		markupHighlight.add(new Label("resource", resource));
		add(markupHighlight);

		// Show container if markup stream is available
		markupHighlight.setVisible(markupStream != null);

    }

    private String getErrorMessage(final Throwable throwable) {
        ExceptionErrorPage exceptionErrorPage = new ExceptionErrorPage(throwable, null);
        return exceptionErrorPage.getErrorMessage(throwable);
    }

    private String getStackTrace(final Throwable throwable) {
        ExceptionErrorPage exceptionErrorPage = new ExceptionErrorPage(throwable, null);
        return exceptionErrorPage.getStackTrace(throwable);
    }
}
