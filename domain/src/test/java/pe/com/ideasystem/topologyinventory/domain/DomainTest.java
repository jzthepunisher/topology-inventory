package pe.com.ideasystem.topologyinventory.domain;

import org.junit.jupiter.api.Test;
import pe.com.ideasystem.topologyinventory.domain.entity.CoreRouter;
import pe.com.ideasystem.topologyinventory.domain.entity.EdgeRouter;
import pe.com.ideasystem.topologyinventory.domain.entity.Router;
import pe.com.ideasystem.topologyinventory.domain.entity.Switch;
import pe.com.ideasystem.topologyinventory.domain.exception.GenericSpecificationException;
import pe.com.ideasystem.topologyinventory.domain.service.RouterService;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Location;
import pe.com.ideasystem.topologyinventory.domain.vo.Model;
import pe.com.ideasystem.topologyinventory.domain.vo.Network;
import pe.com.ideasystem.topologyinventory.domain.vo.RouterType;
import pe.com.ideasystem.topologyinventory.domain.vo.SwitchType;
import pe.com.ideasystem.topologyinventory.domain.vo.Vendor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DomainTest {

    @Test
    public void addNetworkToSwitch() {
        var location = createLocation("US");
        var newNetwork = createTestNetwork("30.0.0.1", 8);
        var networkSwitch = createSwitch("30.0.0.0", 8, location);
        assertTrue(networkSwitch.addNetworkToSwitch(newNetwork));
    }

    @Test
    public void addNetworkToSwitch_failBecauseSameNetworkAddress(){
        var location = createLocation("US");
        var newNetwork = createTestNetwork("30.0.0.0", 8);
        var networkSwitch = createSwitch("30.0.0.0", 8, location);
        assertThrows(GenericSpecificationException.class, () -> networkSwitch.addNetworkToSwitch(newNetwork));
    }

    @Test
    public void addSwitchToEdgeRouter(){
        var location = createLocation("US");
        var networkSwitch = createSwitch("30.0.0.0", 8, location);
        var edgeRouter = createEdgeRouter(location,"30.0.0.1");

        edgeRouter.addSwitch(networkSwitch);

        assertEquals(1, edgeRouter.getSwitches().size());
    }

    @Test
    public void addSwitchToEdgeRouter_failBecauseEquipmentOfDifferentCountries(){
        var locationUS = createLocation("US");
        var locationJP = createLocation("JP");
        var networkSwitch = createSwitch("30.0.0.0", 8, locationUS);
        var edgeRouter = createEdgeRouter(locationJP,"30.0.0.1");

        assertThrows(GenericSpecificationException.class, () -> edgeRouter.addSwitch(networkSwitch));
    }

    @Test
    public void addEdgeToCoreRouter() {
        var location = createLocation("US");
        var edgeRouter = createEdgeRouter(location,"30.0.0.1");
        var coreRouter = createCoreRouter(location, "40.0.0.1");

        coreRouter.addRouter(edgeRouter);

        assertEquals(1,coreRouter.getRouters().size());
    }

    @Test
    public void addEdgeToCoreRouter_failBecauseRoutersOfDifferentCountries(){
        var locationUS = createLocation("US");
        var locationJP = createLocation("JP");
        var edgeRouter = createEdgeRouter(locationUS,"30.0.0.1");
        var coreRouter = createCoreRouter(locationJP, "40.0.0.1");

        assertThrows(GenericSpecificationException.class, () -> coreRouter.addRouter(edgeRouter));
    }

    @Test
    public void addCoreToCoreRouter(){
        var location = createLocation("US");
        var coreRouter = createCoreRouter(location, "30.0.0.1");
        var newCoreRouter = createCoreRouter(location, "40.0.0.1");

        coreRouter.addRouter(newCoreRouter);

        assertEquals(1,coreRouter.getRouters().size());
    }

    @Test
    public void addCoreToCoreRouter_failBecauseRoutersOfSameIp(){
        var location = createLocation("US");
        var coreRouter = createCoreRouter(location, "30.0.0.1");
        var newCoreRouter = createCoreRouter(location, "30.0.0.1");

        assertThrows(GenericSpecificationException.class, () -> coreRouter.addRouter(newCoreRouter));
    }

    private Location createLocation(String country) {
        return new Location(
                "Test street",
                "Test City",
                "Test State",
                00000,
                country,
                10F,
                -10F);
    }

    @Test
    public void removeRouter(){
        var location = createLocation("US");
        var coreRouter = createCoreRouter(location, "30.0.0.1");
        var edgeRouter = createEdgeRouter(location, "40.0.0.1");
        var expectedId = edgeRouter.getId();

        coreRouter.addRouter(edgeRouter);
        var actualId = coreRouter.removeRouter(edgeRouter).getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    public void removeSwitch(){
        var location = createLocation("US");
        var network = createTestNetwork("30.0.0.0", 8);
        var networkSwitch = createSwitch("30.0.0.0", 8, location);
        var edgeRouter = createEdgeRouter(location, "40.0.0.1");

        edgeRouter.addSwitch(networkSwitch);
        networkSwitch.removeNetworkFromSwitch(network);
        var expectedId = Id.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490");
        var actualId= edgeRouter.removeSwitch(networkSwitch).getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    public void removeNetwork(){
        var location = createLocation("US");
        var network = createTestNetwork("30.0.0.0", 8);
        var networkSwitch = createSwitch("30.0.0.0", 8, location);

        assertEquals(1, networkSwitch.getSwitchNetworks().size());
        assertTrue(networkSwitch.removeNetworkFromSwitch(network));
        assertEquals(0, networkSwitch.getSwitchNetworks().size());
    }

    @Test
    public void filterRouterByType(){
        List<Router> routers = new ArrayList<>();
        var location = createLocation("US");
        var coreRouter = createCoreRouter(location, "30.0.0.1");
        var edgeRouter = createEdgeRouter(location, "40.0.0.1");

        routers.add(coreRouter);
        routers.add(edgeRouter);

        var coreRouters = RouterService.filterAndRetrieveRouter(routers,
                Router.getRouterTypePredicate(RouterType.CORE));
        var actualCoreType = coreRouters.get(0).getRouterType();
        assertEquals(RouterType.CORE, actualCoreType);

        var edgeRouters = RouterService.filterAndRetrieveRouter(routers,
                Router.getRouterTypePredicate(RouterType.EDGE));
        var actualEdgeType = edgeRouters.get(0).getRouterType();
        assertEquals(RouterType.EDGE, actualEdgeType);
    }

    private Network createTestNetwork(String address, int CIDR) {
        return Network.builder()
                .networkAddress(IP.fromAddress(address))
                .networkName("NewNetwork")
                .networkCidr(CIDR)
                .build();
    }

    private Switch createSwitch(String address, int cidr, Location location) {
        var newNetwork = createTestNetwork(address, cidr);
        var networks = createNetworks(newNetwork);
        var networkSwitch = createNetworkSwitch(location, networks);
        return networkSwitch;
    }

    private Switch createNetworkSwitch(Location location, List<Network> networks){
        return Switch.builder().
                id(Id.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")).
                vendor(Vendor.CISCO).
                model(Model.XYZ0004).
                ip(IP.fromAddress("20.0.0.100")).
                location(location).
                switchType(SwitchType.LAYER3).
                switchNetworks(networks).
                build();
    }

    private List<Network> createNetworks(Network network) {
        List<Network> networks = new ArrayList<>();
        networks.add(network);
        return networks;
    }

    private EdgeRouter createEdgeRouter(Location location, String address){
        Map<Id, Switch> switchesOfEdgeRouter = new HashMap<>();
        return EdgeRouter.builder().
                id(Id.withoutId()).
                vendor(Vendor.CISCO).
                model(Model.XYZ0002).
                ip(IP.fromAddress(address)).
                location(location).
                routerType(RouterType.EDGE).
                switches(switchesOfEdgeRouter).
                build();
    }

    private CoreRouter createCoreRouter(Location location, String address){
        Map<Id, Router> routersOfCoreRouter = new HashMap<>();
        return CoreRouter.builder().
                id(Id.withoutId()).
                vendor(Vendor.HP).
                model(Model.XYZ0001).
                ip(IP.fromAddress(address)).
                location(location).
                routerType(RouterType.CORE).
                routers(routersOfCoreRouter).
                build();
    }
}
