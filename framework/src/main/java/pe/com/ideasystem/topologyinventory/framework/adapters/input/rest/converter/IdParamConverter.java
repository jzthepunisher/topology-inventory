package pe.com.ideasystem.topologyinventory.framework.adapters.input.rest.converter;

import jakarta.ws.rs.ext.ParamConverter;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;

public class IdParamConverter implements ParamConverter<Id> {

    @Override
    public Id fromString(String value){
        return Id.withId(value);
    }

    @Override
    public String toString(Id id) {
        return id.getUuid().toString();
    }
}

