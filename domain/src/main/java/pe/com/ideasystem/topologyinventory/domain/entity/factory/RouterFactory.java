package pe.com.ideasystem.topologyinventory.domain.entity.factory;

import pe.com.ideasystem.topologyinventory.domain.entity.CoreRouter;
import pe.com.ideasystem.topologyinventory.domain.entity.EdgeRouter;
import pe.com.ideasystem.topologyinventory.domain.entity.Router;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Location;
import pe.com.ideasystem.topologyinventory.domain.vo.Model;
import pe.com.ideasystem.topologyinventory.domain.vo.RouterType;
import pe.com.ideasystem.topologyinventory.domain.vo.Vendor;

public class RouterFactory {

    public static Router getRouter(
            Id id,
            Vendor vendor,
            Model model,
            IP ip,
            Location location,
            RouterType routerType) {

        switch (routerType) {
            case CORE -> {
                return CoreRouter.builder()
                        .id(id == null ? Id.withoutId() : id)
                        .vendor(vendor)
                        .model(model)
                        .ip(ip)
                        .location(location)
                        .routerType(routerType)
                        .build();
            }
            case EDGE -> {
                return EdgeRouter.builder()
                        .id(id == null ? Id.withoutId() : id)
                        .vendor(vendor)
                        .model(model)
                        .ip(ip)
                        .location(location)
                        .routerType(routerType)
                        .build();
            }
            default -> throw new UnsupportedOperationException("No valid router type informed");
        }


    }
}
