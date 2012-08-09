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
public class BlogManager extends AbstractService implements IBlogManager {

    private final static String BLOG_LAST_READ = "blog.last.read";

    @Autowired
    private IBArticleRepo articleRepo;

    @Autowired
    private IBCommentRepo commentRepo;

    @Autowired
    private IActivityManager activityManager;

    @Autowired
    private IApplicationService applicationService;

    @Override
    public BArticle newArticle(final User author) {
        Check.notNull(author);
        BArticle entity = new BArticle(author, getClub());
        return entity;
    }

    @Override
    public BComment newComment(final BArticle article, final User author) {
        Check.notNull(article);
        Check.notNull(author);
        BComment entity = new BComment(article, author);
        return entity;
    }

    @Override
    public void save(final BArticle article) {
        validate(article);
        boolean isNew = article.isNew();
        articleRepo.save(article);
        if (isNew) {
            activityManager.onBArticleCreate(article);
        }
    }

    @Override
    public void save(final BComment comment) {
        validate(comment);
        boolean isNew = comment.isNew();
        commentRepo.save(comment);
        if (isNew) {
            activityManager.onBCommentCreate(comment);
        }
    }

    @Override
    public void remove(final BComment comment) {
        commentRepo.delete(comment.getId());
    }

    @Override
    public BArticle loadArticleById(final Long id) {
        BArticle article = articleRepo.findOne(id);
        assertClub(article);
        return article;
    }

    @Override
    public List<BArticle> findAllArticles(final int page, final int size, EntityPath<?>... attributes) {
        return articleRepo.findAll(null, new PageRequest(page, size, Sort.Direction.DESC, BArticle_.createDate.getName()), attributes).getContent();
    }

    @Override
    public Long getNumArticles() {
        return articleRepo.count();
    }

    @Override
    public Long getNumComments(final BArticle article) {
        BooleanExpression isArticle = QBComment.bComment.article.eq(article);
        return commentRepo.count(isArticle);
    }

    @Override
    public List<BComment> findAllComments(final BArticle article, EntityPath<?>... attributes) {
        BooleanExpression isArticle = QBComment.bComment.article.eq(article);
        return commentRepo.findAll(isArticle, new OrderSpecifier(Order.ASC, QBComment.bComment.createDate), attributes);
    }

    @Override
    public List<BComment> findLastNComments(final int num, EntityPath<?>... attributes) {
        return commentRepo.findAll(null, new PageRequest(0, num, Sort.Direction.DESC, BComment_.createDate.getName()), attributes).getContent();
    }

    @Override
    public boolean hasUnreadArticleOrComment(final User user) {
        String lastRead = applicationService.getUserProperty(user, BLOG_LAST_READ);
        // find last article or comment
        DateTime lastCreated = null;
        // next call triggers two sql-statements. could be improved.
        List<BArticle> list = articleRepo.findAll((Predicate) null, new PageRequest(0, 1, Sort.Direction.DESC, BArticle_.createDate.getName())).getContent();
        if (!list.isEmpty()) {
            lastCreated = new DateTime(list.get(0).getCreateDate());
        }
        List<BComment> list2 = commentRepo.findAll((Predicate) null, new PageRequest(0, 1, Sort.Direction.DESC, BComment_.createDate.getName())).getContent();
        if (!list2.isEmpty()) {
            DateTime tmp = new DateTime(list2.get(0).getCreateDate());
            lastCreated = max(lastCreated, tmp);
        }
        if (lastCreated == null) {
            return false;
        } else if (lastRead == null) {
            return true;
        } else {
            return lastCreated.isAfter(new Long(lastRead));
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

    @Override
    public void markAllRead(final User user) {
        applicationService.saveUserProperty(user, BLOG_LAST_READ, "" + new Date().getTime());
    }
}
