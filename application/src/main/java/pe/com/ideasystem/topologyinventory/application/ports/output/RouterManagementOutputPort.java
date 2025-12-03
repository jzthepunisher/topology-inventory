package pe.com.ideasystem.topologyinventory.application.ports.output;

import pe.com.ideasystem.topologyinventory.domain.entity.Router;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;

public interface RouterManagementOutputPort {
    Router retrieveRouter(Id id);
    boolean removeRouter(Id id);
    Router persistRouter(Router router);
}
