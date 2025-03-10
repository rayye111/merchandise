package com.goods.comm.po;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapLayout {
    private List<Location> storeLocations;
    private List<Location> temporaryWarehouseLocations;
    private Graph graph; // Graph类代表整个地图的图形结构
    private List<Route> routes; // 添加用于存储路线的列表


    // 获取Graph对象的方法
    public Graph getGraph() {
        return graph;
    }
    // 构造函数
    public MapLayout() {
        storeLocations = new ArrayList<>();
        temporaryWarehouseLocations = new ArrayList<>();
        graph = new Graph(); // 假设Graph是一个自定义类来表示图结构
        routes = new ArrayList<>(); // 初始化路线列表
    }

    // 打印所有边的方法
    public void printAllEdges() {
        graph.printAllEdges();
    }

    // 添加商店位置
    public void addStoreLocation(Location location) {
        storeLocations.add(location);
        graph.addNode(location); // 将位置添加到图形结构中
    }

    // 添加临时仓库位置
    public void addTemporaryWarehouseLocation(Location location) {
        temporaryWarehouseLocations.add(location);
        graph.addNode(location); // 将位置添加到图形结构中
    }

    // 添加路线到图结构中
    public void addRoute(Location from, Location to,double weight) {
//        routes.add(new Route(from, to));
        Route route = new Route(from, to,weight);
        routes.add(route);
        graph.addEdge(from, to, weight);
        System.out.println("Added edge from " + from + " to " + to + " with weight " + weight);
    }

    // 获取最短路径
    public Path getShortestPath(Location from, Location to) {
        // 这里调用图形算法来计算最短路径，Path是一个假设的类，用于表示路径
        return graph.calculateShortestPath(from, to);
    }

    // 获取商店位置
    public List<Location> getStoreLocations() {
        return storeLocations;
    }

    // 获取临时仓库位置
    public List<Location> getTemporaryWarehouseLocations() {
        return temporaryWarehouseLocations;
    }

    // 获取路线
    public List<Route> getRoutes() {
        return routes;
    }

    // 添加随机路线
    public void addRandomRoutes(int numberOfRoutes, int gridWidth, int gridHeight,double weight) {
        Random random = new Random();
        for (int i = 0; i < numberOfRoutes; i++) {
            int startX = random.nextInt(gridWidth);
            int startY = random.nextInt(gridHeight);
            int endX = random.nextInt(gridWidth);
            int endY = random.nextInt(gridHeight);
            addRoute(new Location(startX, startY), new Location(endX, endY),weight);
        }
    }
    // 添加手动指定的路线
    public void addCustomRoute(Location from, Location to) {
        routes.add(new Route(from, to));
    }

}
