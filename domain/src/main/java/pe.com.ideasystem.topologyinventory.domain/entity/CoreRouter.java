package pe.com.ideasystem.topologyinventory.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pe.com.ideasystem.topologyinventory.domain.specification.EmptyRouterSpec;
import pe.com.ideasystem.topologyinventory.domain.specification.EmptySwitchSpec;
import pe.com.ideasystem.topologyinventory.domain.specification.SameCountrySpec;
import pe.com.ideasystem.topologyinventory.domain.specification.SameIpSpec;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Location;
import pe.com.ideasystem.topologyinventory.domain.vo.Model;
import pe.com.ideasystem.topologyinventory.domain.vo.RouterType;
import pe.com.ideasystem.topologyinventory.domain.vo.Vendor;

import java.util.Map;

@Getter
@ToString
public final class CoreRouter extends Router {

    private final Map<Id, Router> routers;

    @Builder
    public CoreRouter(Id id, Vendor vendor, Model model, IP ip, Location location, RouterType routerType, Map<Id, Router> routers) {
        super(id, vendor, model, ip, location, routerType);
        this.routers = routers;
    }

    public Router addRouter(Router anyRouter) {
        var sameCountryRouterSpec = new SameCountrySpec(this);
        var sameIpSpec = new SameIpSpec(this);

        sameCountryRouterSpec.check(anyRouter);
        sameIpSpec.check(anyRouter);

        return this.routers.put(anyRouter.id, anyRouter);
    }

    public Router removeRouter(Router anyRouter) {
        var emptyRouterSpec = new EmptyRouterSpec();
        var emptySwitchSpec = new EmptySwitchSpec();

        switch (anyRouter.routerType) {
            case CORE -> {
                var coreRouter = (CoreRouter)anyRouter;
                emptyRouterSpec.check(coreRouter);
            }
            case EDGE -> {
                var edgeRouter = (EdgeRouter)anyRouter;
                emptySwitchSpec.check(edgeRouter);
            }
        }

        return this.routers.remove(anyRouter.id);
    }
}
