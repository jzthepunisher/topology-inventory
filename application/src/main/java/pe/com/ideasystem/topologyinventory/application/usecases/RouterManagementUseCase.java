package pe.com.ideasystem.topologyinventory.application.usecases;

import pe.com.ideasystem.topologyinventory.application.ports.output.RouterManagementOutputPort;
import pe.com.ideasystem.topologyinventory.domain.entity.CoreRouter;
import pe.com.ideasystem.topologyinventory.domain.entity.Router;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Location;
import pe.com.ideasystem.topologyinventory.domain.vo.Model;
import pe.com.ideasystem.topologyinventory.domain.vo.RouterType;
import pe.com.ideasystem.topologyinventory.domain.vo.Vendor;

public interface RouterManagementUseCase {

    Router createRouter(Id id,
                        Vendor vendor,
                        Model model,
                        IP ip,
                        Location location,
                        RouterType routerType);

    boolean removeRouter(Id id);

    CoreRouter addRouterToCoreRouter(Router router,
                                     CoreRouter coreRouter);

    Router removeRouterFromCoreRouter(Router router,
                                      CoreRouter coreRouter);

    Router retrieveRouter(Id id);

    Router persistRouter(Router router);
}
