package de.flower.rmt.repository;

import org.hibernate.ejb.criteria.CriteriaBuilderImpl;
import org.hibernate.ejb.criteria.predicate.BooleanStaticAssertionPredicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

/**
 * @author oblume
 */
public class Specs {

    public static <X, T> Specification eq(final SingularAttribute<X, T> attribute, final T object) {
        return  new Specification<X>() {
            @Override
            public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get(attribute), object);
            }
        };
    }

    public static <X, T> Specification in(final ListAttribute<X, T> attribute, final T object) {
        return  new Specification<X>() {
            @Override
            public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.isMember(object, root.get(attribute));
            }
        };
    }

    /**
     * Not the ideal solution. Want to separate building the predicate and the join.
     */
    public static <X, Y, T> Specification joinEq(final ListAttribute<X, Y> joinAttribute, final SingularAttribute<Y, T> attribute, final T object) {
        return  new Specification<X>() {
            @Override
            public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                final ListJoin<X,Y> join = root.join(joinAttribute);
                return cb.equal(join.get(attribute), object);
            }
        };
    }

    public static <X> Specification fetch(final Attribute<X, ?> ... attributes) {
        return  new Specification<X>() {
            @Override
            public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                for (Attribute attribute : attributes) {
                    if (attribute.isCollection()) {
                        root.fetch((PluralAttribute<? super X, ?, Object>) attribute);
                    } else {
                        root.fetch((SingularAttribute<? super X, Object>) attribute);
                    }
                }
                // force distinct results. fetching associations might lead to duplicate root entities in the result set.
                query.distinct(true);
                //  return always-true-predicate to satisfy caller. null is not allowed.
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
