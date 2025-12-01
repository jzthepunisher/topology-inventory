package pe.com.ideasystem.topologyinventory.application.usecases;

import pe.com.ideasystem.topologyinventory.application.ports.output.RouterManagementOutputPort;
import pe.com.ideasystem.topologyinventory.application.ports.output.SwitchManagementOutputPort;
import pe.com.ideasystem.topologyinventory.domain.entity.EdgeRouter;
import pe.com.ideasystem.topologyinventory.domain.entity.Switch;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Location;
import pe.com.ideasystem.topologyinventory.domain.vo.Model;
import pe.com.ideasystem.topologyinventory.domain.vo.SwitchType;
import pe.com.ideasystem.topologyinventory.domain.vo.Vendor;

public interface SwitchManagementUseCase {

    Switch createSwitch(
      Vendor vendor,
      Model model,
      IP ip,
      Location location,
      SwitchType switchType
    );

    Switch retrieveSwitch(Id id);

    EdgeRouter addSwitchToEdgeRouter(Switch networkSwitch, EdgeRouter edgeRouter);

    EdgeRouter removeSwitchFromEdgeRouter(Switch networkSwitch, EdgeRouter edgeRouter);
}
