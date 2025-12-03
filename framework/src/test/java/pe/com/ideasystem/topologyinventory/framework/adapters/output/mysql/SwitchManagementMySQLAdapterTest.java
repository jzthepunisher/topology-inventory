package pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pe.com.ideasystem.topologyinventory.domain.entity.Switch;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Model;
import pe.com.ideasystem.topologyinventory.domain.vo.SwitchType;
import pe.com.ideasystem.topologyinventory.domain.vo.Vendor;

@QuarkusTest
public class SwitchManagementMySQLAdapterTest {

    @InjectMock
    SwitchManagementMySQLAdapter switchManagementMySQLAdapter;

    @Test
    public void testRetrieveSwitch() {
        Switch aSwitch = getSwitch();
        Mockito.when(switchManagementMySQLAdapter.retrieveSwitch(aSwitch.getId())).thenReturn(aSwitch);
        Switch retrievedSwitch = switchManagementMySQLAdapter.retrieveSwitch(aSwitch.getId());
        Assertions.assertSame(aSwitch, retrievedSwitch);

    }

    Switch getSwitch(){
        return Switch.builder()
                .id(Id.withoutId())
                .switchType(SwitchType.LAYER3)
                .switchNetworks(null)
                .ip(IP.fromAddress("10.0.0.1"))
                .model(Model.XYZ0004)
                .vendor(Vendor.CISCO)
                .location(null)
                .build();
    }
}
