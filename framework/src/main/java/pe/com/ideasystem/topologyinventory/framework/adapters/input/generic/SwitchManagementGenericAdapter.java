package pe.com.ideasystem.topologyinventory.framework.adapters.input.generic;

import pe.com.ideasystem.topologyinventory.application.ports.input.RouterManagementInputPort;
import pe.com.ideasystem.topologyinventory.application.ports.input.SwitchManagementInputPort;
import pe.com.ideasystem.topologyinventory.application.usecases.RouterManagementUseCase;
import pe.com.ideasystem.topologyinventory.application.usecases.SwitchManagementUseCase;
import pe.com.ideasystem.topologyinventory.domain.entity.EdgeRouter;
import pe.com.ideasystem.topologyinventory.domain.entity.Router;
import pe.com.ideasystem.topologyinventory.domain.entity.Switch;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Location;
import pe.com.ideasystem.topologyinventory.domain.vo.Model;
import pe.com.ideasystem.topologyinventory.domain.vo.RouterType;
import pe.com.ideasystem.topologyinventory.domain.vo.SwitchType;
import pe.com.ideasystem.topologyinventory.domain.vo.Vendor;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.h2.RouterManagementH2Adapter;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.h2.SwitchManagementH2Adapter;

public class SwitchManagementGenericAdapter {

    private SwitchManagementUseCase switchManagementUseCase;
    private RouterManagementUseCase routerManagementUseCase;

    public SwitchManagementGenericAdapter(){
        setPorts();
    }

    private void setPorts(){
        this.routerManagementUseCase = new RouterManagementInputPort(
                RouterManagementH2Adapter.getInstance()
        );
        this.switchManagementUseCase = new SwitchManagementInputPort(
                SwitchManagementH2Adapter.getInstance()
        );
    }

    /**
     * GET /switch/retrieve/{id}
     * */
    public Switch retrieveSwitch(Id switchId) {
        return switchManagementUseCase.retrieveSwitch(switchId);
    }

    /**
     * POST /switch/create
     * */
    public EdgeRouter createAndAddSwitchToEdgeRouter(
            Vendor vendor,
            Model model,
            IP ip,
            Location location,
            SwitchType switchType,
            Id routerId
    ) {
        Switch newSwitch = switchManagementUseCase.createSwitch(vendor, model, ip, location, switchType);
        Router edgeRouter = routerManagementUseCase.retrieveRouter(routerId);
        if(!edgeRouter.getRouterType().equals(RouterType.EDGE))
            throw new UnsupportedOperationException("Please inform the id of an edge router to add a switch");
        Router router = switchManagementUseCase.addSwitchToEdgeRouter(newSwitch, (EdgeRouter) edgeRouter);
        return (EdgeRouter) routerManagementUseCase.persistRouter(router);
    }

    /**
     * POST /switch/remove
     * */
    public EdgeRouter removeSwitchFromEdgeRouter(Id switchId, Id edgeRouterId) {
        EdgeRouter edgeRouter = (EdgeRouter) routerManagementUseCase
                .retrieveRouter(edgeRouterId);
        Switch networkSwitch = edgeRouter.getSwitches().get(switchId);
        Router router = switchManagementUseCase
                .removeSwitchFromEdgeRouter(networkSwitch, edgeRouter);
        return (EdgeRouter) routerManagementUseCase.persistRouter(router);
    }

}
