package de.flower.rmt.ui.page.blog;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.markup.html.form.TextAreaMaxLengthBehavior;
import de.flower.rmt.model.db.entity.BArticle;
import de.flower.rmt.model.db.entity.BComment;
import de.flower.rmt.service.IBlogManager;
import de.flower.rmt.ui.markup.html.form.EntityForm;
import de.flower.rmt.ui.markup.html.form.field.TextAreaPanel;
import de.flower.rmt.ui.panel.RMTBasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * form to create new comment.
 *
 * @author flowerrrr
 */
public class CommentFormPanel extends RMTBasePanel {

    @SpringBean
    private IBlogManager blogManager;

    public CommentFormPanel(final IModel<BArticle> model) {
        super(model);

        IModel<BComment> commentModel = new LoadableDetachableModel<BComment>() {
            @Override
            protected BComment load() {
                return blogManager.newComment(model.getObject(), getUser());
            }
        };
        EntityForm<BComment> form = new EntityForm<BComment>("form", commentModel) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<BComment> form) {
                BComment comment = form.getModelObject();
                blogManager.save(comment);
                AjaxEventSender.entityEvent(this, BComment.class);
            }
        };
        add(form);

        form.add(new TextAreaPanel("text") {
            {
                getFormComponent().add(new TextAreaMaxLengthBehavior(BComment.MAXLENGTH));
            }
        });
    }
}
