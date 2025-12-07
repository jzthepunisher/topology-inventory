package pe.com.ideasystem.topologyinventory.domain.entity;

import lombok.Getter;
import lombok.Setter;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Location;
import pe.com.ideasystem.topologyinventory.domain.vo.Model;
import pe.com.ideasystem.topologyinventory.domain.vo.RouterType;
import pe.com.ideasystem.topologyinventory.domain.vo.Vendor;

import java.util.function.Predicate;

@Getter
public abstract sealed class Router extends Equipment permits CoreRouter, EdgeRouter {

    protected final RouterType routerType;

    @Setter
    public Id parentRouterId;

    public Router(Id id, Vendor vendor, Model model, IP ip, Location location, RouterType routerType) {
        super(id, vendor, model, ip, location);
        this.routerType = routerType;
    }

    public static Predicate<Router> getRouterTypePredicate(RouterType routerType) {
        return r -> r.getRouterType().equals(routerType);
    }

    public static Predicate<Equipment> getModelPredicate(Model model) {
        return r -> r.getModel().equals(model);
    }

    public static Predicate<Equipment> getCountryPredicate(Location location) {
        return p -> p.location.country().equals(location.country());
    }

    public abstract void changeLocation(Location location);
}
