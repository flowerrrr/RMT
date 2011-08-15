package de.flower.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author oblume
 */
public class RepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable> extends JpaRepositoryFactoryBean<T, S, ID> {

    @Override
    protected RepositoryFactory createRepositoryFactory(
            EntityManager entityManager) {
        return new RepositoryFactory(entityManager);
    }

    private static class RepositoryFactory extends JpaRepositoryFactory {

        public RepositoryFactory(EntityManager entityManager) {
            super(entityManager);
        }

        @Override
        protected <T, ID extends Serializable> Object getTargetRepository(
                RepositoryMetadata metadata, EntityManager entityManager) {

            JpaEntityInformation<?, Serializable> entityInformation =
                    getEntityInformation(metadata.getDomainClass());

            return new Repository(entityInformation, entityManager);
        }

        @Override
        public Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return Repository.class;
        }
    }
}
