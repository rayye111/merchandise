package com.goods.gui;

import com.goods.comm.helper.DatabaseHelper;
import com.goods.comm.po.MapLayout;
import com.goods.comm.po.User;
import com.goods.utils.PasswordUtil;
import com.goods.utils.UserSessionUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.*;

public class LoginFrame extends JFrame {

    public LoginFrame(MapLayout mapLayout) {
        setTitle("登录");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// 创建并设置背景面板
//        BackgroundPanel backgroundPanel = new BackgroundPanel("pic/back.jpg"); // 替换为实际图片路径
//        backgroundPanel.setLayout(new GridBagLayout());
//        setContentPane(backgroundPanel);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // 设置组件间的间隙

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("邮箱:"), gbc);
        JTextField usernameField = new JTextField();
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("密码:"), gbc);
        JPasswordField passwordField = new JPasswordField();
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // 让按钮跨越两列
        gbc.anchor = GridBagConstraints.CENTER; // 按钮居中对齐

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton loginButton = new JButton("登录");
        loginButton.addActionListener(e -> {
            // 登录逻辑
            String email = usernameField.getText();
            String password = new String(passwordField.getPassword());

            boolean loginSuccessful = false/* 调用登录方法 */;
            try {
                loginSuccessful = DatabaseHelper.login(email,password);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (loginSuccessful) {
                // 设置当前用户的电子邮件地址
                UserSessionUtil.getInstance().setCurrentUserEmail(email);
                try {
                    User user = DatabaseHelper.getUserInfoByEmail(email);
                    UserSessionUtil.getInstance().setCurrentUserPassword(user.getPassword());
                    UserSessionUtil.getInstance().setCurrentUserType(user.getUsername());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                dispose(); // 关闭登录窗口
                EventQueue.invokeLater(() -> {
                    MapDisplay mapDisplay = new MapDisplay(mapLayout);
                    mapDisplay.askForWarehouseCount();
                    mapDisplay.setVisible(true); // 显示地图界面

                });
            } else {
                JOptionPane.showMessageDialog(this, "登录失败");
            }
        });
        buttonPanel.add(loginButton);

        JButton registerButton = new JButton("注册");
        registerButton.addActionListener(e -> {
            RegisterFrame registerFrame = new RegisterFrame();
            registerFrame.setVisible(true);
        });
        buttonPanel.add(registerButton);

        add(buttonPanel, gbc);
    }
}

