package pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pe.com.ideasystem.topologyinventory.application.ports.output.SwitchManagementOutputPort;
import pe.com.ideasystem.topologyinventory.domain.entity.Switch;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql.mappers.RouterMapper;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql.repository.SwitchManagementRepository;

@ApplicationScoped
public class SwitchManagementMySQLAdapter implements SwitchManagementOutputPort {

    @Inject
    SwitchManagementRepository switchManagementRepository;

    @Override
    public Switch retrieveSwitch(Id id) {
        var switchData = switchManagementRepository.findById(id.getUuid()).subscribe().asCompletionStage().join();
        return RouterMapper.switchDataToDomain(switchData);
    }
}
