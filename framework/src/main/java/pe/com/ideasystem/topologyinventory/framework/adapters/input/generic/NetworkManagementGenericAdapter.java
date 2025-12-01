package pe.com.ideasystem.topologyinventory.framework.adapters.input.generic;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pe.com.ideasystem.topologyinventory.application.usecases.NetworkManagementUseCase;
import pe.com.ideasystem.topologyinventory.application.usecases.SwitchManagementUseCase;
import pe.com.ideasystem.topologyinventory.domain.entity.Switch;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Network;

@ApplicationScoped
public class NetworkManagementGenericAdapter {

    @Inject
    private SwitchManagementUseCase switchManagementUseCase;
    @Inject
    private NetworkManagementUseCase networkManagementUseCase;

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
