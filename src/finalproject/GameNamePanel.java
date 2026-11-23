package finalproject;

import javax.swing.*;
import java.awt.*;

public class GameNamePanel extends JPanel {// 遊戲名稱面板
    private int x = 400;// 初始位置，從右側開始
    private Timer timer;// 定時器用於動畫效果

    public GameNamePanel(CardLayout layout, JPanel parent) {// 建構子，初始化面板
        setOpaque(false);// 設定為透明
        setLayout(null);// 設定無布局管理器
    }

    public void startAnimation(Runnable onDone) {// 開始動畫
        x = 400;// 重置位置到右側
        timer = new Timer(20, e -> {// 定時器每20毫秒觸發一次
            x -= 4;// 每次移動4個像素
            if (x < -300) {// 當位置小於-300時，停止動畫
                timer.stop();// 停止定時器
                onDone.run();// 執行完成後的操作
            }
            repaint();// 重繪面板以更新顯示
        });
        timer.start();// 啟動定時器
    }

    protected void paintComponent(Graphics g) {// 繪製面板內容
        super.paintComponent(g);// 繪製背景
        g.setFont(new Font("微軟正黑體", Font.BOLD, 36));// 設定字體
        g.setColor(Color.BLACK);// 設定顏色為黑色
        g.drawString("雷霆戰機", x, 200);// 繪製遊戲名稱
        g.setFont(new Font("微軟正黑體", Font.PLAIN, 20));// 設定字體為普通
        g.setColor(Color.LIGHT_GRAY);// 設定顏色為淺灰色
        g.drawString("fake", x + 160, 200);// 繪製副標題
    }
}