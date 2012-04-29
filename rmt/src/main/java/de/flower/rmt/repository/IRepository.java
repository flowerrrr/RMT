package de.flower.rmt.repository;

import com.mysema.query.types.EntityPath;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author flowerrrr
 */
@NoRepositoryBean
public interface IRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>, QueryDslPredicateExecutor<T> {

    /**
     * Reattaches object to hibernate sesssion. Used for working with detached objects.
     * Unpersisted changes are lost.
     *
     * @param entity
     */
    @Deprecated
    void reattach(T entity);

    void detach(T entity);

    /**
     * Marks entity as deleted by setting objectstatus to DELETED.
     *
     * @param entity
     */
    void softDelete(T entity);

    T findOne(Predicate predicate, EntityPath<?>... attributes);

    List<T> findAll(Predicate predicate);

    List<T> findAll(Predicate predicate, EntityPath<?>... fetchAttributes);

    Page<T> findAll(Predicate predicate, Pageable pageable, EntityPath<?>... fetchAttributes);

    List<T> findAll(Predicate predicate, OrderSpecifier<?> order, EntityPath<?>... attributes);

}
