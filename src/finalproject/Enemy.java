package finalproject;

import java.awt.Rectangle;

public class Enemy {//敵人
    public Rectangle rect;// 矩形區域，用於碰撞檢測和繪製
    public int type;// 敵人類型，0 = 普通敵人，1 = 強力敵人，2 = 超強敵人
    public int hp;// 敵人血量

    public Enemy(int x, int y, int type, int hp) {// 建構子，初始化敵人位置、類型和血量
        this.rect = new Rectangle(x, y, 40, 40);// 設定敵人的矩形區域，假設寬高為 40
        this.type = type;// 設定敵人的類型
        this.hp = hp;// 設定敵人的血量
    }
}
