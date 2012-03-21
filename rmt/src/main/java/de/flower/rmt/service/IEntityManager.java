package de.flower.rmt.service;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IEntityManager<T> {

    void save(T entity);

    T loadById(Long id);

    List<T> findAll();

    void delete(Long id);

    T newInstance();
}
