package main.java.com.NBA;

import java.util.Random;

public class Match {
    private static blueTeam team1 = new blueTeam();
    private static redTeam team2 = new redTeam();
    private static int matchTime = 2880; //比赛时间
    private static double defenseFactor = 0.15;  //防守因子，用防守者的防守值乘以该因子，让被防守者的命中率下降这个值的大小

    public static void main(String[] args) {
        System.out.println("下面请看 " + team1.getTeamName() + " VS " + team2.getTeamName() + " 带来的比赛");
        System.out.println("比赛正式开始");

        chooseFirstTeam(); //相当于跳球部分

        while (matchTime-- > 0) {

            int attackTime = new Random().nextInt(16) + 8; //随机生成每次进攻时间,最低8秒，最多24秒
            if (team1.isAttacked) {   //判断1队是否已经进攻过了,进攻过了为true, 则2队进攻
                matchTime -= attackTime;

                attackOneTime(team2, team1);  //team2为进攻方，1为防守方

                team2.isAttacked = true;
                team1.isAttacked = false;
                System.out.println("");
            } else {
                matchTime -= attackTime;

                attackOneTime(team1, team2); //防守和进攻进行转换

                team2.isAttacked = false;
                team1.isAttacked = true;
                System.out.println("");
            }
            if (matchTime < 0) {
                matchTime = 1;
            }
            System.out.println("现在双方比分：" + team1.getTeamName() + " " + team1.getTotalScore() + " VS " + team2.getTeamName() + " " + team2.getTotalScore());
            System.out.println("");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (matchTime == 1) {
                if (team1.getTotalScore() == team2.getTotalScore()) {
                    System.out.println("激烈的常规时间结束！！  两队现在进入加时状态");
                    matchTime = 300;  //加时时间
                    continue;
                }
                System.out.println("激烈的比赛结束了！！  两队得分情况:");
                printResult(team1);
                printResult(team2);
                matchTime = 0;
            }
        }
    }


    private static void attackOneTime(Team attacker, Team defender) {
        Player attackingPlayer = attacker.attack();

        String position = attackingPlayer.getPosition();  //拿到正在进攻的球员位置

        defenceTheAttacking(attacker, attackingPlayer, position, defender);  //通过对应位置防守者的防守属性去影响进攻球员的命中率
    }

    private static void defenceTheAttacking(Team attackTeam, Player attackingPlayer, String position, Team defenceTeam) {
        int defensiveValue = judgeThePosition(position, defenceTeam);
        int normalShoot2 = attackingPlayer.getTwoShotInRate();
        int normalShoot3 = attackingPlayer.getThreeShotInRate();

        effectAttackAndShoot(attackTeam, attackingPlayer, defensiveValue, normalShoot2); //用防守方的防守值去影响进攻者的投篮属性

        restoreTheShootRate(attackingPlayer, normalShoot2, normalShoot3); //还原进攻者的正常投篮属性

    }

    private static void restoreTheShootRate(Player attackingPlayer, int normalShoot2, int normalShoot3) {
        attackingPlayer.setTwoShotInRate(normalShoot2);
        attackingPlayer.setThreeShotInRate(normalShoot3);
    }

    private static void effectAttackAndShoot(Team attackTeam, Player attackingPlayer, int defensiveValue, int normalShoot2) {
        //通过防守者防守属性 重新计算进攻者的命中率
        attackingPlayer.setThreeShotInRate((int) (normalShoot2 - (defensiveValue * defenseFactor)));
        attackingPlayer.setTwoShotInRate((int) (normalShoot2 - (defensiveValue * defenseFactor)));

        //将每次进攻的得分统计
        int totalScore = attackTeam.getTotalScore();
        totalScore += attackingPlayer.shoot();
        attackTeam.setTotalScore(totalScore);
    }

    private static int judgeThePosition(String position, Team defenceTeam) {
        switch (position) {
            case "PG":
                return defenceTeam.getPG().getDefensiveValue();
            case "SG":
                return defenceTeam.getSG().getDefensiveValue();
            case "SF":
                return defenceTeam.getSF().getDefensiveValue();
            case "PF":
                return defenceTeam.getPF().getDefensiveValue();
            case "C":
                return defenceTeam.getC().getDefensiveValue();
        }
        return 0;
    }

    private static void printResult(Team team) {
        System.out.println(team.getTeamName() + "队");
        System.out.println(team.getPG().getName() + " : " + team.getPG().getScore() + "分");
        System.out.println(team.getSG().getName() + " : " + team.getSG().getScore() + "分");
        System.out.println(team.getSF().getName() + " : " + team.getSF().getScore() + "分");
        System.out.println(team.getPF().getName() + " : " + team.getPF().getScore() + "分");
        System.out.println(team.getC().getName() + " : " + team.getC().getScore() + "分");
    }


    private static void chooseFirstTeam() {
        int i = new Random().nextInt(2);
        Team firstAttackTeam;

        if (i == 0) {
            firstAttackTeam = team1;
        } else {
            firstAttackTeam = team2;
        }

        System.out.println(firstAttackTeam.getTeamName() + "获得球权");
        firstAttackTeam.attack();
        firstAttackTeam.isAttacked = true;

    }
}
