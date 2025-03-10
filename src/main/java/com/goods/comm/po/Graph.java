package com.goods.comm.po;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

public class Graph {
    private Map<Location, LinkedList<Edge>> adjacencyMap;

    // 方法来获取所有边
    public void printAllEdges() {
        // 遍历邻接表的每一个条目
        for (Map.Entry<Location, LinkedList<Edge>> entry : adjacencyMap.entrySet()) {
            // 获取源节点
            Location source = entry.getKey();
            // 获取源节点的所有边
            LinkedList<Edge> edges = entry.getValue();
            // 遍历源节点的所有边
            for (Edge edge : edges) {
                // 打印边的信息，包括源节点、目的地和权重
                System.out.println("Edge from " + source + " to " + edge.getDestination() + " with weight " + edge.getWeight());
            }
        }
    }
    // 构造函数
    public Graph() {
        adjacencyMap = new HashMap<>();
    }



    // 添加节点
    public void addNode(Location node) {
        adjacencyMap.putIfAbsent(node, new LinkedList<>());
    }

    // 添加边
    public void addEdge(Location source, Location destination, double weight) {
        // 确保映射中已经有这些节点的条目
        adjacencyMap.putIfAbsent(source, new LinkedList<>());
        adjacencyMap.putIfAbsent(destination, new LinkedList<>()); // 如果是无向图，也需要添加终点节点

        // 安全地添加边
        adjacencyMap.get(source).add(new Edge(destination, weight));
        System.out.println("Added edge from " + source + " to " + destination + " with weight " + weight);
    }



    // 计算从源到目的地的最短路径 (Dijkstra算法)
    public Path calculateShortestPath(Location source, Location destination) {
        PriorityQueue<PathNode> minHeap = new PriorityQueue<>();
        Map<Location, Double> distances = new HashMap<>();
        Map<Location, Location> previous = new HashMap<>();

        // 初始化距离和路径
        for (Location node : adjacencyMap.keySet()) {
            distances.put(node, Double.MAX_VALUE);
            previous.put(node, null);
        }

        distances.put(source, 0.0);
        minHeap.add(new PathNode(source, 0.0));

        while (!minHeap.isEmpty()) {
            PathNode currentNode = minHeap.poll();
            Location currentLocation = currentNode.getLocation();

            // 遍历所有邻接节点
            for (Edge edge : adjacencyMap.get(currentLocation)) {
                Location neighbor = edge.getDestination();
                double newDistance = distances.get(currentLocation) + edge.getWeight();

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, currentLocation);
                    minHeap.add(new PathNode(neighbor, newDistance));
                }
            }
        }

        // 构建最短路径
        return buildPath(destination, previous);
    }

    // 辅助方法来构建路径
    private Path buildPath(Location destination, Map<Location, Location> previous) {
        LinkedList<Location> path = new LinkedList<>();
        for (Location at = destination; at != null; at = previous.get(at)) {
            path.addFirst(at);
        }
        return new Path(path); // Path需要根据实际情况进行定义
    }

    // 方法来打印所有节点和边
    public void printGraph() {
        if (adjacencyMap.isEmpty()) {
            System.out.println("Graph is empty.");
            return;
        }
        for (Location node : adjacencyMap.keySet()) {
            System.out.print("Location " + node + " has edges to: ");
            for (Edge edge : adjacencyMap.get(node)) {
                System.out.print(edge.getDestination() + "(weight: " + edge.getWeight() + "), ");
            }
            System.out.println(); // 换行
        }
    }


}

// 边的表示
class Edge {
    // 边的目的地
    private Location destination;
    // 边的权重
    private double weight;

    public Edge(Location destination, double weight) {
        // 设置边的目的地
        this.destination = destination;
        // 设置边的权重
        this.weight = weight;
    }

    // 获取边的目的地
    public Location getDestination() {
        return destination;
    }

    // 获取边的权重
    public double getWeight() {
        return weight;
    }
}

// 用于Dijkstra算法的路径节点
class PathNode implements Comparable<PathNode> {
    // 节点的位置
    private Location location;
    // 从源节点到此节点的距离
    private double distance;
    // 构造PathNode函数
    public PathNode(Location location, double distance) {
        // 设置节点的位置
        this.location = location;
        // 设置从源节点到此节点的距离
        this.distance = distance;
    }
    // 获取节点的位置
    public Location getLocation() {
        return location;
    }

    // 获取从源节点到此节点的距离
    public double getDistance() {
        return distance;
    }

    // 重写compareTo方法，用于比较两个PathNode对象
    // 比较的依据是从源节点到各自节点的距离
    @Override
    public int compareTo(PathNode other) {
        return Double.compare(this.distance, other.distance);
    }
}
