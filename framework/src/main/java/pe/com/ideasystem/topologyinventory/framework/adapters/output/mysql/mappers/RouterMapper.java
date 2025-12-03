package pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql.mappers;

import pe.com.ideasystem.topologyinventory.domain.entity.CoreRouter;
import pe.com.ideasystem.topologyinventory.domain.entity.EdgeRouter;
import pe.com.ideasystem.topologyinventory.domain.entity.Router;
import pe.com.ideasystem.topologyinventory.domain.entity.Switch;
import pe.com.ideasystem.topologyinventory.domain.entity.factory.RouterFactory;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Location;
import pe.com.ideasystem.topologyinventory.domain.vo.Model;
import pe.com.ideasystem.topologyinventory.domain.vo.Network;
import pe.com.ideasystem.topologyinventory.domain.vo.RouterType;
import pe.com.ideasystem.topologyinventory.domain.vo.SwitchType;
import pe.com.ideasystem.topologyinventory.domain.vo.Vendor;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql.data.IPData;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql.data.LocationData;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql.data.ModelData;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql.data.NetworkData;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql.data.RouterData;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql.data.RouterTypeData;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql.data.SwitchData;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql.data.SwitchTypeData;
import pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql.data.VendorData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static pe.com.ideasystem.topologyinventory.domain.vo.Id.withId;

public class RouterMapper {

    public static Router routerDataToDomain(RouterData routerData){
        var router = RouterFactory.getRouter(
                withId(routerData.getRouterId().toString()),
                Vendor.valueOf(routerData.getRouterVendor().toString()),
                Model.valueOf(routerData.getRouterModel().toString()),
                IP.fromAddress(routerData.getIp().getAddress()),
                locationDataToLocation(routerData.getRouterLocation()),
                RouterType.valueOf(routerData.getRouterType().name()));
        if(routerData.getRouterParentCoreId()!=null)
            router.setParentRouterId(Id.withId(routerData.getRouterParentCoreId().toString()));
        if(routerData.getRouterType().equals(RouterTypeData.CORE)){
            var coreRouter = (CoreRouter) router;
            coreRouter.setRouters(getRoutersFromData(routerData.getRouters()));
            return coreRouter;
        } else {
            var edgeRouter = (EdgeRouter) router;
            edgeRouter.setSwitches(getSwitchesFromData(routerData.getSwitches()));
            return edgeRouter;
        }
    }

    public static RouterData routerDomainToData(Router router){
        var routerData = RouterData.builder().
                routerId(router.getId().getUuid()).
                routerVendor(VendorData.valueOf(router.getVendor().toString())).
                routerModel(ModelData.valueOf(router.getModel().toString())).
                ip(IPData.fromAddress(router.getIp().getIpAddress())).
                routerLocation(locationDomainToLocationData(router.getLocation())).
                routerType(RouterTypeData.valueOf(router.getRouterType().toString())).
                build();
        if(router.getParentRouterId()!=null)
            routerData.setRouterParentCoreId(router.getParentRouterId().getUuid());
        if(router.getRouterType().equals(RouterType.CORE)) {
            var coreRouter = (CoreRouter) router;
            routerData.setRouters(getRoutersFromDomain(coreRouter.getRouters()));
        } else {
            var edgeRouter = (EdgeRouter) router;
            routerData.setSwitches(getSwitchesFromDomain(edgeRouter.getSwitches()));
        }
        return routerData;
    }

    public static Switch switchDataToDomain(SwitchData switchData) {
        return Switch.builder().
                id(Id.withId(switchData.getSwitchId().toString())).
                routerId(Id.withId(switchData.getRouterId().toString())).
                vendor(Vendor.valueOf(switchData.getSwitchVendor().toString())).
                model(Model.valueOf(switchData.getSwitchModel().toString())).
                ip(IP.fromAddress(switchData.getIp().getAddress())).
                location(locationDataToLocation(switchData.getSwitchLocation())).
                switchType(SwitchType.valueOf(switchData.getSwitchType().toString())).
                switchNetworks(getNetworksFromData(switchData.getNetworks())).
                build();
    }

    public static SwitchData switchDomainToData(Switch aSwitch){
        return  SwitchData.builder().
                switchId(aSwitch.getId().getUuid()).
                routerId(aSwitch.getRouterId().getUuid()).
                switchVendor(VendorData.valueOf(aSwitch.getVendor().toString())).
                switchModel(ModelData.valueOf(aSwitch.getModel().toString())).
                ip(IPData.fromAddress(aSwitch.getIp().getIpAddress())).
                switchLocation(locationDomainToLocationData(aSwitch.getLocation())).
                switchType(SwitchTypeData.valueOf(aSwitch.getSwitchType().toString())).
                networks(getNetworksFromDomain(aSwitch.getSwitchNetworks(), aSwitch.getId().getUuid())).
                build();
    }

    public static Location locationDataToLocation(LocationData locationData){
        return new Location(
                locationData.getAddress(),
                locationData.getCity(),
                locationData.getState(),
                locationData.getZipcode(),
                locationData.getCountry(),
                locationData.getLatitude(),
                locationData.getLongitude());
    }

    public static LocationData locationDomainToLocationData(Location location){
        return LocationData.builder()
                .address(location.address())
                .city(location.city())
                .state(location.state())
                .zipcode(location.zipCode())
                .country(location.country())
                .latitude(location.latitude())
                .longitude(location.longitude())
                .build();
    }

    private static Map<Id, Router> getRoutersFromData(Set<RouterData> routerDataList){
        Map<Id,Router> routerMap = new HashMap<>();
        for (RouterData routerData : routerDataList) {
            routerMap.put(
                    withId(routerData.getRouterId().toString()),
                    routerDataToDomain(routerData));
        }
        return routerMap;
    }

    private static Set<RouterData>  getRoutersFromDomain(Map<Id, Router> routers){
        Set<RouterData> routerDataList = new HashSet<>();
        routers.values().stream().forEach(router -> {
            var routerData = routerDomainToData(router);
            routerDataList.add(routerData);
        });
        return routerDataList;
    }

    private static Map<Id, Switch> getSwitchesFromData(List<SwitchData> switchDataList){
        Map<Id,Switch> switchMap = new HashMap<>();
        for (SwitchData switchData : switchDataList) {
            switchMap.put(
                    withId(switchData.getSwitchId().toString()),
                    switchDataToDomain(switchData));
        }
        return switchMap;
    }

    private static List<SwitchData>  getSwitchesFromDomain(Map<Id, Switch> switches){
        List<SwitchData> switchDataList = new ArrayList<>();
        if(switches!=null) {
            switches.values().stream().forEach(aSwitch -> {
                switchDataList.add(switchDomainToData(aSwitch));
            });
        }
        return switchDataList;
    }

    private static List<Network> getNetworksFromData(Set<NetworkData> networkData){
        List<Network> networks = new ArrayList<>();
        networkData.forEach(data ->{
            var network = new Network(
                    IP.fromAddress(data.getIp().getAddress()),
                    data.getName(),
                    data.getCidr());
            networks.add(network);
        });
        return networks;
    }

    private static Set<NetworkData>  getNetworksFromDomain(List<Network> networks, UUID routerId){
        Set<NetworkData> networkDataSet = new HashSet<>();
        if(networks!=null) {
            networks.forEach(network -> {
                var networkData = new NetworkData(

                        IPData.fromAddress(network.getNetworkAddress().getIpAddress()),
                        network.getNetworkName(),
                        network.getNetworkCidr()
                );
                networkDataSet.add(networkData);
            });
        }
        return networkDataSet;
    }

}
