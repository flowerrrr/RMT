package de.flower.rmt.repository.impl;

import com.google.common.collect.Lists;
import com.mysema.query.support.Expressions;
import com.mysema.query.types.Ops;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Path;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import de.flower.common.model.db.entity.AbstractBaseEntity;
import de.flower.common.model.db.entity.AbstractBaseEntity_;
import de.flower.common.model.db.entity.QAbstractBaseEntity;
import de.flower.common.model.db.type.ObjectStatus;
import de.flower.common.repository.ExtendedQueryDslJpaRepository;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.AbstractClubRelatedEntity;
import de.flower.rmt.model.db.entity.AbstractClubRelatedEntity_;
import de.flower.rmt.model.db.entity.Club;
import de.flower.rmt.repository.IRepository;
import de.flower.rmt.repository.Specs;
import de.flower.rmt.security.SecurityService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;


public class BaseRepository<T extends AbstractBaseEntity, ID extends Serializable> extends ExtendedQueryDslJpaRepository<T, ID> implements IRepository<T, ID> {

    private EntityManager em;

    private JpaEntityInformation<T, ID> entityInformation;

    private SecurityService securityService;

    public BaseRepository(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager, final SecurityService securityService) {
        super(entityInformation, entityManager);
        this.em = entityManager;
        this.entityInformation = entityInformation;
        this.securityService = securityService;
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
    public T findOne(final Predicate predicate, final Path<?>... attributes) {
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
    public List<T> findAll(final Predicate predicate, final Path<?>... fetchAttributes) {
        return super.findAll(getDefaultPredicate().and(predicate), fetchAttributes);
    }

    @Override
    public Page<T> findAll(final Predicate predicate, final Pageable pageable, final Path<?>... fetchAttributes) {
        return super.findAll(getDefaultPredicate().and(predicate), pageable, fetchAttributes);
    }

    @Override
    public List<T> findAll(final Predicate predicate, final OrderSpecifier<?> order, final Path<?>... fetchAttributes) {
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

    @Override
    public long count() {
        return super.count(getDefaultPredicate());
    }

    @Override
    public long count(final Predicate predicate) {
        return super.count(getDefaultPredicate().and(predicate));
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
        String variable = StringUtils.uncapitalize(entityInformation.getEntityName());
        Path<? extends AbstractBaseEntity> root = Expressions.path(entityInformation.getJavaType(), variable);
        return new QAbstractBaseEntity(root).objectStatus.ne(ObjectStatus.DELETED);
    }

    @Deprecated // use querydsl predicates if possible
    private Specification<T> getNotDeleted() {
        Specification notDeleted = Specifications.not(Specs.eq(AbstractBaseEntity_.objectStatus, ObjectStatus.DELETED));
        return notDeleted;
    }

    private BooleanExpression hasClub() {
        String variable = StringUtils.uncapitalize(entityInformation.getEntityName());
        Path<?> root = Expressions.path(entityInformation.getJavaType(), variable);
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
