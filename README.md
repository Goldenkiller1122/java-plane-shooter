# 雷霆戰機 Fake

以 Java Swing 製作的 2D 飛機射擊遊戲，為物件導向程式設計課程專案。玩家可以選擇不同戰機與遊戲難度，透過鍵盤控制飛機、閃避敵機並累積分數。

## 專案特色

- 使用 Java Swing 建立遊戲視窗與操作介面
- 透過 CardLayout 切換標題、主選單、戰機選擇及遊戲畫面
- 提供 4 種戰機與普通、困難兩種模式
- 自動發射子彈，並隨機產生不同類型的敵機
- 使用碰撞偵測處理攻擊、受傷與道具拾取
- 設有回血與護盾道具
- 顯示玩家血量、分數及遊戲結束畫面
- 可按 ESC 返回主選單

## 使用技術

- Java
- Java Swing
- Java AWT
- CardLayout
- Timer
- KeyListener、ActionListener
- 物件導向程式設計
- 碰撞偵測與遊戲狀態管理

## 操作方式

| 按鍵 | 功能 |
| --- | --- |
| 左方向鍵 | 向左移動 |
| 右方向鍵 | 向右移動 |
| 1、2、3 | 使用對應欄位中的道具 |
| ESC | 返回主選單 |

遊戲會自動發射子彈，玩家需控制飛機擊落敵機並避開碰撞。

## 執行方式

1. 下載或複製本專案。
2. 使用 Eclipse、IntelliJ IDEA 或其他 Java IDE 開啟專案。
3. 將 `src` 設定為原始碼資料夾。
4. 執行 `src/finalproject/Main.java`。

## 專案結構

```text
src/
├── finalproject/
│   ├── Main.java
│   ├── GameData.java
│   ├── GamePanel.java
│   ├── GameNamePanel.java
│   ├── MainMenuPanel.java
│   ├── SelectPanel.java
│   ├── Enemy.java
│   └── Effect.java
└── 遊戲圖片與特效素材
```

## 學習成果

透過本專案練習 Java 物件導向程式設計、圖形介面建置、鍵盤事件處理、遊戲迴圈及碰撞判斷，也學習將不同畫面與遊戲資料分開管理，提升程式架構與除錯能力。

## 作者

盧昱安  
東吳大學資訊管理學系
