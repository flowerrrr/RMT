package de.flower.common.ui.markup.html.basic;

import de.flower.common.util.Strings;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Inserts soft-hyphens into the displayed label.
 */
public class WordBreakLabel extends Label {

    private final static int nChars = 5;

    public WordBreakLabel(final String id, final String label) {
        super(id, label);
    }

    @Override
    public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
        replaceComponentTagBody(markupStream, openTag, Strings.insertSoftHyphens(getDefaultModelObjectAsString(), nChars));
    }

}
