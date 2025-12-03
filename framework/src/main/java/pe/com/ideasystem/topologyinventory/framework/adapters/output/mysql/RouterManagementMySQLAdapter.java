package pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql;

import io.quarkus.hibernate.reactive.panache.Panache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pe.com.ideasystem.topologyinventory.application.ports.output.RouterManagementOutputPort;
import pe.com.ideasystem.topologyinventory.domain.entity.Router;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql.mappers.RouterMapper;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql.repository.RouterManagementRepository;

@ApplicationScoped
public class RouterManagementMySQLAdapter implements RouterManagementOutputPort {

    @Inject
    RouterManagementRepository routerManagementRepository;

    @Override
    public Router retrieveRouter(Id id) {
        var routerData = routerManagementRepository.findById(id.getUuid()).subscribe().asCompletionStage().join();
        return RouterMapper.routerDataToDomain(routerData);
    }

    @Override
    public boolean removeRouter(Id id) {
        return routerManagementRepository.deleteById(id.getUuid()).subscribe().asCompletionStage().join();
    }

    @Override
    public Router persistRouter(Router router) {
        var routerData = RouterMapper.routerDomainToData(router);
        Panache.withTransaction( () -> routerManagementRepository.persist(routerData));
        return router;
    }

}
