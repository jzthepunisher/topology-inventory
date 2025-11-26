module application {
    requires domain;
    requires static lombok;

    exports pe.com.ideasystem.topologyinventory.application.ports.input;
    exports pe.com.ideasystem.topologyinventory.application.ports.output;
    exports pe.com.ideasystem.topologyinventory.application.usecases;

    provides pe.com.ideasystem.topologyinventory.application.usecases.RouterManagementUseCase
            with pe.com.ideasystem.topologyinventory.application.ports.input.RouterManagementInputPort;
    provides pe.com.ideasystem.topologyinventory.application.usecases.SwitchManagementUseCase
            with pe.com.ideasystem.topologyinventory.application.ports.input.SwitchManagementInputPort;
    provides pe.com.ideasystem.topologyinventory.application.usecases.NetworkManagementUseCase
            with pe.com.ideasystem.topologyinventory.application.ports.input.NetworkManagementInputPort;
}