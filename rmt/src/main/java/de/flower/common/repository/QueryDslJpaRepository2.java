/*
 * Copyright 2008-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flower.common.repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import de.flower.common.util.ReflectionUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * Adds support for prefetching associations.
 *
 * @author flower
 */
public class QueryDslJpaRepository2<T, ID extends Serializable> extends QueryDslJpaRepository<T, ID> {

    private EntityManager em;

    public QueryDslJpaRepository2(JpaEntityInformation<T, ID> entityMetadata, EntityManager entityManager) {
        super(entityMetadata, entityManager);
        this.em = entityManager;
    }

    public List<T> findAll(final Predicate predicate, final EntityPath<?>... fetchAttributes) {
        return createQuery(fetchAttributes).where(predicate).list(getPath());
    }

    public Page<T> findAll(final Predicate predicate, Pageable pageable, final EntityPath<?>... fetchAttributes) {
        JPQLQuery countQuery = createQuery(predicate);
        JPQLQuery query = applyPagination(createQuery(fetchAttributes).where(predicate), pageable);

        return new PageImpl<T>(query.list(getPath()), pageable, countQuery.count());
    }

    public List<T> findAll(final Predicate predicate, final OrderSpecifier<?> order, final EntityPath<?>... fetchAttributes) {
        return createQuery(fetchAttributes).where(predicate).orderBy(order).list(getPath());
    }

    protected JPQLQuery createQuery(EntityPath<?>... fetchAttributes) {
        JPAQuery query = new JPAQuery(em).from(getPath());
        for (EntityPath<?> path : fetchAttributes) {
            query.leftJoin(path).fetch();
        }
        query.distinct(); // fetching one-to-many associations would multiply the result set.
        return query;
    }

    private EntityPath<T> getPath() {
        return (EntityPath<T>) ReflectionUtil.getField(this, "path");
    }
}
