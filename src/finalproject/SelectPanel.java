
package finalproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SelectPanel extends JPanel {// 選擇飛機面板
    private JLabel[] planeOptions = new JLabel[4];// 用於存儲飛機選項的標籤

    public SelectPanel(CardLayout layout, JPanel parent) {// 建構子，初始化選擇面板
        setLayout(null);// 設定無布局管理器
        setOpaque(false);// 設定為透明

        JLabel title = new JLabel("選擇你的飛機", SwingConstants.CENTER);// 創建標題標籤
        title.setFont(new Font("微軟正黑體", Font.BOLD, 18));// 設定字體
        title.setBounds(100, 10, 200, 30);// 設定標題位置和大小
        title.setForeground(Color.WHITE);// 設定標題顏色為白色
        add(title);// 添加標題到面板

        for (int i = 0; i < 4; i++) {// 創建四個飛機選項
            ImageIcon icon = new ImageIcon(getClass().getResource("/player" + (i + 1) + ".png"));// 加載飛機圖標
            Image scaled = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);// 縮放圖標到50x50像素
            planeOptions[i] = new JLabel(new ImageIcon(scaled));// 創建標籤顯示飛機圖標
            planeOptions[i].setBounds(40 + i * 80, 60, 60, 60);// 設定飛機選項的位置和大小
            final int index = i;// 使用final變量來捕獲當前索引
            planeOptions[i].addMouseListener(new MouseAdapter() {// 添加滑鼠點擊事件監聽器
                public void mouseClicked(MouseEvent e) {// 當飛機選項被點擊時
                    GameData.selectedPlane = index;// 設定選中的飛機索引
                    for (int j = 0; j < planeOptions.length; j++) {//清除其他飛機選項的邊框
                        if (j == GameData.selectedPlane) {// 如果是選中的飛機
                            planeOptions[j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));// 設定黃色邊框
                        } else {// 如果不是選中的飛機
                            planeOptions[j].setBorder(null);// 清除邊框
                        }
                    }
                }
            });
            add(planeOptions[i]);// 添加飛機選項到面板
        }

        JRadioButton easy = new JRadioButton("簡單");// 創建簡單模式選項
        JRadioButton hard = new JRadioButton("困難");// 創建困難模式選項
        easy.setBounds(100, 150, 80, 30);// 設定簡單模式選項的位置和大小
        hard.setBounds(220, 150, 80, 30);// 設定困難模式選項的位置和大小
        easy.setOpaque(false);// 設定簡單模式選項為透明
        hard.setOpaque(false);// 設定困難模式選項為透明
        easy.setForeground(Color.WHITE);// 設定簡單模式選項的文字顏色為白色
        hard.setForeground(Color.WHITE);// 設定困難模式選項的文字顏色為白色
        easy.setSelected(true);// 設定簡單模式為預設選中狀態
        ButtonGroup group = new ButtonGroup();// 創建按鈕組以確保只能選擇一個選項
        group.add(easy);// 添加簡單模式選項到按鈕組
        group.add(hard);// 添加困難模式選項到按鈕組
        add(easy);// 添加簡單模式選項到面板
        add(hard);// 添加困難模式選項到面板

        JButton startBtn = new JButton("Start");// 創建開始遊戲按鈕
        startBtn.setBounds(140, 220, 120, 40);// 設定按鈕位置和大小
        startBtn.addActionListener(e -> {// 當按鈕被點擊時
            GameData.hardMode = hard.isSelected();// 根據選擇的模式設定遊戲難度
            layout.show(parent, "game");// 切換到遊戲面板

            GamePanel gamePanel = (GamePanel) parent.getComponent(3);// 獲取遊戲面板實例
            gamePanel.startGame();// 開始遊戲
            gamePanel.requestFocusInWindow();// 請求遊戲面板獲得焦點
        });
        add(startBtn);// 添加開始遊戲按鈕到面板
    }

    protected void paintComponent(Graphics g) {// 繪製面板內容
        super.paintComponent(g);// 繪製背景
        Image bg = new ImageIcon(getClass().getResource("/background1.jpg")).getImage();// 加載背景圖片
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);// 繪製背景圖片
    }
}
