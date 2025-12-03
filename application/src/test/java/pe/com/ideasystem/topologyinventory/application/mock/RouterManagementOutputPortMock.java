package pe.com.ideasystem.topologyinventory.application.mock;

import io.quarkus.test.Mock;
import pe.com.ideasystem.topologyinventory.application.ports.output.RouterManagementOutputPort;
import pe.com.ideasystem.topologyinventory.domain.entity.Router;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;

@Mock
public class RouterManagementOutputPortMock implements RouterManagementOutputPort {

    @Override
    public Router retrieveRouter(Id id) {
        return null;
    }

    @Override
    public boolean removeRouter(Id id) {
        return true;
    }

    @Override
    public Router persistRouter(Router router) {
        return null;
    }
}
