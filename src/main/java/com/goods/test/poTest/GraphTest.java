package com.goods.test.poTest;

import com.goods.comm.po.Graph;
import com.goods.comm.po.Location;
import com.goods.comm.po.Path;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class GraphTest {

    @Test
    void testGraph() throws Exception{
        // 初始化一些Location对象
        Location location1 = new Location(5,6);
        Location location2 = new Location(20,15);

        // 初始化一个Graph对象
        Graph graph = new Graph();

        // 添加节点
        graph.addNode(location1);
        graph.addNode(location2);

        // 添加边
        graph.addEdge(location1, location2, 10.0);

        //打印所有节点和边(验证节点和边是否已添加)
        graph.printGraph();

        // 计算最短路径
        Path shortestPath = graph.calculateShortestPath(location1, location2);

        // 获取私有方法
        Method method = Graph.class.getDeclaredMethod("buildPath", Location.class, Map.class);
        // 设置方法可访问
        method.setAccessible(true);
        // 初始化previous Map
        Map<Location,Location> previous = new HashMap<>();
        previous.put(location1,location2);
        // 调用方法并获取结果
        Path result = (Path) method.invoke(graph,location1,previous);

        // 调用方法来获取所有边
        graph.printAllEdges();

    }
}
