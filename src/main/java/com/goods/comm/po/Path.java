package com.goods.comm.po;

import java.util.List;

//表示从一个地点到另一个地点的路径
public class Path {
    private List<Location> pathPoints;
    private double totalDistance;

    // 构造函数
    public Path(List<Location> pathPoints) {
        this.pathPoints = pathPoints;
        this.totalDistance = calculateTotalDistance(pathPoints);
    }

    // 获取路径上的所有点
    public List<Location> getPathPoints() {
        return pathPoints;
    }

    // 获取路径的总长度
    public double getTotalDistance() {
        return totalDistance;
    }

    // 计算路径的总长度
    private double calculateTotalDistance(List<Location> pathPoints) {
        double distance = 0.0;
        for (int i = 0; i < pathPoints.size() - 1; i++) {
            Location current = pathPoints.get(i);
            Location next = pathPoints.get(i + 1);
            distance += current.distanceTo(next);
        }
        return distance;
    }

    // 打印路径信息
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Location point : pathPoints) {
            sb.append(point.toString()).append(" -> ");
        }
        sb.append("Total Distance: ").append(totalDistance);
        return sb.toString();
    }
}
