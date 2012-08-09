package de.flower.rmt.service;

import com.mysema.query.types.EntityPath;
import de.flower.rmt.model.db.entity.BArticle;
import de.flower.rmt.model.db.entity.BComment;
import de.flower.rmt.model.db.entity.User;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IBlogManager {

    BArticle newArticle(User author);

    BComment newComment(BArticle article, User author);

    BArticle loadArticleById(Long id);

    List<BArticle> findAllArticles(int pageNum, int itemsPerPage, EntityPath<?>... attributes);

    Long getNumArticles();

    Long getNumComments(BArticle article);

    List<BComment> findAllComments(BArticle entity, EntityPath<?>... attributes);

    List<BComment> findLastNComments(int num, EntityPath<?>... attributes);

    void save(BArticle article);

    void save(BComment comment);

    void remove(BComment comment);

    boolean hasUnreadArticleOrComment(User user);

    void markAllRead(User user);
}
