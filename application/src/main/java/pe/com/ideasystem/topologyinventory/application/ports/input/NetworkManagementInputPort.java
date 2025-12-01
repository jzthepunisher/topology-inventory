package pe.com.ideasystem.topologyinventory.application.ports.input;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pe.com.ideasystem.topologyinventory.application.ports.output.RouterManagementOutputPort;
import pe.com.ideasystem.topologyinventory.application.usecases.NetworkManagementUseCase;
import pe.com.ideasystem.topologyinventory.domain.entity.EdgeRouter;
import pe.com.ideasystem.topologyinventory.domain.entity.Switch;
import pe.com.ideasystem.topologyinventory.domain.service.NetworkService;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Network;

import java.util.function.Predicate;

@ApplicationScoped
@NoArgsConstructor
public class NetworkManagementInputPort implements NetworkManagementUseCase {

    @Inject
    RouterManagementOutputPort routerManagementOutputPort;

    @Override
    public Network createNetwork(IP networkAddress, String networkName, int networkCidr) {
        return Network.builder()
                .networkAddress(networkAddress)
                .networkName(networkName)
                .networkCidr(networkCidr)
                .build();
    }

    @Override
    public Switch addNetworkToSwitch(Network network, Switch networkSwitch) {
        Id routerId = networkSwitch.getRouterId();
        Id switchId = networkSwitch.getId();
        EdgeRouter edgeRouter = (EdgeRouter) routerManagementOutputPort
                .retrieveRouter(routerId);
        Switch switchToAddNetwork = edgeRouter
                .getSwitches()
                .get(switchId);
        switchToAddNetwork.addNetworkToSwitch(network);
        routerManagementOutputPort.persistRouter(edgeRouter);
        return switchToAddNetwork;
    }

    @Override
    public Switch removeNetworkFromSwitch(String networkName, Switch networkSwitch) {
        Id routerId = networkSwitch.getRouterId();
        Id switchId = networkSwitch.getId();
        EdgeRouter edgeRouter = (EdgeRouter) routerManagementOutputPort
                .retrieveRouter(routerId);
        Switch switchToRemoveNetwork = edgeRouter
                .getSwitches()
                .get(switchId);
        Predicate<Network> networkPredicate = Network.getNetworkNamePredicate(networkName);
        var network = NetworkService.
                findNetwork(switchToRemoveNetwork.getSwitchNetworks(), networkPredicate);
        switchToRemoveNetwork.removeNetworkFromSwitch(network);
        routerManagementOutputPort.persistRouter(edgeRouter);
        return switchToRemoveNetwork.removeNetworkFromSwitch(network)
                ? switchToRemoveNetwork
                : null;
    }
}
