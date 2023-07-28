package de.flower.rmt.repository.factory;

import de.flower.rmt.repository.impl.BaseRepository;
import de.flower.rmt.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.io.Serializable;


public class RepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable> extends JpaRepositoryFactoryBean<T, S, ID> {

    @Autowired
    private SecurityService securityService;

    @Override
    protected RepositoryFactory createRepositoryFactory(
            EntityManager entityManager) {
        return new RepositoryFactory(entityManager, securityService);
    }

    private static class RepositoryFactory extends JpaRepositoryFactory {

        private SecurityService securityService;

        public RepositoryFactory(EntityManager entityManager, final SecurityService securityService) {
            super(entityManager);
            this.securityService = securityService;
        }

        @Override
        protected <T, ID extends Serializable> JpaRepository<?, ?>
        getTargetRepository(RepositoryMetadata metadata, EntityManager entityManager) {
            JpaEntityInformation<?, Serializable> entityInformation =
                    getEntityInformation(metadata.getDomainType());
            Assert.notNull(entityInformation);
            return new BaseRepository(entityInformation, entityManager, securityService);
        }

        @Override
        public Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseRepository.class;
        }
    }
}
