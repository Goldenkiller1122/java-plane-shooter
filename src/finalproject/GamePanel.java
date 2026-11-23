
package finalproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {// 遊戲面板
    private boolean playerDead = false;// 玩家是否死亡
    private long playerDeathTime = 0;// 玩家死亡時間

    private Timer gameTimer, beamTimer, enemyTimer;// 定時器，用於遊戲邏輯更新、射擊和敵人生成
    private Random rand = new Random();// 隨機數生成器，用於生成敵人位置和類型
    private CardLayout layout;// 卡片布局，用於切換不同的面板
    private JPanel parent;// 父面板，用於切換到主選單
    private Image gameOverImg, blankImg;// 遊戲結束圖片和空白圖片

    public GamePanel(CardLayout layout, JPanel parent) {// 建構子，初始化遊戲面板
        this.layout = layout;// 設定卡片布局
        this.parent = parent;// 設定父面板

        setFocusable(true);// 設定面板可聚焦
        addKeyListener(this);// 添加鍵盤事件監聽器
        requestFocusInWindow();// 請求面板獲得焦點
        loadImages();// 加載遊戲所需的圖片資源

        int interval = GameData.hardMode ? GameData.HARD_ENEMY_TIMER : GameData.NORMAL_ENEMY_TIMER;// 根據難度模式設定敵人生成間隔時間
        gameTimer = new Timer(20, this);// 遊戲邏輯更新定時器，每20毫秒觸發一次
        beamTimer = new Timer(300, e -> GameData.beams.add(new Rectangle(GameData.playerX + 15, GameData.playerY, 10, 20)));// 射擊定時器，每300毫秒生成一個子彈
        enemyTimer = new Timer(interval, e -> spawnEnemy());// 敵人生成定時器，每隔設定的時間生成一個敵人
    }

    public void loadImages() {// 加載遊戲所需的圖片資源
        for (int i = 0; i < 4; i++)// 加載玩家飛機圖片
            GameData.playerImgs[i] = new ImageIcon(getClass().getResource("/player" + (i + 1) + ".png")).getImage();// 0 = 普通飛機，1 = 強力飛機，2 = 超強飛機，3 = 特殊飛機
        for (int i = 0; i < 3; i++)// 加載敵人圖片
            GameData.enemyImgs[i] = new ImageIcon(getClass().getResource("/enemy" + (i + 1) + ".png")).getImage();//
        GameData.bgImg = new ImageIcon(getClass().getResource("/background1.jpg")).getImage();//背景
        GameData.beamImg = new ImageIcon(getClass().getResource("/beam.png")).getImage();// 子彈圖片
        GameData.explodeImg = new ImageIcon(getClass().getResource("/explode.gif")).getImage();// 爆炸圖片
        GameData.itemHealImg = new ImageIcon(getClass().getResource("/item_heal.png")).getImage();// 回血物品圖片
        GameData.healEffectImg = new ImageIcon(getClass().getResource("/heal_effect.gif")).getImage();// 回血效果圖片
        GameData.itemShieldImg = new ImageIcon(getClass().getResource("/item_shield.png")).getImage();// 護盾物品圖片
        GameData.shieldEffectImg = new ImageIcon(getClass().getResource("/shield_effect.gif")).getImage();// 護盾效果圖片
        gameOverImg = new ImageIcon(getClass().getResource("/gameover.png")).getImage();// 遊戲結束圖片
        blankImg = new ImageIcon(getClass().getResource("/blank.png")).getImage();// 空白圖片，用於顯示遊戲結束時的背景
    }

    public void startGame() {// 開始遊戲，初始化遊戲數據和定時器
        GameData.playerX = 180;// 玩家初始位置
        GameData.playerY = 400;// 玩家初始位置
        playerDead = false;// 玩家是否死亡
        playerDeathTime = 0;// 玩家死亡時間

        GameData.hp = GameData.hardMode ? GameData.HARD_MAX_HP : GameData.NORMAL_MAX_HP;// 玩家血量初始化
        GameData.score = 0;// 玩家分數初始化
        GameData.gameOver = false;// 遊戲是否結束初始化
        GameData.beams.clear();// 清空玩家子彈列表
        GameData.enemies.clear();// 清空敵人列表
        GameData.items.clear();// 清空物品列表
        GameData.itemTypes.clear();// 清空物品類型列表
        Arrays.fill(GameData.inventory, 0);// 清空玩家物品庫存
        GameData.healEffects.clear();// 清空回血效果列表
        GameData.explosions.clear();// 清空爆炸效果列表
        gameTimer.start();// 啟動遊戲邏輯更新定時器
        beamTimer.start();// 啟動射擊定時器
        enemyTimer.start();// 啟動敵人生成定時器
    }

    public void returnToMenu() {// 返回主選單，停止所有定時器並切換到主選單面板
        gameTimer.stop();// 停止遊戲邏輯更新定時器
        beamTimer.stop();// 停止射擊定時器
        enemyTimer.stop();// 停止敵人生成定時器
        layout.show(parent, "mainMenu");// 切換到主選單面板
    }

    public void spawnEnemy() {// 生成一個新的敵人
        int x = rand.nextInt(360);// 隨機生成敵人初始位置的 x 坐標
        int type = rand.nextInt(3);// 隨機生成敵人類型，0 = 普通敵人，1 = 強力敵人，2 = 超強敵人
        int[] hpArr = GameData.hardMode ? GameData.HARD_ENEMY_HP : GameData.NORMAL_ENEMY_HP;// 根據難度模式選擇敵人血量數組
        GameData.enemies.add(new Enemy(x, 0, type, hpArr[type]));// 創建新的敵人並添加到敵人列表中
    }

    public void paintComponent(Graphics g) {// 繪製遊戲面板內容
        super.paintComponent(g);// 繪製背景
        g.drawImage(GameData.bgImg, 0, 0, getWidth(), getHeight(), this);// 繪製背景圖片

        if (GameData.gameOver) {// 如果遊戲結束，顯示遊戲結束畫面
            g.drawImage(blankImg, 80, 100, 240, 100, this);// 繪製空白背景
            g.drawImage(gameOverImg, 110, 110, 180, 40, this);// 繪製遊戲結束圖片
            g.setFont(new Font("Arial", Font.BOLD, 20));// 設定字體
            g.setColor(Color.WHITE);// 設定文字顏色為白色
            g.drawString("Score: " + GameData.score, 150, 160);// 繪製分數
            g.drawString("HP: " + GameData.hp, 10, 20);// 繪製玩家血量
            return;// 如果遊戲結束，直接返回，不繪製其他內容
        }

        for (Rectangle b : GameData.beams)// 繪製玩家子彈
            g.drawImage(GameData.beamImg, b.x, b.y, b.width, b.height, this);// 繪製子彈圖片

        for (Enemy enemy : GameData.enemies)// 繪製敵人
            g.drawImage(GameData.enemyImgs[enemy.type], enemy.rect.x, enemy.rect.y, enemy.rect.width, enemy.rect.height, this);// 繪製敵人圖片

        for (int i = 0; i < GameData.items.size(); i++) {// 繪製物品
            Rectangle it = GameData.items.get(i);// 獲取物品矩形
            int type = GameData.itemTypes.get(i);// 獲取物品類型
            Image img = (type == 0) ? GameData.itemHealImg : GameData.itemShieldImg;// 根據物品類型選擇圖片
            g.drawImage(img, it.x, it.y, 30, 30, this);// 繪製物品圖片
        }

        for (Effect eff : GameData.healEffects)// 繪製回血效果
            g.drawImage(GameData.healEffectImg, eff.x, eff.y, 50, 50, this);// 繪製回血效果圖片

        for (Effect boom : GameData.explosions)// 繪製爆炸效果
            g.drawImage(GameData.explodeImg, boom.x, boom.y, 40, 40, this);// 繪製爆炸圖片

        g.setColor(Color.WHITE);// 設定文字顏色為白色
        g.drawString("HP: " + GameData.hp, 10, 20);// 繪製玩家血量
        for (int i = 0; i < 3; i++) {// 繪製玩家物品庫存
            g.setColor(Color.BLUE);// 設定庫存框的顏色為藍色
            g.drawRect(10 + i * 40, 400, 30, 30);// 
            if (GameData.inventory[i] == 1)// 如果有回血物品
                g.drawImage(GameData.itemHealImg, 10 + i * 40, 400, 30, 30, this);// 繪製回血物品圖片
            else if (GameData.inventory[i] == 2)// 如果有護盾物品
                g.drawImage(GameData.itemShieldImg, 10 + i * 40, 400, 30, 30, this);// 繪製護盾物品圖片
        }

        if (GameData.playerHasShield)// 如果玩家有護盾
            g.drawImage(GameData.shieldEffectImg, GameData.playerX - 10, GameData.playerY - 10, 60, 60, this);// 繪製護盾效果圖片

        g.drawImage(GameData.playerImgs[GameData.selectedPlane], GameData.playerX, GameData.playerY, 40, 40, this);// 繪製玩家飛機圖片
        g.drawString("Score: " + GameData.score, 300, 20);// 繪製玩家分數
    }

    public void actionPerformed(ActionEvent e) {// 處理遊戲邏輯更新
        move();// 移動遊戲元素
        repaint();// 重繪遊戲面板
    }

    public void move() {// 移動遊戲元素
        int enemySpeed = GameData.hardMode ? GameData.HARD_ENEMY_SPEED : GameData.NORMAL_ENEMY_SPEED;// 根據難度模式設定敵人移動速度
        double healRate = GameData.hardMode ? GameData.HARD_HEAL_DROP_RATE : GameData.NORMAL_HEAL_DROP_RATE;// 根據難度模式設定回血物品掉落率
        double shieldRate = GameData.hardMode ? GameData.HARD_SHIELD_DROP_RATE : GameData.NORMAL_SHIELD_DROP_RATE;// 根據難度模式設定護盾物品掉落率

        GameData.beams.forEach(b -> b.y -= 5);// 移動玩家子彈向上
        GameData.beams.removeIf(b -> b.y < 0);// 移除超出屏幕上方的子彈

        for (int i = 0; i < GameData.beams.size(); i++) {// 檢查子彈是否擊中敵人
            Rectangle beam = GameData.beams.get(i);// 獲取當前子彈矩形
            for (int j = 0; j < GameData.enemies.size(); j++) {// 遍歷所有敵人
                Enemy enemy = GameData.enemies.get(j);// 獲取當前敵人
                if (beam.intersects(enemy.rect)) {// 如果子彈與敵人矩形相交
                    enemy.hp--;// 敵人血量減少
                    GameData.beams.remove(i);// 移除擊中的子彈
                    i--;// 調整索引以避免跳過下一個子彈
                    if (enemy.hp <= 0) {// 如果敵人血量為0或以下
                        GameData.explosions.add(new Effect(enemy.rect.x, enemy.rect.y, 0)); // 0 = 爆炸動畫);// 添加爆炸效果
                        GameData.enemies.remove(j);// 移除敵人
                        GameData.score++;// 增加玩家分數
                        if (Math.random() < healRate) {// 如果隨機生成回血物品
                            GameData.items.add(new Rectangle(enemy.rect.x, enemy.rect.y, 30, 30));// 添加回血物品到物品列表
                            GameData.itemTypes.add(0);// 添加物品類型為回血物品
                        } else if (Math.random() < shieldRate) {// 如果隨機生成護盾物品
                            GameData.items.add(new Rectangle(enemy.rect.x, enemy.rect.y, 30, 30));// 添加護盾物品到物品列表
                            GameData.itemTypes.add(1);// 添加物品類型為護盾物品
                        }
                    }
                    break;// 跳出內層循環，繼續檢查下一個子彈
                }
            }
        }

        for (Enemy e : GameData.enemies)// 移動敵人向下
            e.rect.y += enemySpeed;// 敵人矩形向下移動

        GameData.enemies.removeIf(e -> e.rect.y > 500);// 移除超出屏幕下方的敵人
        for (Rectangle it : GameData.items)// 移動物品向下
            it.y += 2;// 物品矩形向下移動
        GameData.items.removeIf(it -> it.y > 500);// 移除超出屏幕下方的物品
        while (GameData.itemTypes.size() > GameData.items.size())// 確保物品類型列表與物品列表大小一致
            GameData.itemTypes.remove(GameData.itemTypes.size() - 1);// 移除多餘的物品類型

        Rectangle playerRect = new Rectangle(GameData.playerX, GameData.playerY, 40, 40);// 玩家矩形，用於碰撞檢測
        for (int i = 0; i < GameData.items.size(); i++) {// 檢查玩家是否拾取物品
            if (GameData.items.get(i).intersects(playerRect)) {// 如果物品與玩家矩形相交
                int type = GameData.itemTypes.get(i);// 獲取物品類型
                for (int j = 0; j < 3; j++) {// 遍歷玩家物品庫存
                    if (GameData.inventory[j] == 0) {// 如果庫存中沒有物品
                        GameData.inventory[j] = (type == 0) ? 1 : 2;// 如果是回血物品，設定庫存為1；如果是護盾物品，設定庫存為2
                        break;// 跳出循環，避免重複添加物品
                    }
                }
                GameData.items.remove(i);// 移除拾取的物品
                GameData.itemTypes.remove(i);// 移除對應的物品類型
                i--;// 調整索引以避免跳過下一個物品
            }
        }

        GameData.healEffects.removeIf(e -> System.currentTimeMillis() - e.startTime > 2000);// 移除超過2秒的回血效果
        GameData.explosions.removeIf(e -> System.currentTimeMillis() - e.startTime > 500);// 移除超過0.5秒的爆炸效果

        if (GameData.playerHasShield && System.currentTimeMillis() > GameData.shieldEndTime) {// 如果護盾已經過期
            GameData.playerHasShield = false;// 移除護盾效果
        }

        for (int i = 0; i < GameData.enemies.size(); i++) {// 檢查敵人是否碰撞到玩家
            Enemy e = GameData.enemies.get(i);// 獲取當前敵人
            if (e.rect.intersects(playerRect)) {// 如果敵人矩形與玩家矩形相交
                if (GameData.playerHasShield) {// 如果玩家有護盾
                    GameData.enemies.remove(i);// 移除敵人
                    i--;// 調整索引以避免跳過下一個敵人
                    continue;// 繼續檢查下一個敵人
                }
                GameData.hp--;// 玩家血量減少
                GameData.explosions.add(new Effect(e.rect.x, e.rect.y, 0)); // 0 = 爆炸動畫);// 添加爆炸效果
                GameData.enemies.remove(i);// 移除敵人
                i--;// 調整索引以避免跳過下一個敵人
                if (GameData.hp <= 0 && !playerDead) {// 如果玩家血量為0或以下且尚未死亡
                    GameData.hp = 0;// 確保玩家血量不小於0
                    playerDead = true;// 設定玩家死亡狀態
                    playerDeathTime = System.currentTimeMillis();// 記錄玩家死亡時間
                    GameData.explosions.add(new Effect(GameData.playerX, GameData.playerY, 0)); // 0 = 爆炸動畫);// 添加玩家死亡爆炸效果
                }
            }
        }

        if (playerDead && !GameData.gameOver && System.currentTimeMillis() - playerDeathTime > 300) {// 如果玩家死亡且遊戲尚未結束，且死亡時間超過300毫秒
            GameData.gameOver = true;// 設定遊戲結束狀態
            gameTimer.stop();// 停止遊戲邏輯更新定時器
            beamTimer.stop();// 停止射擊定時器
            enemyTimer.stop();// 停止敵人生成定時器
        }
    }

    public void keyPressed(KeyEvent e) {// 處理鍵盤按下事件
        int maxHp = GameData.hardMode ? GameData.HARD_MAX_HP : GameData.NORMAL_MAX_HP;// 根據難度模式設定玩家血量上限
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) returnToMenu();// 如果按下ESC鍵，返回主選單
        if (e.getKeyCode() == KeyEvent.VK_LEFT && GameData.playerX > 0) GameData.playerX -= 10;// 如果按下左鍵且玩家位置在屏幕左側，則向左移動
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && GameData.playerX < 360) GameData.playerX += 10;// 如果按下右鍵且玩家位置在屏幕右側，則向右移動

        if (e.getKeyCode() >= KeyEvent.VK_1 && e.getKeyCode() <= KeyEvent.VK_3) {// 如果按下1、2或3鍵
            int index = e.getKeyCode() - KeyEvent.VK_1;// 計算物品庫存索引，0 = 回血物品，1 = 護盾物品，2 = 爆炸物品

            if (GameData.inventory[index] == 1 && GameData.hp < maxHp) {// 如果有回血物品且玩家血量小於上限
                GameData.hp++;// 玩家血量增加
                GameData.inventory[index] = 0;// 使用後將物品庫存清空
                GameData.healEffects.add(new Effect(GameData.playerX - 5, GameData.playerY - 5, 0)); // 0 = 爆炸動畫);// 添加回血效果
            } else if (GameData.inventory[index] == 2) {// 如果有護盾物品
                GameData.playerHasShield = true;// 設定玩家擁有護盾
                GameData.shieldEndTime = System.currentTimeMillis() + 5000;// 設定護盾持續時間為5秒
                GameData.inventory[index] = 0;// 使用後將物品庫存清空
            }
        }
    }

    public void keyReleased(KeyEvent e) {}// 處理鍵盤釋放事件
    public void keyTyped(KeyEvent e) {}// 處理鍵盤輸入事件，這裡不需要實現任何功能
}
