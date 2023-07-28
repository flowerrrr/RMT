package de.flower.rmt.service;

import com.mysema.query.types.EntityPath;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.*;
import de.flower.rmt.repository.IBArticleRepo;
import de.flower.rmt.repository.IBCommentRepo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class BlogManager extends AbstractService {

    private final static String BLOG_LAST_READ = "blog.last.read";

    @Autowired
    private IBArticleRepo articleRepo;

    @Autowired
    private IBCommentRepo commentRepo;

    @Autowired
    private ActivityManager activityManager;

    @Autowired
    private ApplicationService applicationService;

    public BArticle newArticle(final User author) {
        Check.notNull(author);
        BArticle entity = new BArticle(author, getClub());
        return entity;
    }

    public BComment newComment(final BArticle article, final User author) {
        Check.notNull(article);
        Check.notNull(author);
        BComment entity = new BComment(article, author);
        return entity;
    }

    public void save(final BArticle article) {
        validate(article);
        boolean isNew = article.isNew();
        articleRepo.save(article);
        if (isNew) {
            activityManager.onBArticleCreate(article);
            markAllRead(article.getAuthor());
        }
    }

    public void save(final BComment comment) {
        validate(comment);
        boolean isNew = comment.isNew();
        commentRepo.save(comment);
        if (isNew) {
            activityManager.onBCommentCreate(comment);
            markAllRead(comment.getAuthor());
        }
    }

    public void remove(final BComment comment) {
        commentRepo.delete(comment.getId());
    }

    public BArticle loadArticleById(final Long id) {
        BArticle article = articleRepo.findOne(id);
        assertClub(article);
        return article;
    }

    public List<BArticle> findAllArticles(final int page, final int size, EntityPath<?>... attributes) {
        return articleRepo.findAll(null, new PageRequest(page, size, Sort.Direction.DESC, BArticle_.createDate.getName()), attributes).getContent();
    }

    public Long getNumArticles() {
        return articleRepo.count();
    }

    public Long getNumComments(final BArticle article) {
        BooleanExpression isArticle = QBComment.bComment.article.eq(article);
        return commentRepo.count(isArticle);
    }

    public List<BComment> findAllComments(final BArticle article, EntityPath<?>... attributes) {
        BooleanExpression isArticle = QBComment.bComment.article.eq(article);
        return commentRepo.findAll(isArticle, new OrderSpecifier(Order.ASC, QBComment.bComment.createDate), attributes);
    }

    public List<BComment> findLastNComments(final int num, EntityPath<?>... attributes) {
        BooleanExpression isClub = QBComment.bComment.article.club.eq(getClub());
        return commentRepo.findAll(isClub, new PageRequest(0, num, Sort.Direction.DESC, BComment_.createDate.getName()), attributes).getContent();
    }

    public boolean hasUnreadArticleOrComment(final User user) {
        String lastRead = applicationService.getUserProperty(user, BLOG_LAST_READ);
        // find last article or comment
        DateTime lastCreated = null;
        // next call triggers two sql-statements. could be improved.
        List<BArticle> list = articleRepo.findAll((Predicate) null, new PageRequest(0, 1, Sort.Direction.DESC, BArticle_.createDate.getName())).getContent();
        if (!list.isEmpty()) {
            lastCreated = new DateTime(list.get(0).getCreateDate());
        }
        BooleanExpression isClub = QBComment.bComment.article.club.eq(getClub());
        List<BComment> list2 = commentRepo.findAll(isClub, new PageRequest(0, 1, Sort.Direction.DESC, BComment_.createDate.getName())).getContent();
        if (!list2.isEmpty()) {
            DateTime tmp = new DateTime(list2.get(0).getCreateDate());
            lastCreated = max(lastCreated, tmp);
        }
        if (lastCreated == null) {
            return false;
        } else if (lastRead == null) {
            return true;
        } else {
            DateTime lastReadDt = new DateTime(new Long(lastRead));
            return lastCreated.isAfter(lastReadDt);
        }
    }

    private DateTime max(final DateTime a, final DateTime b) {
        if (a == null) {
            return b;
        } else if (b == null) {
            return a;
        } else {
            return (a.isAfter(b)) ? a : b;
        }
    }

    public void markAllRead(final User user) {
        applicationService.saveUserProperty(user, BLOG_LAST_READ, "" + new Date().getTime());
    }
}
