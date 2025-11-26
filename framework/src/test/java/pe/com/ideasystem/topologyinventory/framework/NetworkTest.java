package pe.com.ideasystem.topologyinventory.framework;

import org.junit.jupiter.api.Test;
import pe.com.ideasystem.topologyinventory.domain.entity.Switch;
import pe.com.ideasystem.topologyinventory.domain.service.NetworkService;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Network;
import pe.com.ideasystem.topologyinventory.framework.adapters.input.generic.NetworkManagementGenericAdapter;
import pe.com.ideasystem.topologyinventory.framework.adapters.input.generic.SwitchManagementGenericAdapter;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NetworkTest extends FrameworkTestData {

    NetworkManagementGenericAdapter networkManagementGenericAdapter;
    SwitchManagementGenericAdapter switchManagementGenericAdapter;

    public NetworkTest(){
        this.networkManagementGenericAdapter = new NetworkManagementGenericAdapter(null, null);
        this.switchManagementGenericAdapter = new SwitchManagementGenericAdapter(null, null);
        loadData();
    }
    @Test
    @Order(1)
    public void addNetworkToSwitch(){
        Id switchId = Id.withId("922dbcd5-d071-41bd-920b-00f83eb4bb46");
        Switch networkSwitch = networkManagementGenericAdapter.addNetworkToSwitch(network, switchId);
        Predicate<Network> predicate = Network.getNetworkNamePredicate("TestNetwork");
        Network actualNetwork = NetworkService.findNetwork(networkSwitch.getSwitchNetworks(), predicate);
        assertEquals(network, actualNetwork);
    }
    @Test
    @Order(2)
    public void removeNetworkFromSwitch(){
        Id switchId = Id.withId("922dbcd5-d071-41bd-920b-00f83eb4bb46");
        var networkName = "HR";
        Predicate<Network> predicate = Network.getNetworkNamePredicate(networkName);
        Switch networkSwitch = switchManagementGenericAdapter.retrieveSwitch(switchId);
        Network existentNetwork = NetworkService.findNetwork(networkSwitch.getSwitchNetworks(), predicate);
        assertNotNull(existentNetwork);
        networkSwitch = networkManagementGenericAdapter.removeNetworkFromSwitch(networkName, switchId);
        assertNull(networkSwitch);
    }
}
