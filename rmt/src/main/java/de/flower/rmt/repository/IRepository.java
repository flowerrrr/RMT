package de.flower.rmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author flowerrrr
 */
@NoRepositoryBean
public interface IRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * Reattaches object to hibernate sesssion. Used for working with detached objects.
     * Unpersisted changes are lost.
     *
     * @param entity
     */
    @Deprecated
    void reattach(T entity);

    /**
     * Marks entity as deleted by setting objectstatus to DELETED.
     * @param entity
     */
    void softDelete(T entity);
}
