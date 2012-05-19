package de.flower.common.validation.unique.impl;

import de.flower.common.model.db.entity.IEntity;
import de.flower.common.util.Check;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Does the actual database operations used for validation.
 */
@Service
public final class RowCountChecker implements IRowCountChecker {

    /**
     * Need to create own EntityManager. Otherwise when validating during a save/update the database query will force a
     * flush of the unsaved entity -> null id error from hibernate. So we need a separate session. However this opens
     * the risk for phantom reads.
     *
     * @see http://community.jboss.org/wiki/AccessingTheHibernateSessionWithinAConstraintValidator
     */
    private final EntityManagerFactory emFactory;

    @Autowired
    public RowCountChecker(final EntityManagerFactory emFactory) {
        this.emFactory = Check.notNull(emFactory);
    }

    @Override
    public Long rowCount(final IEntity entity, final List<String> attributeNames) {
        EntityManager em = null;
        try {
            em = emFactory.createEntityManager();
            return rowCount(entity, attributeNames, em);
        } finally {
            em.close();
        }
    }

    private Long rowCount(final IEntity entity, final List<String> attributeNames, final EntityManager em) {
        final Class<? extends IEntity> entityClass = entity.getClass();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> query = cb.createQuery(Long.class);
        final Root<? extends IEntity> root = query.from(entityClass);
        query.select(cb.count(root));
        Predicate condition = null;
        for (final String attributeName : attributeNames) {
            Predicate tmp;
            try {
                tmp = cb.equal(root.<Object>get(attributeName), PropertyUtils.getProperty(entity, attributeName));
            // CHECKSTYLE IGNORE 1 LINES
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
            condition = (condition == null) ? tmp : cb.and(condition, tmp);
        }
        // update on existing element? exclude the element itself
        if (!entity.isNew()) {
            condition = cb.and(condition, cb.notEqual(root.<Long>get("id"), "" + entity.getId()));
        }
        query.where(condition);
        return em.createQuery(query).getSingleResult();
    }
}