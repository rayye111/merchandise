package com.goods.comm.po;

// Route类表示两点之间的路线
public class Route {
    private Location start;
    private Location end;
    private double weight; // 路线的权重

    public Route(Location start, Location end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public Route(Location start, Location end) {
        this.start = start;
        this.end = end;
    }

    public Location getStart() {
        return start;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getEnd() {
        return end;
    }

    public void setEnd(Location end) {
        this.end = end;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
