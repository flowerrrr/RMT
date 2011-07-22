package de.flower.common.ui.form;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author oblume
 */
public class TestTextFieldPanel extends Panel {

    private final static Logger log = LoggerFactory.getLogger(TestTextFieldPanel.class);

    public TestTextFieldPanel(String id) {
        super(id);
        setOutputMarkupId(true);
        add(new TextField("foobar"));
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        tag.setName("foo");
    }

    @Override
    public Markup getAssociatedMarkup() {
        return Markup.of("<wicket:panel><input wicket:id='foobar'/></wicket:panel>");
    }
}
