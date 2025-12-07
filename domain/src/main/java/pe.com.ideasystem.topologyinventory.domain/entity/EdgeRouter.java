package pe.com.ideasystem.topologyinventory.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pe.com.ideasystem.topologyinventory.domain.specification.AllowedCitySpec;
import pe.com.ideasystem.topologyinventory.domain.specification.AllowedCountrySpec;
import pe.com.ideasystem.topologyinventory.domain.specification.EmptyNetworkSpec;
import pe.com.ideasystem.topologyinventory.domain.specification.SameCountrySpec;
import pe.com.ideasystem.topologyinventory.domain.specification.SameIpSpec;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Location;
import pe.com.ideasystem.topologyinventory.domain.vo.Model;
import pe.com.ideasystem.topologyinventory.domain.vo.RouterType;
import pe.com.ideasystem.topologyinventory.domain.vo.Vendor;

import java.util.Map;

@Getter
@ToString
public final class EdgeRouter extends Router {

    @Setter
    private Map<Id, Switch> switches;

    @Builder
    public EdgeRouter(Id id,
                      Vendor vendor,
                      Model model,
                      IP ip,
                      Location location,
                      RouterType routerType,
                      Map<Id, Switch> switches
    ) {
        super(id, vendor, model, ip, location, routerType);
        this.switches = switches;
    }

    public void addSwitch(Switch anySwitch) {
        var sameCountryRouterSpec = new SameCountrySpec(this);
        var sameIpSpec = new SameIpSpec(this);

        sameCountryRouterSpec.check(anySwitch);
        sameIpSpec.check(anySwitch);

        this.switches.put(anySwitch.id, anySwitch);
    }

    public Switch removeSwitch(Switch anySwitch) {
        var emptyNetworkSpec = new EmptyNetworkSpec();
        emptyNetworkSpec.check(anySwitch);

        return this.switches.remove(anySwitch.id);
    }

    @Override
    public void changeLocation(Location location) {
        var allowedCountrySpec = new AllowedCountrySpec();
        var allowedCitySpec = new AllowedCitySpec();
        allowedCountrySpec.check(location);
        allowedCitySpec.check(location);
        this.location = location;
    }
}
