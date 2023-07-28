package de.flower.rmt.ui.page.uniforms.manager;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.service.UniformManager;
import de.flower.rmt.ui.model.UniformModel;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class UniformsSecondaryPanel extends BasePanel {

    @SpringBean
    private UniformManager uniformManager;

    public UniformsSecondaryPanel(final IModel<Team> model) {
        add(new Link("newButton") {

            @Override
            public void onClick() {
                setResponsePage(new UniformEditPage(new UniformModel(uniformManager.newInstance(model.getObject())), model));
            }
        });
    }


}
