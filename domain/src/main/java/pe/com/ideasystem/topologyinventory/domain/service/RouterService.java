package pe.com.ideasystem.topologyinventory.domain.service;

import pe.com.ideasystem.topologyinventory.domain.entity.Equipment;
import pe.com.ideasystem.topologyinventory.domain.entity.Router;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RouterService {
    public static List<Router> filterAndRetrieveRouter(List<Router> routers, Predicate<Router> routerPredicate) {
         return routers
                 .stream()
                 .filter(routerPredicate)
                 .collect(Collectors.<Router>toList());
    }

    public static Router findById(Map<Id, Router> routers, Id id) {
        return  routers.get(id);
    }
}
