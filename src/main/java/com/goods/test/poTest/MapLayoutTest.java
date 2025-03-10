package com.goods.test.poTest;

import com.goods.comm.po.Location;
import com.goods.comm.po.MapLayout;
import com.goods.comm.po.Path;
import com.goods.comm.po.Route;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MapLayoutTest {
    @Test
    public void testAddStoreLocation() {
        // 测试添加商店位置
        MapLayout mapLayout = new MapLayout();
        Location location = new Location(10, 20);

        mapLayout.addStoreLocation(location);

        List<Location> storeLocations = mapLayout.getStoreLocations();

        Assertions.assertEquals(1, storeLocations.size());
        Assertions.assertEquals(location, storeLocations.get(0));
    }

    @Test
    public void testAddTemporaryWarehouseLocation() {
        // 测试添加临时仓库位置
        MapLayout mapLayout = new MapLayout();
        Location location = new Location(30, 40);

        mapLayout.addTemporaryWarehouseLocation(location);

        List<Location> temporaryWarehouseLocations = mapLayout.getTemporaryWarehouseLocations();

        Assertions.assertEquals(1, temporaryWarehouseLocations.size());
        Assertions.assertEquals(location, temporaryWarehouseLocations.get(0));
    }

    @Test
    public void testAddRoute() {
        // 测试添加路线
        MapLayout mapLayout = new MapLayout();
        Location from = new Location(10, 20);
        Location to = new Location(30, 40);
        double weight = 10.0;

        mapLayout.addRoute(from, to, weight);

        List<Route> routes = mapLayout.getRoutes();

        Assertions.assertEquals(1, routes.size());
        Assertions.assertEquals(from, routes.get(0));
        Assertions.assertEquals(to, routes.get(0));
        Assertions.assertEquals(weight, routes.get(0).getWeight(), 0.0001);
    }

    @Test
    public void testGetShortestPath() {
        // 测试获取最短路径
        MapLayout mapLayout = new MapLayout();
        Location from = new Location(10, 20);
        Location to = new Location(30, 40);
        double weight = 10.0;

        mapLayout.addRoute(from, to, weight);

        Path shortestPath = mapLayout.getShortestPath(from, to);

        // 验证最短路径的结果
        // ...
    }

    @Test
    public void testAddRandomRoutes() {
        // 测试添加随机路线
        MapLayout mapLayout = new MapLayout();
        int numberOfRoutes = 5;
        int gridWidth = 100;
        int gridHeight = 100;
        double weight = 10.0;

        mapLayout.addRandomRoutes(numberOfRoutes, gridWidth, gridHeight, weight);

        List<Route> routes = mapLayout.getRoutes();

        Assertions.assertEquals(numberOfRoutes, routes.size());
        // 验证随机路线的结果
        // ...
    }

    @Test
    public void testAddCustomRoute() {
        // 测试添加手动指定的路线
        MapLayout mapLayout = new MapLayout();
        Location from = new Location(10, 20);
        Location to = new Location(30, 40);

        mapLayout.addCustomRoute(from, to);

        List<Route> routes = mapLayout.getRoutes();

        Assertions.assertEquals(1, routes.size());
        Assertions.assertEquals(from, routes.get(0));
        Assertions.assertEquals(to, routes.get(0));
    }
}

