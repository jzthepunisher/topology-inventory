package pe.com.ideasystem.topologyinventory.application.mock;

import io.quarkus.test.Mock;
import pe.com.ideasystem.topologyinventory.application.ports.output.SwitchManagementOutputPort;
import pe.com.ideasystem.topologyinventory.domain.entity.Switch;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;

@Mock
public class SwitchManagementOutputPortMock implements SwitchManagementOutputPort {

    @Override
    public Switch retrieveSwitch(Id id) {
        return null;
    }
}
