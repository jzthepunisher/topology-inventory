package pe.com.ideasystem.topologyinventory.framework.adapters.input.rest.converter;

import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Singleton
public class IdParamConverterProvider implements ParamConverterProvider {

    @SuppressWarnings("unchecked")
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType,
                                              Type genericType,
                                              Annotation[] annotations) {
        if (rawType.isAssignableFrom(Id.class)) {
            return (ParamConverter<T>) new IdParamConverter();
        }
        return null;
    }
}