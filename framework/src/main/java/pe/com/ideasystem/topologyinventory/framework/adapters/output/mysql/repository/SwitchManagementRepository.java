package pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import jakarta.enterprise.context.ApplicationScoped;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql.data.SwitchData;

import java.util.UUID;

@ApplicationScoped
@WithSession
public class SwitchManagementRepository implements PanacheRepositoryBase<SwitchData, UUID> {

}
