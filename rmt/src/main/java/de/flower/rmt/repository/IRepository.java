package de.flower.rmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author oblume
 */
@NoRepositoryBean
public interface IRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    /**
     * Reloads object from database. Used for working with detached objects.
     * Unpersisted changes are not saved.
     *
     * @param entity
     * @return
     */
    T reload(T entity);
}
