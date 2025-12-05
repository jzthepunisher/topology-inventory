package pe.com.ideasystem.topologyinventory.bootstrap;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pe.com.ideasystem.topologyinventory.domain.entity.CoreRouter;
import pe.com.ideasystem.topologyinventory.domain.entity.EdgeRouter;
import pe.com.ideasystem.topologyinventory.domain.entity.Switch;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Location;
import pe.com.ideasystem.topologyinventory.domain.vo.Model;
import pe.com.ideasystem.topologyinventory.domain.vo.Network;
import pe.com.ideasystem.topologyinventory.domain.vo.Protocol;
import pe.com.ideasystem.topologyinventory.domain.vo.RouterType;
import pe.com.ideasystem.topologyinventory.domain.vo.SwitchType;
import pe.com.ideasystem.topologyinventory.domain.vo.Vendor;

@RegisterForReflection(targets = {
        CoreRouter.class,
        EdgeRouter.class,
        Switch.class,
        Id.class,
        IP.class,
        Location.class,
        Model.class,
        Network.class,
        Protocol.class,
        RouterType.class,
        SwitchType.class,
        Vendor.class,
})
public class ReflectionConfiguration {

}