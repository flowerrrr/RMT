package de.flower.common.repository;

import de.flower.common.model.AbstractBaseEntity;
import de.flower.common.util.Check;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author oblume
 */
public class Repository<T extends AbstractBaseEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements IRepository<T, ID> {

    public Repository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
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
