package de.flower.rmt.ui.manager.page.myteams;

import de.flower.common.ui.FormMode;
import de.flower.common.ui.form.MyForm;
import de.flower.rmt.model.TeamBE;
import de.flower.rmt.service.IMyTeamManager;
import de.flower.rmt.ui.common.ajax.AjaxEvent;
import de.flower.rmt.ui.common.ajax.AjaxRespondListener;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.jsr303.PropertyValidation;

/**
 * @author oblume
 */
public class MyTeamEditPanel extends BasePanel {

    private FormMode mode;

    private Form<TeamBE> form;

    @SpringBean
    private IMyTeamManager myTeamManager;

    public MyTeamEditPanel(String id) {
        super(id);

        form = new MyForm<TeamBE>("form", new CompoundPropertyModel<TeamBE>(new TeamBE()));
        add(form);
        final FeedbackPanel feedback;
        form.add(feedback = new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(form)));
        feedback.setOutputMarkupId(true);

        TextField name;
        form.add(name = new TextField("name"));
        // add(new InputValidationBorder<TeamBE>("nameBorder", form, name));
        name.setRequired(false);
        name.add(new AjaxFormComponentUpdatingBehavior("onblur") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(feedback);
            }
            @Override
            protected void onError(AjaxRequestTarget target, RuntimeException e) {
                target.add(feedback);
            }

        });
        form.add(new FeedbackPanel("feedback2", new ComponentFeedbackMessageFilter(name)));


        form.add(new TextField("url"));
        form.add(new AjaxSubmitLink("saveButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                // TODO validate data
                myTeamManager.save((TeamBE) form.getModelObject());
                target.registerRespondListener(new AjaxRespondListener(AjaxEvent.MYTEAM_CREATED));
                ModalWindow.closeCurrent(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        form.add(new PropertyValidation(form));

    }

    public void init(IModel<TeamBE> model) {
        if (model == null) {
            model = Model.of(new TeamBE());
        }
        form.setModel(new CompoundPropertyModel<TeamBE>(model));
    }
}
