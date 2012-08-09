package de.flower.rmt.model.db.entity;

import de.flower.common.model.db.entity.AbstractBaseEntity;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * blog comment.
 *
 * @author flowerrrr
 */
@Entity
public class BComment extends AbstractBaseEntity {

    public final static int MAXLENGTH = 1024;

    @Column(length = MAXLENGTH)
    @Size(max = MAXLENGTH)
    private String text;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Index(name = "ix_barticle")
    private BArticle article;

    /**
     * FetchType EAGER needed as long as there's no bCommentManager that allows eager loading of comment instances.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @Index(name = "ix_author")
    private User author;

    protected BComment() {
    }

    public BComment(final BArticle article, final User author) {
        this.article = checkNotNull(article);
        this.author = checkNotNull(author);
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(final User author) {
        this.author = author;
    }

    public BArticle getArticle() {
        return article;
    }

    public void setArticle(final BArticle article) {
        this.article = article;
    }
}
