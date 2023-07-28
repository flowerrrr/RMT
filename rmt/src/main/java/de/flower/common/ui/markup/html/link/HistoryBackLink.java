package de.flower.common.ui.markup.html.link;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.ExternalLink;

public class HistoryBackLink extends ExternalLink {

    public HistoryBackLink(final String id) {
        super(id, "#");
        add(AttributeModifier.replace("onclick", "window.history.back();return false;"));
    }
}
