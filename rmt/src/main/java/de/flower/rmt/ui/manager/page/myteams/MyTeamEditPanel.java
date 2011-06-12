package de.flower.rmt.ui.manager.page.myteams;

import de.flower.common.ui.FormMode;
import de.flower.rmt.model.MyTeamBE;
import de.flower.rmt.service.IMyTeamManager;
import de.flower.rmt.ui.common.ajax.AjaxEvent;
import de.flower.rmt.ui.common.ajax.AjaxRespondListener;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author oblume
 */
public class MyTeamEditPanel extends BasePanel {

    private FormMode mode;

    private Form<MyTeamBE> form;

    @SpringBean
    private IMyTeamManager myTeamManager;

    public MyTeamEditPanel(String id) {
        super(id);

        form = new Form<MyTeamBE>("form");
        add(form);
        form.add(new TextField("name"));
        form.add(new TextField("url"));
        form.add(new AjaxSubmitLink("saveButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                // TODO validate data
                myTeamManager.save((MyTeamBE) form.getModelObject());
                target.registerRespondListener(new AjaxRespondListener(AjaxEvent.MYTEAM_CREATED));
                ModalWindow.closeCurrent(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

    }

    public void init(IModel<MyTeamBE> model) {
        if (model == null) {
            model = Model.of(new MyTeamBE());
        }
        form.setModel(new CompoundPropertyModel<MyTeamBE>(model));
    }
}
