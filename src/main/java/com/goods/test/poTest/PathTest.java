package com.goods.test.poTest;

import com.goods.comm.po.Location;
import com.goods.comm.po.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PathTest {
    @Test
    public void testGetPathPoints() {
        // 测试获取路径上的所有点
        List<Location> pathPoints = new ArrayList<>();
        pathPoints.add(new Location(10, 20));
        pathPoints.add(new Location(30, 40));
        pathPoints.add(new Location(50, 60));

        Path path = new Path(pathPoints);

        List<Location> result = path.getPathPoints();

        Assertions.assertEquals(pathPoints, result);
    }

    @Test
    public void testGetTotalDistance() {
        // 测试获取路径的总长度
        List<Location> pathPoints = new ArrayList<>();
        pathPoints.add(new Location(10, 20));
        pathPoints.add(new Location(30, 40));
        pathPoints.add(new Location(50, 60));

        Path path = new Path(pathPoints);

        double result = path.getTotalDistance();

        // 验证总长度的结果
        // ...
    }

    @Test
    public void testCalculateTotalDistance() throws Exception {
        // 测试计算路径的总长度
        List<Location> pathPoints = new ArrayList<>();
        pathPoints.add(new Location(10, 20));
        pathPoints.add(new Location(30, 40));
        pathPoints.add(new Location(50, 60));

        Path path = new Path(pathPoints);

        Method method = Path.class.getDeclaredMethod("calculateTotalDistance", List.class);
        method.setAccessible(true);
        double result = (double) method.invoke(path, pathPoints);


        // 验证计算总长度的结果
        // ...
    }

    @Test
    public void testToString() {
        // 测试打印路径信息
        List<Location> pathPoints = new ArrayList<>();
        pathPoints.add(new Location(10, 20));
        pathPoints.add(new Location(30, 40));
        pathPoints.add(new Location(50, 60));

        Path path = new Path(pathPoints);

        String result = path.toString();

        // 验证打印路径信息的结果
        // ...
    }
}

