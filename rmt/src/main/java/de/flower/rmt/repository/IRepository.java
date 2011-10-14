package de.flower.rmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author flowerrrr
 */
@NoRepositoryBean
public interface IRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    /**
     * Reattaches object to hibernate sesssion. Used for working with detached objects.
     * Unpersisted changes are lost.
     *
     * @param entity
     */
    void reattach(T entity);
}
