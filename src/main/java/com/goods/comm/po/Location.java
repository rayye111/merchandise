package com.goods.comm.po;


//位置类  表示地图上的一个特定位置
public class Location {
    private int x;
    private int y;

    // 构造函数
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 获取X坐标
    public int getX() {
        return x;
    }

    // 设置X坐标
    public void setX(int x) {
        this.x = x;
    }

    // 获取Y坐标
    public int getY() {
        return y;
    }

    // 设置Y坐标
    public void setY(int y) {
        this.y = y;
    }

    // 计算与另一个位置之间的距离
    public double distanceTo(Location other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    // 重写toString方法，方便打印位置信息
    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

