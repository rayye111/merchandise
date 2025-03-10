package com.goods.test.poTest;

import com.goods.comm.po.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class LocationTest {
    @Test
    public void testGettersAndSetters() {
        // 测试 getX() 和 setX() 方法
        Location location = new Location(10, 20);

        Assertions.assertEquals(10, location.getX());
        Assertions.assertEquals(20, location.getY());

        location.setX(30);
        location.setY(40);

        Assertions.assertEquals(30, location.getX());
        Assertions.assertEquals(40, location.getY());
    }

    @Test
    public void testDistanceTo() {
        // 测试 distanceTo() 方法
        Location location1 = new Location(10, 20);
        Location location2 = new Location(30, 40);

        double distance = location1.distanceTo(location2);

        Assertions.assertEquals(28.284271247461902, distance, 0.0001);
    }

    @Test
    public void testToString() {
        // 测试 toString() 方法
        Location location = new Location(10, 20);

        String expected = "Location{x=10, y=20}";
        String actual = location.toString();

        Assertions.assertEquals(expected, actual);
    }
}
