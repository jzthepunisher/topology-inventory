module application {
    exports pe.com.ideasystem.topologyinventory.application.usecases;
    exports pe.com.ideasystem.topologyinventory.application.ports.output;
    exports pe.com.ideasystem.topologyinventory.application.ports.input;
    requires domain;
    requires static lombok;
}