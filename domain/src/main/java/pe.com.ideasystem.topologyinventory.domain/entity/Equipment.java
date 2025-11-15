package pe.com.ideasystem.topologyinventory.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Location;
import pe.com.ideasystem.topologyinventory.domain.vo.Model;
import pe.com.ideasystem.topologyinventory.domain.vo.Vendor;

import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public abstract sealed class Equipment permits Router, Switch {
    protected Id id;
    protected Vendor vendor;
    protected Model model;
    protected IP ip;
    protected Location location;

    public static Predicate<Equipment> getVendorPredicate(Vendor vendor) {
        return r -> r.getVendor().equals(vendor);
    }
}

