package com.goods.test.poTest;

import com.goods.comm.po.Location;
import com.goods.comm.po.Route;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RouteTest {
    @Test
    public void testGetStart() {
        // 测试获取路线的起点
        Location start = new Location(10, 20);
        Location end = new Location(30, 40);
        double weight = 10.0;

        Route route = new Route(start, end, weight);

        Location result = route.getStart();

        Assertions.assertEquals(start, result);
    }

    @Test
    public void testSetStart() {
        // 测试设置路线的起点
        Location start = new Location(10, 20);
        Location end = new Location(30, 40);
        double weight = 10.0;

        Route route = new Route(start, end, weight);

        Location newStart = new Location(50, 60);
        route.setStart(newStart);

        Location result = route.getStart();

        Assertions.assertEquals(newStart, result);
    }

    @Test
    public void testGetEnd() {
        // 测试获取路线的终点
        Location start = new Location(10, 20);
        Location end = new Location(30, 40);
        double weight = 10.0;

        Route route = new Route(start, end, weight);

        Location result = route.getEnd();

        Assertions.assertEquals(end, result);
    }

    @Test
    public void testSetEnd() {
        // 测试设置路线的终点
        Location start = new Location(10, 20);
        Location end = new Location(30, 40);
        double weight = 10.0;

        Route route = new Route(start, end, weight);

        Location newEnd = new Location(50, 60);
        route.setEnd(newEnd);

        Location result = route.getEnd();

        Assertions.assertEquals(newEnd, result);
    }

    @Test
    public void testGetWeight() {
        // 测试获取路线的权重
        Location start = new Location(10, 20);
        Location end = new Location(30, 40);
        double weight = 10.0;

        Route route = new Route(start, end, weight);

        double result = route.getWeight();

        Assertions.assertEquals(weight, result, 0.0001);
    }

    @Test
    public void testSetWeight() {
        // 测试设置路线的权重
        Location start = new Location(10, 20);
        Location end = new Location(30, 40);
        double weight = 10.0;

        Route route = new Route(start, end, weight);

        double newWeight = 20.0;
        route.setWeight(newWeight);

        double result = route.getWeight();

        Assertions.assertEquals(newWeight, result, 0.0001);
    }
}
