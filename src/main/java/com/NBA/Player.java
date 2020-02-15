package main.java.com.NBA;

import java.util.Random;

public class Player {
    private String name;
    private String position;
    private int twoShotInRate;
    private int threeShotInRate;
    private int height;
    private int dunkRate;
    private int chooseRate;

    private int score = 0;

    private int choose2Or3Rate;
    private int defensiveValue;

    public Player(String name, String position, int twoShotInRate, int threeShotInRate, int height, int dunkRate, int chooseRate, int choose2Or3Rate, int defensiveValue) {
        this.name = name;
        this.position = position;
        this.twoShotInRate = twoShotInRate;
        this.threeShotInRate = threeShotInRate;
        this.height = height;
        this.dunkRate = dunkRate;
        this.chooseRate = chooseRate;
        this.choose2Or3Rate = choose2Or3Rate;
        this.defensiveValue = defensiveValue;
    }

    public int shoot() {
        return choose2Or3Shoot(choose2Or3Rate);
    }

    private int choose2Or3Shoot(int choose2Or3Rate) {
        int choose2Or3Number = new Random().nextInt(100);
        int dunckNumber = new Random().nextInt(100);
        if (choose2Or3Number < choose2Or3Rate && choose2Or3Number > 0) {

            if (dunckNumber < dunkRate) {
                System.out.println("欧欧欧！！ 快看呐！ " + name + " 高高跃起,重扣一记!");
                score += 2;
                return 2;
            }

            System.out.println(name + " 选择中距离出手");
            if (shotInOrNot(twoShotInRate)) {
                System.out.println(name + " 18英尺外，跳投出手，刷网而进");
                score += 2;
                return 2;
            } else {
                System.out.println(name + " 手感不佳，框都被打歪了");
            }
        } else {
            System.out.println(name + " 24英尺外，果断三分出手，有没有");
            if (shotInOrNot(threeShotInRate)) {
                System.out.println("我的三分剑，是地狱的火焰");
                System.out.println(name + " 今天可是真顺手啊，怎么来，怎么有");
                score += 3;
                return 3;
            } else {
                System.out.println("哐当一声,我都心疼篮筐了!");
            }
        }
        return 0;
    }

    private boolean shotInOrNot(int shotInRate) {
        int shotInRateNumber = new Random().nextInt(100);
        return shotInRateNumber < shotInRate && shotInRateNumber > 0;
    }

    public int getChooseRate() {
        return chooseRate;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setTwoShotInRate(int twoShotInRate) {
        this.twoShotInRate = twoShotInRate;
    }

    public void setThreeShotInRate(int threeShotInRate) {
        this.threeShotInRate = threeShotInRate;
    }

    public String getPosition() {
        return position;
    }

    public int getTwoShotInRate() {
        return twoShotInRate;
    }

    public int getThreeShotInRate() {
        return threeShotInRate;
    }

    public int getDefensiveValue() {
        return defensiveValue;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
