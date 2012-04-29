package de.flower.rmt.repository.impl;

import com.google.common.collect.Lists;
import com.mysema.query.support.Expressions;
import com.mysema.query.types.*;
import com.mysema.query.types.expr.BooleanExpression;
import de.flower.common.model.AbstractBaseEntity;
import de.flower.common.model.AbstractBaseEntity_;
import de.flower.common.model.ObjectStatus;
import de.flower.common.model.QAbstractBaseEntity;
import de.flower.common.spring.SpringApplicationContextBridge;
import de.flower.common.util.Check;
import de.flower.rmt.model.AbstractClubRelatedEntity;
import de.flower.rmt.model.AbstractClubRelatedEntity_;
import de.flower.rmt.model.Club;
import de.flower.rmt.repository.IRepository;
import de.flower.rmt.repository.Specs;
import de.flower.rmt.service.security.ISecurityService;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * @author flowerrrr
 */
public class BaseRepository<T extends AbstractBaseEntity, ID extends Serializable> extends QueryDslJpaRepository<T, ID> implements IRepository<T, ID> {

    private EntityManager em;

    private JpaEntityInformation<T, ID> entityInformation;

    private ISecurityService securityService;

    public BaseRepository(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
        this.entityInformation = entityInformation;
        this.securityService = (ISecurityService) SpringApplicationContextBridge.getInstance().getBean("securityService");
    }

    public void reattach(T entity) {
        Check.notNull(entity);
        Session session = (Session) em.getDelegate();
        session.buildLockRequest(LockOptions.NONE).lock(entity);
    }

    public void detach(T entity) {
        Check.notNull(entity);
        Session session = (Session) em.getDelegate();
        session.evict(entity);
    }

    public void softDelete(T entity) {
        entity.setObjectStatus(ObjectStatus.DELETED);
        save(entity);
    }

    //***************************************************************
    // Filtering of database reads.
    // Filters out all deleted entities.
    // Multi tenancy support.
    // Add club of currently logged in user to query where clause.
    // Filtering is still suboptimal, as only findAll calls gets filtered.
    //***************************************************************

    @Override
    @Deprecated // currently not used
    public T findOne(final Predicate predicate, final EntityPath<?>... attributes) {
        // TODO (flowerrrr - 29.04.12) use paging call with setMaxresult = 2
        List<T> list = super.findAll(predicate, attributes);
        if (list.isEmpty()) {
            return null;
        } else if (list.size() > 1) {
            throw new IllegalArgumentException("Query returned more then one record.");
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<T> findAll(final Predicate predicate) {
        return Lists.newArrayList(super.findAll(getDefaultPredicate().and(predicate)));
    }

    @Override
    public List<T> findAll() {
        return super.findAll(getDefaultPredicate());
    }

    @Override
    public List<T> findAll(final Specification<T> spec) {
        Specifications<T> defaultFilter = getDefaultFilter();
        return super.findAll(defaultFilter.and(spec));
    }

    @Override
    @Deprecated // use querydsl predicates if possible
    public Page<T> findAll(final Specification<T> spec, final Pageable pageable) {
        Specifications<T> defaultFilter = getDefaultFilter();
        return super.findAll(defaultFilter.and(spec), pageable);
    }

    @Override
    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        return super.findAll(getDefaultPredicate().and(predicate), pageable);
    }

    @Override
    public List<T> findAll(final Predicate predicate, final EntityPath<?>... fetchAttributes) {
        return super.findAll(getDefaultPredicate().and(predicate), fetchAttributes);
    }

    @Override
    public Page<T> findAll(final Predicate predicate, final Pageable pageable, final EntityPath<?>... fetchAttributes) {
        return super.findAll(getDefaultPredicate().and(predicate), pageable, fetchAttributes);
    }

    @Override
    public List<T> findAll(final Predicate predicate, final OrderSpecifier<?> order, final EntityPath<?>... fetchAttributes) {
        return super.findAll(getDefaultPredicate().and(predicate), order, fetchAttributes);
    }

    @Override
    public Page<T> findAll(final Pageable pageable) {
        return super.findAll(getDefaultPredicate(), pageable);
    }

    @Override
    public List<T> findAll(final Sort sort) {
        throw new UnsupportedOperationException("Feature not implemented!");
    }

    @Override
    @Deprecated // use querydsl predicates if possible
    public List<T> findAll(final Specification<T> spec, final Sort sort) {
        throw new UnsupportedOperationException("Feature not implemented!");
    }

    private BooleanExpression getDefaultPredicate() {
        BooleanExpression predicate = isNotDeleted();
        Class<T> domainClass = entityInformation.getJavaType();
        if (AbstractClubRelatedEntity.class.isAssignableFrom(domainClass)) {
            predicate = predicate.and(hasClub());
        }
        return predicate;
    }

    @Deprecated // use querydsl predicates if possible
    private Specifications<T> getDefaultFilter() {
        Specifications<T> specs = Specifications.where(getNotDeleted());
        Class<T> domainClass = entityInformation.getJavaType();
        if (AbstractClubRelatedEntity.class.isAssignableFrom(domainClass)) {
            specs = specs.and(getHasClub());
        }
        return specs;
    }

    private BooleanExpression isNotDeleted() {
        // next line does not work, will cause runtime error
        // QAbstractBaseEntity.abstractBaseEntity.objectStatus.ne(ObjectStatus.DELETED);
        Path<? extends AbstractBaseEntity> root = Expressions.path(entityInformation.getJavaType(), entityInformation.getEntityName().toLowerCase());
        return new QAbstractBaseEntity(root).objectStatus.ne(ObjectStatus.DELETED);
    }

    @Deprecated // use querydsl predicates if possible
    private Specification<T> getNotDeleted() {
        Specification notDeleted = Specifications.not(Specs.eq(AbstractBaseEntity_.objectStatus, ObjectStatus.DELETED));
        return notDeleted;
    }

    private BooleanExpression hasClub() {
        Path<?> root = Expressions.path(entityInformation.getJavaType(), entityInformation.getEntityName().toLowerCase());
        Path<Club> club = Expressions.path(Club.class, root, "club");
        return Expressions.predicate(Ops.EQ_OBJECT, club, Expressions.constant(getClub()));
    }

    @Deprecated // use querydsl predicates if possible
    private Specification<T> getHasClub() {
        Specification hasClub = Specs.eq(AbstractClubRelatedEntity_.club, getClub());
        return hasClub;
    }

    private Club getClub() {
        return securityService.getUser().getClub();
    }
}
