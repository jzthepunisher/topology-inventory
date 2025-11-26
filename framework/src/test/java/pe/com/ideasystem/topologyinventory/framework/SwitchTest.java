package pe.com.ideasystem.topologyinventory.framework;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import pe.com.ideasystem.topologyinventory.domain.entity.EdgeRouter;
import pe.com.ideasystem.topologyinventory.domain.entity.Switch;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Model;
import pe.com.ideasystem.topologyinventory.domain.vo.SwitchType;
import pe.com.ideasystem.topologyinventory.domain.vo.Vendor;
import pe.com.ideasystem.topologyinventory.framework.adapters.input.generic.SwitchManagementGenericAdapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SwitchTest extends FrameworkTestData {

    SwitchManagementGenericAdapter switchManagementGenericAdapter;

    public SwitchTest(){
        this.switchManagementGenericAdapter = new SwitchManagementGenericAdapter(null, null);
        loadData();
    }

    @Test
    @Order(1)
    public void retrieveSwitch(){
        Id switchId = Id.withId("922dbcd5-d071-41bd-920b-00f83eb4bb47");
        Switch networkSwitch = switchManagementGenericAdapter.retrieveSwitch(switchId);
        assertNotNull(networkSwitch);
    }

    @Test
    @Order(2)
    public void createAndAddSwitchToEdgeRouter(){
        var expectedSwitchIP = "15.0.0.1";
        var id = Id.withId("b07f5187-2d82-4975-a14b-bdbad9a8ad46");
        EdgeRouter edgeRouter = switchManagementGenericAdapter.createAndAddSwitchToEdgeRouter(
                Vendor.HP,
                Model.XYZ0004,
                IP.fromAddress(expectedSwitchIP),
                locationA,
                SwitchType.LAYER3,
                id);
        String actualSwitchIP = edgeRouter.getSwitches()
                .entrySet()
                .stream()
                .map(entry -> entry.getValue())
                .map(aSwitch -> aSwitch.getIp().getIpAddress())
                .filter(ipAddress -> ipAddress.equals(expectedSwitchIP))
                .findFirst()
                .get();
        assertEquals(expectedSwitchIP,actualSwitchIP);
    }
}
