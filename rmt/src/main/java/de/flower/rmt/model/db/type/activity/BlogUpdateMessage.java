package de.flower.rmt.model.db.type.activity;

import com.google.common.annotations.VisibleForTesting;
import de.flower.rmt.model.db.entity.BArticle;
import de.flower.rmt.model.db.entity.BComment;

import java.io.Serializable;


public class BlogUpdateMessage implements Serializable {

    /**
     * Must have a serialVersionUID as instances are stored in database.
     */
    private static final long serialVersionUID = 1L;

    private String heading;

    private Long articleId;

    /**
     * fullname of author.
     */
    private String authorUserName;

    boolean isCommment = false;

    @VisibleForTesting
    public BlogUpdateMessage() {

    }

    public BlogUpdateMessage(final BArticle article) {
        this.heading = article.getHeading();
        this.articleId = article.getId();
        this.authorUserName = article.getAuthor().getFullname();
    }

    public BlogUpdateMessage(final BArticle article, final BComment comment) {
        this.heading = article.getHeading();
        this.articleId = article.getId();
        this.authorUserName = comment.getAuthor().getFullname();
        this.isCommment = true;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(final String heading) {
        this.heading = heading;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(final Long articleId) {
        this.articleId = articleId;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public void setAuthorUserName(final String authorUserName) {
        this.authorUserName = authorUserName;
    }

    public boolean isCommment() {
        return isCommment;
    }

    public void setCommment(final boolean commment) {
        isCommment = commment;
    }

    @Override
    public String toString() {
        return "BlogUpdateMessage{" +
                "heading='" + heading + '\'' +
                ", articleId=" + articleId +
                ", authorUserName='" + authorUserName + '\'' +
                ", isCommment=" + isCommment +
                '}';
    }
}
