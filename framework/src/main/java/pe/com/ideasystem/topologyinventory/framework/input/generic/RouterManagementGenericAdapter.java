package pe.com.ideasystem.topologyinventory.framework.input.generic;

import pe.com.ideasystem.topologyinventory.application.ports.input.RouterManagementInputPort;
import pe.com.ideasystem.topologyinventory.application.usecases.RouterManagementUseCase;
import pe.com.ideasystem.topologyinventory.domain.entity.CoreRouter;
import pe.com.ideasystem.topologyinventory.domain.entity.Router;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Location;
import pe.com.ideasystem.topologyinventory.domain.vo.Model;
import pe.com.ideasystem.topologyinventory.domain.vo.RouterType;
import pe.com.ideasystem.topologyinventory.domain.vo.Vendor;
import pe.com.ideasystem.topologyinventory.framework.output.h2.RouterManagementH2Adapter;


public class RouterManagementGenericAdapter {

    private RouterManagementUseCase routerManagementUseCase;

    public RouterManagementGenericAdapter() {
        setPorts();
    }

    private void setPorts(){
        this.routerManagementUseCase = new RouterManagementInputPort(
                RouterManagementH2Adapter.getInstance()
        );
    }

    /**
     * GET /router/retrieve/{id}
     * */
    public Router retrieveRouter(Id id){
        return routerManagementUseCase.retrieveRouter(id);
    }

    /**
     * GET /router/remove/{id}
     * */
    public Router removeRouter(Id id){
        return routerManagementUseCase.removeRouter(id);
    }

    /**
     * POST /router/create
     * */
    public Router createRouter(Vendor vendor,
                               Model model,
                               IP ip,
                               Location location,
                               RouterType routerType){
        var router = routerManagementUseCase.createRouter(
                null,
                vendor,
                model,
                ip,
                location,
                routerType
        );
        return routerManagementUseCase.persistRouter(router);
    }

    /**
     * POST /router/add
     * */
    public Router addRouterToCoreRouter(Id routerId, Id coreRouterId){
        Router router = routerManagementUseCase.retrieveRouter(routerId);
        CoreRouter coreRouter = (CoreRouter) routerManagementUseCase.retrieveRouter(coreRouterId);
        return routerManagementUseCase.addRouterToCoreRouter(router, coreRouter);
    }

    /**
     * POST /router/remove
     * */
    public Router removeRouterFromCoreRouter(Id routerId, Id coreRouterId){
        Router router = routerManagementUseCase.retrieveRouter(routerId);
        CoreRouter coreRouter = (CoreRouter) routerManagementUseCase.retrieveRouter(coreRouterId);
        return routerManagementUseCase.removeRouterFromCoreRouter(router, coreRouter);
    }
}
