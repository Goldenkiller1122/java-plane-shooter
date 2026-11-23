package finalproject;

public class Effect {
// type 參數說明：0 = 爆炸效果，1 = 回血特效，2 = 護盾特效
    public int type;
    public int x, y;// 效果位置
    public long startTime;// 效果開始時間，用於計時效果持續時間



    public Effect(int x, int y, int type) {// 建構子，初始化效果位置、類型和開始時間
        this.x = x;// 效果的 x 坐標
        this.y = y;// 效果的 y 坐標
        this.type = type;
        this.startTime = System.currentTimeMillis();// 記錄效果開始的時間
    }
}