package finalproject;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("雷霆戰機fake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 設定關閉操作為退出應用程式
        frame.setSize(400, 500);// 設定視窗大小為400x500像素
        frame.setLocationRelativeTo(null);// 設定視窗位置為螢幕中央
        frame.setResizable(false);// 設定視窗不可調整大小

        CardLayout cardLayout = new CardLayout();// 使用CardLayout來管理不同的面板
        JPanel cards = new JPanel(cardLayout);// 創建一個面板來容納不同的卡片面板

        GameNamePanel gameNamePanel = new GameNamePanel(cardLayout, cards);// 創建遊戲名稱面板
        MainMenuPanel mainMenuPanel = new MainMenuPanel(cardLayout, cards);// 創建主選單面板
        SelectPanel selectPanel = new SelectPanel(cardLayout, cards);// 創建選擇面板
        GamePanel gamePanel = new GamePanel(cardLayout, cards);// 創建遊戲面板

        cards.add(gameNamePanel, "gameName");// 將遊戲名稱面板添加到卡片面板中
        cards.add(mainMenuPanel, "mainMenu");// 將主選單面板添加到卡片面板中
        cards.add(selectPanel, "select");// 將選擇面板添加到卡片面板中
        cards.add(gamePanel, "game");// 將遊戲面板添加到卡片面板中

        frame.add(cards);// 將卡片面板添加到主視窗中
        frame.setVisible(true);// 設定視窗可見

        gameNamePanel.startAnimation(() -> cardLayout.show(cards, "mainMenu"));// 開始遊戲名稱面板的動畫，動畫結束後切換到主選單面板
    }
}