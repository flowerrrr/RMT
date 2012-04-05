package de.flower.rmt.ui.manager.page.event.notification;

import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.ResourceModel;

import java.util.Arrays;

/**
 * @author flowerrrr
 */
public class SelectTemplatePanel extends BasePanel {

    protected enum Template {
        EVENT_INVITATION;
    }

    private String resourceKeyPrefix = "notification.template.";


    public SelectTemplatePanel() {
        super();

        ListView list = new ListView<Template>("list", Arrays.asList(Template.values())) {

            @Override
            protected void populateItem(final ListItem<Template> item) {
                item.add(new AjaxLink<Template>("link", item.getModel()){
                    {
                        add(new Label("label", new ResourceModel(resourceKeyPrefix + item.getModelObject().name().toLowerCase())));
                    }
                    @Override
                    public void onClick(final AjaxRequestTarget target) {
                        onUpdate(target, getModelObject());
                    }
                });
            }
        };
        add(list);
    }

    protected void onUpdate(AjaxRequestTarget target, Template template) {

    }
}
