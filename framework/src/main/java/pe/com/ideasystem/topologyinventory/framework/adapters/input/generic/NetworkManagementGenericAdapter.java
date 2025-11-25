package pe.com.ideasystem.topologyinventory.framework.adapters.input.generic;

import pe.com.ideasystem.topologyinventory.application.ports.input.NetworkManagementInputPort;
import pe.com.ideasystem.topologyinventory.application.ports.input.SwitchManagementInputPort;
import pe.com.ideasystem.topologyinventory.application.usecases.NetworkManagementUseCase;
import pe.com.ideasystem.topologyinventory.application.usecases.SwitchManagementUseCase;
import pe.com.ideasystem.topologyinventory.domain.entity.Switch;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Network;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.h2.RouterManagementH2Adapter;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.h2.SwitchManagementH2Adapter;

public class NetworkManagementGenericAdapter {

    private SwitchManagementUseCase switchManagementUseCase;
    private NetworkManagementUseCase networkManagementUseCase;

    public NetworkManagementGenericAdapter(){
        setPorts();
    }

    private void setPorts(){
        this.switchManagementUseCase = new SwitchManagementInputPort(SwitchManagementH2Adapter.getInstance());
        this.networkManagementUseCase = new NetworkManagementInputPort(RouterManagementH2Adapter.getInstance());
    }

    /**
     * POST /network/add
     * */
    public Switch addNetworkToSwitch(Network network, Id switchId) {
        Switch networkSwitch = switchManagementUseCase.retrieveSwitch(switchId);
        return networkManagementUseCase.addNetworkToSwitch(network, networkSwitch);
    }

    /**
     * POST /network/remove
     * */
    public Switch removeNetworkFromSwitch(String networkName, Id switchId) {
        Switch networkSwitch = switchManagementUseCase.retrieveSwitch(switchId);
        return networkManagementUseCase.removeNetworkFromSwitch(networkName, networkSwitch);
    }
}
