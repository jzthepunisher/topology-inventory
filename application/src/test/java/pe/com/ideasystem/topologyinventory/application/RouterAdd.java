package pe.com.ideasystem.topologyinventory.application;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pe.com.ideasystem.topologyinventory.domain.entity.CoreRouter;
import pe.com.ideasystem.topologyinventory.domain.entity.EdgeRouter;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Model;
import pe.com.ideasystem.topologyinventory.domain.vo.Vendor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static pe.com.ideasystem.topologyinventory.domain.vo.RouterType.CORE;
import static pe.com.ideasystem.topologyinventory.domain.vo.RouterType.EDGE;

public class RouterAdd extends ApplicationTestData {

    public RouterAdd() {
        loadData();
    }

    //Adding an edge router to a core router
    @Given("I have an edge router")
    public void assert_edge_router_exists(){
        edgeRouter = (EdgeRouter) this.routerManagementUseCase.createRouter(
                Vendor.HP,
                Model.XYZ0004,
                IP.fromAddress("20.0.0.1"),
                locationA,
                EDGE
        );
        assertNotNull(edgeRouter);
    }

    @And("I have a core router")
    public void assert_core_router_exists() {
        coreRouter = (CoreRouter) this.routerManagementUseCase.createRouter(
                Vendor.CISCO,
                Model.XYZ0001,
                IP.fromAddress("30.0.0.1"),
                locationA,
                CORE
        );
        assertNotNull(coreRouter);
    }

    @Then("I add an edge router to a core router")
    public void add_edge_to_core_router(){
        var actualEdgeId = edgeRouter.getId();
        var routerWithEdge = (CoreRouter) this.routerManagementUseCase.addRouterToCoreRouter(edgeRouter, coreRouter);
        var expectedEdgeId = routerWithEdge.getRouters().get(actualEdgeId).getId();
        assertEquals(actualEdgeId, expectedEdgeId);
    }

}
