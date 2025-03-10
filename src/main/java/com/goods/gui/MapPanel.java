package com.goods.gui;

import com.goods.comm.po.Location;
import com.goods.comm.po.MapLayout;
import com.goods.comm.po.Route;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//  将MapLayout中的数据打印到图中
public class MapPanel extends JPanel {
    private MapLayout mapLayout;
    private int gridSize;
    private BufferedImage mapImage;

    public MapPanel(MapLayout mapLayout, int gridSize) {
        this.mapLayout = mapLayout;
        this.gridSize = gridSize;
        // 加载地图图片
        try {
            mapImage = ImageIO.read(new File("pic/map.jpg")); // 替换为实际图片路径
        } catch (IOException e) {
            e.printStackTrace();
            mapImage = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 绘制地图背景图片
        if (mapImage != null) {
            g.drawImage(mapImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }

        drawGrid(g);
        drawMapElements(g);
    }

    // 绘制网格
    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < getWidth(); i += gridSize) {
            for (int j = 0; j < getHeight(); j += gridSize) {
                g.drawRect(i, j, gridSize, gridSize);
            }
        }
    }

    // 绘制地图元素（商店和仓库）
    private void drawMapElements(Graphics g) {
        // 绘制商店位置
        g.setColor(Color.RED);
        for (Location store : mapLayout.getStoreLocations()) {
            g.fillOval(store.getX() * gridSize, store.getY() * gridSize, gridSize, gridSize);
        }

        // 绘制临时仓库位置
        g.setColor(Color.BLUE);
        for (Location warehouse : mapLayout.getTemporaryWarehouseLocations()) {
            g.fillRect(warehouse.getX() * gridSize, warehouse.getY() * gridSize, gridSize, gridSize);
        }

        // 绘制网格路线
        g.setColor(Color.BLACK);
        for (Route route : mapLayout.getRoutes()) {
            drawGridRoute(g, route);
        }

//        // 绘制随机路线,此方法会生成直线
//        g.setColor(Color.BLACK);
//        for (Route route : mapLayout.getRoutes()) {
//            int x1 = route.getStart().getX() * gridSize;
//            int y1 = route.getStart().getY() * gridSize;
//            int x2 = route.getEnd().getX() * gridSize;
//            int y2 = route.getEnd().getY() * gridSize;
//            g.drawLine(x1, y1, x2, y2);
//        }
    }

    // 绘制沿着网格的路线
    private void drawGridRoute(Graphics g, Route route) {
        int x1 = route.getStart().getX() * gridSize + gridSize / 2;
        int y1 = route.getStart().getY() * gridSize + gridSize / 2;
        int x2 = route.getEnd().getX() * gridSize + gridSize / 2;
        int y2 = route.getEnd().getY() * gridSize + gridSize / 2;

        // 沿着网格绘制路线
        while (x1 != x2 || y1 != y2) {
            if (x1 != x2) {
                // 水平移动
                x1 += (x2 > x1) ? gridSize : -gridSize;
            } else if (y1 != y2) {
                // 垂直移动
                y1 += (y2 > y1) ? gridSize : -gridSize;
            }
            // 在网格上绘制一小段路线
            g.drawLine(x1 - gridSize / 2, y1 - gridSize / 2, x1 + gridSize / 2, y1 + gridSize / 2);
        }
    }
}
