module framework {
    requires domain;
    requires application;
    requires static lombok;
    requires org.eclipse.persistence.core;
    requires java.sql;
    requires jakarta.persistence;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    exports pe.com.ideasystem.topologyinventory.framework.adapters.output.h2.data;
    opens pe.com.ideasystem.topologyinventory.framework.adapters.output.h2.data;
}