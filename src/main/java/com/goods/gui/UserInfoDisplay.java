package com.goods.gui;

import com.goods.comm.helper.DatabaseHelper;
import com.goods.utils.PasswordUtil;
import com.goods.utils.SimpleDocumentUtil;
import com.goods.utils.UserSessionUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class UserInfoDisplay extends JFrame {
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JButton changePasswordButton;
    private BufferedImage mapImage;
    public UserInfoDisplay() {
        setTitle("个人信息");
        setSize(500, 400);  // 设置窗口大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 关闭窗口时只关闭当前窗口
        setUpUI();
    }

    private void setUpUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 用户信息显示部分
        String currentUserEmail = UserSessionUtil.getInstance().getCurrentUserEmail();
        currentUserEmail = currentUserEmail != null ? currentUserEmail : "未登录";
        String currentUserType = UserSessionUtil.getInstance().getCurrentUserType();
        currentUserType = currentUserType != null ? currentUserType : "未登录";

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
//        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 邮箱标签
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel userInfoLabel = new JLabel("邮箱: " + currentUserEmail);
        add(userInfoLabel, gbc);

        // 用户名标签
        gbc.gridy = 1;
        JLabel userTypeLabel = new JLabel("用户名: " + currentUserType);
        add(userTypeLabel, gbc);

        // 尝试从网络加载并调整用户头像大小
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel userAvatarLabel = new JLabel();
        try {
            Image avatarImage = ImageIO.read(new URL("https://picdm.sunbangyan.cn/2023/12/15/00366fb615e197e70756898d4fbf2ea7.jpeg"));
            Image scaledImage = avatarImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon avatarIcon = new ImageIcon(scaledImage);
            userAvatarLabel.setIcon(avatarIcon);
        } catch (Exception e) {
            userAvatarLabel.setText("头像加载失败");
        }
        add(userAvatarLabel, gbc);

        // 添加原密码输入框
//        gbc.gridx = 0; // 第一列
//        gbc.gridy = 2; // 第三行
//        gbc.gridwidth = 1; // 一列宽
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        add(new JLabel("原密码:"), gbc);
//        gbc.gridx = 1; // 第二列
//        oldPasswordField = new JPasswordField(10);
//        add(oldPasswordField, gbc);

        // 添加新密码输入框
        gbc.gridx = 0;
        gbc.gridy = 2; // 设置在第三行
        gbc.gridwidth = 2; // 占据两列宽度
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("新密码:"), gbc);

        gbc.gridy = 3; // 设置在第四行
        newPasswordField = new JPasswordField(10);
        add(newPasswordField, gbc);

        // 密码修改按钮
        gbc.gridy = 4; // 设置在第五行
        gbc.gridwidth = 2; // 占据两列宽度
        changePasswordButton = new JButton("修改密码");
        changePasswordButton.setEnabled(false); // 初始时不可用
        add(changePasswordButton, gbc);

        // 添加文本输入监听器以激活按钮
        DocumentListener passwordFieldListener = createPasswordListener();
//        oldPasswordField.getDocument().addDocumentListener(passwordFieldListener);
        newPasswordField.getDocument().addDocumentListener(passwordFieldListener);

        // 添加修改密码的事件监听器
        String finalCurrentUserEmail = currentUserEmail;
        String finalCurrentUserEmail1 = currentUserEmail;
        changePasswordButton.addActionListener(e -> {
            String newPassword = new String(newPasswordField.getPassword());
            String newHashedPassword = PasswordUtil.hashPassword(newPassword);
            boolean isSuccess = DatabaseHelper.updatePasswordByEmail(finalCurrentUserEmail1, newHashedPassword);
            if (isSuccess) {
                JOptionPane.showMessageDialog(this, "密码修改成功");
            } else {
                JOptionPane.showMessageDialog(this, "密码修改失败");
            }
        });
    }

    // 创建文档监听器的辅助方法
    private DocumentListener createPasswordListener() {
        return new DocumentListener() {
            public void update() {
                boolean enabled = newPasswordField.getPassword().length > 0;
                changePasswordButton.setEnabled(enabled);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
        };
    }
    // 其他方法...
}
