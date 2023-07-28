package de.flower.rmt.ui.page.blog;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.behavior.CkEditorBehavior;
import de.flower.rmt.model.db.entity.BArticle;
import de.flower.rmt.service.BlogManager;
import de.flower.rmt.ui.app.Resource;
import de.flower.rmt.ui.markup.html.form.CancelableEntityForm;
import de.flower.rmt.ui.markup.html.form.EntityForm;
import de.flower.rmt.ui.markup.html.form.field.TextAreaPanel;
import de.flower.rmt.ui.markup.html.form.field.TextFieldPanel;
import de.flower.rmt.ui.panel.RMTBasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;


public class ArticleEditPanel extends RMTBasePanel<BArticle> {

    @SpringBean
    private BlogManager blogManager;

    public ArticleEditPanel(final IModel<BArticle> model) {
        super(model);

        EntityForm<BArticle> form = new CancelableEntityForm<BArticle>("form", model) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<BArticle> form) {
                BArticle article = form.getModelObject();
                blogManager.save(article);
                AjaxEventSender.entityEvent(this, BArticle.class);
                setResponsePage(ArticlePage.class, ArticlePage.getPageParams(article.getId()));
            }
        };
        add(form);

        form.add(new TextFieldPanel("heading"));

        TextAreaPanel ckeditor = new TextAreaPanel("text");
        ckeditor.getFormComponent().add(new CkEditorBehavior(Resource.ckeditorJsUrl, form.getAjaxSubmitLink(), 600));
        form.add(ckeditor);

//        EventDropDownChoicePanel event = new EventDropDownChoicePanel("event");
//        form.add(event);
    }
}
