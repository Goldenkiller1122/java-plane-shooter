package finalproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenuPanel extends JPanel {// 主選單面板
    public MainMenuPanel(CardLayout layout, JPanel parent) {// 建構子，初始化主選單面板{
        setLayout(null);// 設定無布局管理器
        setOpaque(false);// 設定為透明

        JButton startButton = new JButton("開始遊戲");// 創建開始遊戲按鈕
        startButton.setBounds(140, 220, 120, 40);// 設定按鈕位置和大小
        add(startButton);// 添加按鈕到面板

        startButton.addActionListener(e -> {// 當按鈕被點擊時
            layout.show(parent, "select");// 切換到選擇面板
            parent.getComponent(2).requestFocusInWindow();// 請求選擇面板獲得焦點
        });
    }

    protected void paintComponent(Graphics g) {// 繪製面板內容
        super.paintComponent(g);// 繪製背景
        Image bg = new ImageIcon(getClass().getResource("/background1.jpg")).getImage();// 加載背景圖片
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);// 繪製背景圖片
    }
}