package de.flower.rmt.repository;

import de.flower.common.util.Check;
import org.hibernate.ejb.criteria.CriteriaBuilderImpl;
import org.hibernate.ejb.criteria.predicate.BooleanStaticAssertionPredicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

/**
 * @author flowerrrr
 */
public class Specs {

    public static <X, T> Specification eq(final SingularAttribute<X, T> attribute, final T object) {
        return new Specification<X>() {
            @Override
            public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get(attribute), object);
            }
        };
    }

    public static <X, T> Specification isMember(final T object, final ListAttribute<X, T> attribute) {
        return new Specification<X>() {
            @Override
            public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.isMember(object, root.get(attribute));
            }
        };
    }

    public static <X, T> Specification in(final SingularAttribute<X, T> attribute, final List<T> collection) {
        return new Specification<X>() {
            @Override
            public Predicate toPredicate(final Root<X> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
                return root.get(attribute).in(collection);
            }
        };
    }

    /**
     * Not the ideal solution. Want to separate building the predicate and the join.
     */
    public static <X, Y, T> Specification joinEq(final ListAttribute<X, Y> joinAttribute, final SingularAttribute<Y, T> attribute, final T object) {
        return new Specification<X>() {
            @Override
            public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                final ListJoin<X, Y> join = root.join(joinAttribute);
                return cb.equal(join.get(attribute), object);
            }
        };
    }

    public static <X> Specification fetch(final Attribute<X, ?>... attributes) {
        Check.notNull(attributes);
        return new Specification<X>() {
            @Override
            public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                for (Attribute attribute : attributes) {
                    if (attribute.isCollection()) {
                        root.fetch((PluralAttribute<? super X, ?, Object>) attribute, JoinType.LEFT);
                        // force distinct results. fetching associations might lead to duplicate root entities in the result set.
                        query.distinct(true);
                    } else {
                        root.fetch((SingularAttribute<? super X, Object>) attribute, JoinType.LEFT);
                    }
                }
                //  return always-true-predicate to satisfy caller. null is not allowed.
                return new BooleanStaticAssertionPredicate((CriteriaBuilderImpl) cb, true);
            }
        };
    }

    public static <X, T> Specification asc(final SingularAttribute<X, T> attribute) {
        return new Specification<X>() {
            @Override
            public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.orderBy(cb.asc(root.get(attribute)));
                //  return always-true-predicate to satisfy caller. null is not allowed.
                return new BooleanStaticAssertionPredicate((CriteriaBuilderImpl) cb, true);
            }
        };
    }

    public static <X, T> Specification desc(final SingularAttribute<X, T> attribute) {
        return new Specification<X>() {
            @Override
            public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.orderBy(cb.desc(root.get(attribute)));
                //  return always-true-predicate to satisfy caller. null is not allowed.
                return new BooleanStaticAssertionPredicate((CriteriaBuilderImpl) cb, true);
            }
        };
    }

    /**
     * @param joinAttribute
     * @param attribute
     * @param asc
     * @param <X>
     * @param <Y>
     * @param <T>
     * @return
     * @deprecated Should not be used in queries that fetch list-associations cause this call will force distinct=false
     *             on the query.
     */
    @Deprecated
    public static <X, Y, T> Specification orderByJoin(final SingularAttribute<X, Y> joinAttribute, final SingularAttribute<Y, T> attribute, final boolean asc) {
        return new Specification<X>() {

            @Override
            public Predicate toPredicate(final Root<X> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
                Join<X, Y> join;
                join = root.join(joinAttribute);
                Order order;
                if (asc) {
                    order = cb.asc(join.get(attribute));
                } else {
                    order = cb.desc(join.get(attribute));
                }
                query.orderBy(order);
                // must fetch entity of ordered column
                root.fetch(joinAttribute, JoinType.LEFT);
                // query must not be distinct as h2 otherwise complains
                if (query.isDistinct()) {
                    throw new IllegalStateException("Cannot use order by on joined entity when query is distinct");
                }
                query.distinct(false);

                return new BooleanStaticAssertionPredicate((CriteriaBuilderImpl) cb, true);
            }
        };
    }

    public static Specification and(Specification a, Specification b) {
        return Specifications.where(a).and(b);
    }

    public static Specification not(Specification a) {
        return Specifications.not(a);
    }
}
