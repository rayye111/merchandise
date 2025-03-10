package com.goods.gui;

import com.goods.comm.po.MapLayout;

public class fun {
    public static void main(String[] args) {
        // 创建地图（创建一个新的MapLayout对象）
        MapLayout mapLayout = new MapLayout();
        // 读取地图文件（获取MapLayout对象的图形并打印）
        mapLayout.getGraph().printGraph();
        System.out.println();
    }
}
