package de.flower.common.ui.form;

import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;

/**
 * @author oblume
 */
public class TestTextFieldContainer extends WebMarkupContainer {


    public TestTextFieldContainer(String id) {
        super(id);
        setOutputMarkupId(true);
        TextField field = new TextField(id);
        add(field);
    }

    @Override
    public IMarkupFragment getMarkup() {
        return Markup.of("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><wicket:panel><input wicket:id=\"" + getId() + "\" />\n" +
                "</wicket:panel>");
    }

}
