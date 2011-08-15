package de.flower.common.repository;

import de.flower.common.model.AbstractBaseEntity;
import org.apache.commons.lang3.Validate;
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
        Validate.notNull(entity);
        T reloaded = findOne((ID) entity.getId());
        Validate.notNull(reloaded);
        return reloaded;
    }
}
