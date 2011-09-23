package de.flower.rmt.repository.impl;

import de.flower.common.model.AbstractBaseEntity;
import de.flower.common.util.Check;
import de.flower.rmt.repository.IRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author oblume
 */
public class BaseRepository<T extends AbstractBaseEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements IRepository<T, ID> {

    public BaseRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public T reload(T entity) {
        Check.notNull(entity);
        T reloaded = findOne((ID) entity.getId());
        Check.notNull(reloaded);
        return reloaded;
    }
}
