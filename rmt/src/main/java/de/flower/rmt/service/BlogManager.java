package de.flower.rmt.service;

import com.mysema.query.types.EntityPath;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.*;
import de.flower.rmt.repository.IBArticleRepo;
import de.flower.rmt.repository.IBCommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class BlogManager extends AbstractService implements IBlogManager {

    @Autowired
    private IBArticleRepo articleRepo;

    @Autowired
    private IBCommentRepo commentRepo;

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
        articleRepo.save(article);
    }

    @Override
    public void save(final BComment comment) {
        validate(comment);
        commentRepo.save(comment);
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
}
