package com.goods.gui;

import com.goods.comm.helper.DatabaseHelper;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {

    private JTextField emailField;
    private JTextField userName;
    private JTextField verificationCodeField;
    private JPasswordField passwordField;

    public RegisterFrame() {
        setTitle("注册");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 4, 4, 4); // 设置组件之间的间距

        gbc.weightx = 0.1; // 设置水平权重
        gbc.weighty = 1.0; // 设置垂直权重

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("邮箱:"), gbc);
        emailField = new JTextField();
        gbc.gridx = 1;
        gbc.weightx = 1.0; // 增加权重，使输入框更宽
        inputPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("用户名:"), gbc);
        userName = new JTextField();
        gbc.gridx = 1;
        inputPanel.add(userName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("验证码:"), gbc);
        verificationCodeField = new JTextField();
        gbc.gridx = 1;
        inputPanel.add(verificationCodeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("密码:"), gbc);
        passwordField = new JPasswordField();
        gbc.gridx = 1;
        inputPanel.add(passwordField, gbc);

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton sendVerificationCodeButton = new JButton("发送验证码");
        sendVerificationCodeButton.addActionListener(e -> {
            DatabaseHelper.sendVerificationCode(emailField.getText());
        });
        buttonPanel.add(sendVerificationCodeButton);

        JButton registerButton = new JButton("注册");
        registerButton.addActionListener(e -> {
            // 注册逻辑
            String email = emailField.getText();
            String userName = this.userName.getText();
            String verificationCode = verificationCodeField.getText();
            String password = new String(passwordField.getPassword());
            if (verifyAndRegister(email, verificationCode, password,userName)) {
                // TODO 将用户信息添加到数据库
                try {
                    DatabaseHelper.register(email,userName,password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(this, "注册成功");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "注册失败");
            }
        });
        buttonPanel.add(registerButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void sendVerificationCode(String email) {
        // 发送验证码到用户邮箱的逻辑
    }

    private boolean verifyAndRegister(String email, String verificationCode, String password,String userName) {
        // 验证验证码并注册用户的逻辑
        return true; // 暂时返回 true
    }
}

