package pe.com.ideasystem.topologyinventory.domain.specification;

import pe.com.ideasystem.topologyinventory.domain.exception.GenericSpecificationException;

public abstract sealed class AbstractSpecification<T> implements Specification<T> permits
        AndSpecification,
        SameCountrySpec,
        SameIpSpec,
        EmptyRouterSpec,
        EmptySwitchSpec,
        EmptyNetworkSpec,
        NetworkAvailabilitySpec,
        CIDRSpecification,
        NetworkAmountSpec
{

    public abstract boolean isSatisfiedBy(T t);

    public abstract void check(T t) throws GenericSpecificationException;

    public Specification and(Specification specification) {
        return new AndSpecification<T>(this, specification);
    }
}
