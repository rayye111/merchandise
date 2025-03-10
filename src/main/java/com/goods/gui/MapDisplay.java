package com.goods.gui;

import com.goods.comm.helper.DatabaseHelper;
import com.goods.comm.po.Location;
import com.goods.comm.po.MapLayout;
import com.goods.comm.po.Route;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Random;

public class MapDisplay extends JFrame {
    private static MapLayout mapLayout;
    private final int gridSize = 20; // 网格大小

    public MapDisplay(MapLayout mapLayout) {
        this.mapLayout = mapLayout;
        setTitle("仓库选址系统");
        setSize(50, 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new MapPanel(mapLayout, gridSize));
        setUpUI();
    }

    private void setUpUI() {
//        askForWarehouseCount(); // 询问用户要添加多少个仓库
        // 设置布局
        setLayout(new BorderLayout());

        // 添加地图面板
        MapPanel mapPanel = new MapPanel(mapLayout, gridSize);
        add(mapPanel, BorderLayout.CENTER);


        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("选项");
        JMenuItem menuItemUserInfo = new JMenuItem("个人中心");
        menu.add(menuItemUserInfo);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // 为“个人中心”菜单项添加事件监听器
        menuItemUserInfo.addActionListener(e -> openUserInfoDisplay());


        // 添加控制面板
        JPanel controlPanel = new JPanel();
        // 添加控制元素，如按钮、文本框等
        // 例如：添加商店、仓库和路线的按钮
        JButton addStoreButton = new JButton("增加商店");
        JButton addWarehouseButton = new JButton("增加仓库地址");
        JButton addRouteButton = new JButton("增加路线");

        // 添加按钮的点击事件
        addStoreButton.addActionListener(e -> addStore(mapPanel));
        addWarehouseButton.addActionListener(e -> addWarehouse(mapPanel));
        addRouteButton.addActionListener(e -> addRoute(mapPanel));

        // 将按钮添加到控制面板
        controlPanel.add(addStoreButton);
        controlPanel.add(addWarehouseButton);
        controlPanel.add(addRouteButton);

        // 将控制面板添加到窗口
        add(controlPanel, BorderLayout.SOUTH);

        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(e -> {
            try {
                DatabaseHelper.saveToDatabase(mapLayout);
                JOptionPane.showMessageDialog(this, "信息已保存到数据库");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "保存失败：" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        controlPanel.add(saveButton);

        // 重新绘制界面以显示更新
        mapPanel.repaint();
    }

    private void openUserInfoDisplay() {
        // 打开用户信息界面
        new UserInfoDisplay().setVisible(true);
    }

    void askForWarehouseCount() {
        try {
            String countStr = JOptionPane.showInputDialog(this, "请输入仓库的数量:");
            int count = Integer.parseInt(countStr);
            generateWarehouses(count); // 根据用户输入生成仓库
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字");
        }
    }
    private static void generateWarehouses(int count) {
        for (int i = 0; i < count; i++) {
            // 自动生成仓库位置
            Location warehouseLocation = generateRandomLocation();
            mapLayout.addTemporaryWarehouseLocation(warehouseLocation);

            // 这里添加仓库与其他地点之间的路线
        }

        // 需要刷新地图界面以显示新添加的仓库
    }

    private static Location generateRandomLocation() {
        // 生成随机位置的逻辑
        Random random = new Random();
        int x = random.nextInt(24) + 1;; // 随机生成 x 坐标
        int y = random.nextInt(24) + 1;; // 随机生成 y 坐标
        return new Location(x, y);
    }
    // 添加商店、仓库和路线的方法
    private void addStore(MapPanel mapPanel) {
        // 显示对话框以获取商店的坐标
        String locationStr = JOptionPane.showInputDialog(this, "输入商店位置 (x,y):");
        if (locationStr != null && !locationStr.trim().isEmpty()) {
            try {
                String[] parts = locationStr.split(",");
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());

                Location storeLocation = new Location(x, y);
                mapLayout.addStoreLocation(storeLocation);

                // 遍历现有的仓库位置，为每个仓库和新商店之间添加路线
                for (Location warehouse : mapLayout.getTemporaryWarehouseLocations()) {
                    double weight = getDefaultWeight(); // 获取或计算权重
                    mapLayout.addRoute(storeLocation, warehouse, weight);
                }

                mapPanel.repaint();
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(this, "无效的位置格式。请按格式输入坐标: x,y",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void addWarehouse(MapPanel mapPanel) {
        // 显示对话框以获取仓库的坐标
        String locationStr = JOptionPane.showInputDialog(this, "输入仓库地址 (x,y):");
        if (locationStr != null && !locationStr.trim().isEmpty()) {
            try {
                String[] parts = locationStr.split(",");
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());

                Location warehouseLocation = new Location(x, y);
                mapLayout.addTemporaryWarehouseLocation(warehouseLocation);

                // 对每个已存在的商店，要求输入权值
                for (Location store : mapLayout.getStoreLocations()) {
                    String weightStr = JOptionPane.showInputDialog(
                            this,
                            "输入从仓库地址 (" + x + "," + y +
                                    ") 到商店 (" + store.getX() + "," + store.getY() + ")的时间成本:"
                    );
                    if (weightStr != null && !weightStr.trim().isEmpty()) {
                        double weight = Double.parseDouble(weightStr.trim());
                        mapLayout.addRoute(warehouseLocation, store, weight);
                    }
                }

                mapPanel.repaint();
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(this, "无效的格式。请输入正确的坐标和时间成本",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private double getDefaultWeight() {
        // 返回默认权重，或根据某些逻辑计算权重
        return 1.0;
    }



    private void addRoute(MapPanel mapPanel) {
        // 显示对话框以获取路线的起点、终点和权值
        String routeStr = JOptionPane.showInputDialog(this, "输入路线 (startX,startY,endX,endY,weight):");
        if (routeStr != null && !routeStr.trim().isEmpty()) {
            try {
                String[] parts = routeStr.split(",");
                int startX = Integer.parseInt(parts[0].trim());
                int startY = Integer.parseInt(parts[1].trim());
                int endX = Integer.parseInt(parts[2].trim());
                int endY = Integer.parseInt(parts[3].trim());
                double weight = Double.parseDouble(parts[4].trim());

                // 创建起点和终点位置
                Location startLocation = new Location(startX, startY);
                Location endLocation = new Location(endX, endY);

                // 添加路线到地图布局中
                mapLayout.addRoute(startLocation, endLocation, weight);

                // 重新绘制地图面板以显示新添加的路线
                mapPanel.repaint();
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(this, "无效的格式。请输入路线 startX,startY,endX,endY,weight",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }


    public static void main(String[] args) {
        MapLayout mapLayout = new MapLayout();

//        // 添加一个商店
//        Location store = new Location(16, 15);
//        mapLayout.addStoreLocation(store);
//
//        // 添加一些仓库
//        Location warehouse1 = new Location(1, 25);
//        Location warehouse2 = new Location(6, 3);
//        Location warehouse3 = new Location(25, 11);
//        Location warehouse4 = new Location(9, 25);
//        mapLayout.addTemporaryWarehouseLocation(warehouse1);
//        mapLayout.addTemporaryWarehouseLocation(warehouse2);
//        mapLayout.addTemporaryWarehouseLocation(warehouse3);
//        mapLayout.addTemporaryWarehouseLocation(warehouse4);
//
//        //路的权值
//        double weight1 =1.2;
//        double weight2 =1.0;
//        double weight3 =1.4;
//        double weight4 =2.0;
//        // 添加路线
//        mapLayout.addRoute(store, warehouse1,weight1);
//        mapLayout.addRoute(store, warehouse2,weight2);
//        mapLayout.addRoute(store, warehouse3,weight3);
//        mapLayout.addRoute(store, warehouse4,weight4);
//
//
//        // 添加自定义路线
//        Location start1 = new Location(22, 3);
//        Location end1 = new Location(15, 5);
//        mapLayout.addCustomRoute(start1, end1);
//
//
//
//        // 添加一些随机路线（如果需要）
//        int gridWidth = 40; // 网格宽度
//        int gridHeight = 30; // 网格高度
//        int numberOfRandomRoutes = 5; // 要添加的随机路线数量
//        double weight =1.2;
////        mapLayout.addRandomRoutes(numberOfRandomRoutes, gridWidth, gridHeight,weight);



        EventQueue.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(mapLayout); // 传递 mapLayout 到登录窗口
            loginFrame.setVisible(true);
        });

        // 打印所有边
//        mapLayout.printAllEdges();
        mapLayout.getGraph().printGraph();
    }
}


