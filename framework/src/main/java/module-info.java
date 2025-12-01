module framework {
    requires domain;
    requires application;
    requires static lombok;
    requires org.eclipse.persistence.core;
    requires java.sql;
    requires jakarta.persistence;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires jakarta.cdi;

    exports pe.com.ideasystem.topologyinventory.framework.adapters.output.h2.data;
    opens pe.com.ideasystem.topologyinventory.framework.adapters.output.h2.data;

    provides pe.com.ideasystem.topologyinventory.application.ports.output.RouterManagementOutputPort
            with pe.com.ideasystem.topologyinventory.framework.adapters.output.h2.RouterManagementH2Adapter;
    provides pe.com.ideasystem.topologyinventory.application.ports.output.SwitchManagementOutputPort
            with pe.com.ideasystem.topologyinventory.framework.adapters.output.h2.SwitchManagementH2Adapter;

    uses pe.com.ideasystem.topologyinventory.application.usecases.RouterManagementUseCase;
    uses pe.com.ideasystem.topologyinventory.application.usecases.SwitchManagementUseCase;
    uses pe.com.ideasystem.topologyinventory.application.usecases.NetworkManagementUseCase;
    uses pe.com.ideasystem.topologyinventory.application.ports.output.RouterManagementOutputPort;
    uses pe.com.ideasystem.topologyinventory.application.ports.output.SwitchManagementOutputPort;
}