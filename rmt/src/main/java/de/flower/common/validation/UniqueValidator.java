package de.flower.common.validation;


import de.flower.common.jpa.IColumnResolver;
import de.flower.common.logging.Slf4jUtil;
import de.flower.common.model.BaseEntity;
import de.flower.common.util.ReflectionUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author oblume
 */
public class UniqueValidator implements ConstraintValidator<Unique, BaseEntity> {

    private final static Logger log = Slf4jUtil.getLogger();

    private UniqueConstraint[] constraints;

    private EntityManager em;

    /**
     * Need to create own EntityManager. Otherwise when validating during a save/update the database query
     * will force a flush of the unsaved entity -> null id error from hibernate.
     * So we need a separate session. However this opens the risk for phantom reads.
     *
     * @see http://community.jboss.org/wiki/AccessingTheHibernateSessionWithinAConstraintValidator
     */
    @Autowired
    private EntityManagerFactory emFactory;

    @Autowired
    private IColumnResolver columnResolver;

    private Class<?> entityClass;

    private boolean initialized = false;

    public void initialize(Unique constraintAnnotation) {
        this.constraints = constraintAnnotation.constraints();
        if (constraints.length != 0) {
            initialized = true;
        }
    }

    /**
     * Initializatin is delayed until the first validation should be made.
     * Reason: Only then we have access to the entityclass.
     *
     * @param entityClass
     */
    public void lazyInitialize(Class<?> entityClass) {
        if (this.entityClass == null) {
            this.entityClass = entityClass;
        }
        if (!initialized) {

            // lookup uniquness constraints in @Table annotation of class
            Table table = entityClass.getAnnotation(Table.class);
            if (table == null) {
                log.warn("Entity with empty @Unique constraint should be annotated with @Table containing uniquness constraints.");
                return;
            }
            constraints = table.uniqueConstraints();
            if (constraints.length == 0) {
                log.warn("No uniqueness constraints defined in @Table.");
            }
        }
        initialized = true;
    }

    @Override
    public boolean isValid(BaseEntity entity, ConstraintValidatorContext context) {
        lazyInitialize(entity.getClass());
        if (constraints.length == 0) {
            return true; // warnings of misconfiguration have been logged. just keep quiet and let bean validate.
        }
        try {
            boolean valid = true;
            em = emFactory.createEntityManager();
            assert (em != null);
            for (UniqueConstraint constraint : constraints) {
                valid = valid && isValid(entity, constraint);
            }
            return valid;
        } finally {
            em.close();
        }
    }

    private boolean isValid(BaseEntity entity, UniqueConstraint constraint) {
        return rowCount(entity, columnResolver.map2FieldNames(entityClass, constraint.columnNames())).intValue() == 0;
    }

    private Long rowCount(BaseEntity entity, String[] fields) {
        Class<? extends BaseEntity> entityClass = entity.getClass();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<? extends BaseEntity> root = query.from(entityClass);
        query.select(cb.count(root));
        Predicate condition = null;
        for (String field : fields) {
            Predicate tmp = cb.equal(root.<Object>get(field), ReflectionUtil.getProperty(entity, field));
            condition = (condition == null) ? tmp : cb.and(condition, tmp);
        }
        // update on existing element?
        if (!entity.isTransient()) {
            condition = cb.and(condition, cb.notEqual(root.<Long>get("id"), "" + entity.getId()));
        }
        query.where(condition);
        return em.createQuery(query).getSingleResult();
    }

}