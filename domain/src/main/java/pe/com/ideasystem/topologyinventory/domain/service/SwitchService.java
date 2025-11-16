package pe.com.ideasystem.topologyinventory.domain.service;

import pe.com.ideasystem.topologyinventory.domain.entity.Switch;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SwitchService {
    public static List<Switch> filterAndRetrieveSwitch(List<Switch> switches, Predicate<Switch> switchPredicate) {
        return switches
                .stream()
                .filter(switchPredicate)
                .collect(Collectors.<Switch>toList());
    }

    public static Switch findById(Map<Id,Switch> switches, Id id) {
        return switches.get(id);
    }
}
