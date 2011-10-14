package de.flower.rmt.repository.impl;

import de.flower.common.model.AbstractBaseEntity;
import de.flower.common.util.Check;
import de.flower.rmt.repository.IRepository;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author flowerrrr
 */
public class BaseRepository<T extends AbstractBaseEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements IRepository<T, ID> {

    private EntityManager em;

    public BaseRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
    }

    public void reattach(T entity) {
        Check.notNull(entity);
        Session session = (Session)em.getDelegate();
        session.buildLockRequest(LockOptions.NONE).lock(entity);
    }
}
