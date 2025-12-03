package pe.com.ideasystem.topologyinventory.framework.adapters.output.mysql;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pe.com.ideasystem.topologyinventory.domain.entity.Router;
import pe.com.ideasystem.topologyinventory.domain.entity.factory.RouterFactory;
import pe.com.ideasystem.topologyinventory.domain.vo.IP;
import pe.com.ideasystem.topologyinventory.domain.vo.Id;
import pe.com.ideasystem.topologyinventory.domain.vo.Model;
import pe.com.ideasystem.topologyinventory.domain.vo.RouterType;
import pe.com.ideasystem.topologyinventory.domain.vo.Vendor;
import pe.com.ideasystem.topologyinventory.framework.adapters.input.rest.RouterManagementAdapterTest;

@QuarkusTest
public class RouterManagementMySQLAdapterTest {

    @InjectMock
    RouterManagementMySQLAdapter routerManagementMySQLAdapter;

    @Test
    public void testRetrieveRouter() {
        Router router = getRouter();
        Mockito.when(routerManagementMySQLAdapter.retrieveRouter(router.getId())).thenReturn(router);
        Router retrievedRouter = routerManagementMySQLAdapter.retrieveRouter(router.getId());
        Assertions.assertSame(router, retrievedRouter);
    }

    @Test
    public void testRemoveRouter() {
        Router router = getRouter();
        Mockito.when(routerManagementMySQLAdapter.removeRouter(router.getId())).thenReturn(true);
        var isRouterRemoved = routerManagementMySQLAdapter.removeRouter(router.getId());
        Assertions.assertTrue(isRouterRemoved);
    }

    @Test
    public void testPersistRouter() {
        Router router = getRouter();
        Mockito.when(routerManagementMySQLAdapter.persistRouter(router)).thenReturn(router);
        Router persistedRouter = routerManagementMySQLAdapter.persistRouter(router);
        Assertions.assertSame(router, persistedRouter);
    }

    private Router getRouter(){
        return RouterFactory.getRouter(
                Id.withoutId(),
                Vendor.CISCO,
                Model.XYZ0004,
                IP.fromAddress("10.0.0.1"),
                RouterManagementAdapterTest.createLocation("US"),
                RouterType.EDGE);
    }
}
