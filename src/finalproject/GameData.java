package finalproject;

import java.awt.*;
import java.util.ArrayList;

public class GameData {// 遊戲數據類別，存儲遊戲狀態和資源
    public static int hp = 5;// 玩家血量
    public static int score = 0;// 玩家分數
    public static boolean gameOver = false;// 遊戲是否結束
    public static boolean hardMode = false;// 是否為困難模式
    public static int playerX = 180, playerY = 400;// 玩家位置
    public static int selectedPlane = 0;// 選擇的飛機類型，0 = 普通飛機，1 = 強力飛機，2 = 超強飛機
    public static boolean playerHasShield = false;// 玩家是否擁有護盾
    public static long shieldEndTime = 0;// 護盾結束時間

    public static Image[] playerImgs = new Image[4];// 玩家飛機圖片
    public static Image[] enemyImgs = new Image[3];// 敵人圖片
    public static Image bgImg, beamImg, explodeImg;// 背景圖片、子彈圖片和爆炸圖片
    public static Image itemHealImg, healEffectImg;// 回血物品圖片和回血效果圖片
    public static Image itemShieldImg, shieldEffectImg;// 護盾物品圖片和護盾效果圖片

    public static ArrayList<Rectangle> beams = new ArrayList<>();// 玩家子彈列表
    public static ArrayList<Enemy> enemies = new ArrayList<>();// 敵人列表
    public static ArrayList<Rectangle> items = new ArrayList<>();// 物品列表
    public static ArrayList<Integer> itemTypes = new ArrayList<>();// 物品類型列表，0 = 回血物品，1 = 護盾物品
    public static int[] inventory = {0, 0, 0};// 玩家物品庫存，0 = 回血物品數量，1 = 護盾物品數量，2 = 爆炸物品數量
    public static ArrayList<Effect> healEffects = new ArrayList<>();// 回血效果列表
    public static ArrayList<Effect> explosions = new ArrayList<>();// 爆炸效果列表

    ////////////////////////////////////////////////////////////////////////////////////////////
    //數值控制面板
    // 玩家血量上限
    public static final int NORMAL_MAX_HP = 10;// 普通模式下的血量上限
    public static final int HARD_MAX_HP = 5;// 困難模式下的血量上限

    // 普通模式參數
    public static final int NORMAL_ENEMY_SPEED = 3;// 敵人速度
    public static final double NORMAL_HEAL_DROP_RATE = 0.3;// 回血物品掉落率
    public static final double NORMAL_SHIELD_DROP_RATE = 0.2;// 護盾物品掉落率
    public static final int NORMAL_ENEMY_TIMER = 1000;// 敵人生成間隔時間（毫秒）
    public static final int[] NORMAL_ENEMY_HP = {2, 3, 1};// 敵人血量，對應類型 0、1、2 的敵人

    // 困難模式參數
    public static final int HARD_ENEMY_SPEED = 5;// 敵人速度
    public static final double HARD_HEAL_DROP_RATE = 0.2;// 回血物品掉落率
    public static final double HARD_SHIELD_DROP_RATE = 0.1;// 護盾物品掉落率
    public static final int HARD_ENEMY_TIMER = 600;// 敵人生成間隔時間（毫秒）
    public static final int[] HARD_ENEMY_HP = {3, 4, 2};// 敵人血量，對應類型 0、1、2 的敵人
    ////////////////////////////////////////////////////////////////////////////////////////////
}