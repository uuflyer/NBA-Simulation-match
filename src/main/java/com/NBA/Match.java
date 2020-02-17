package main.java.com.NBA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Match {
    private static blueTeam team1 = new blueTeam();
    private static redTeam team2 = new redTeam();
    private static int matchTime = 2880; //比赛时间
    private static double defenseFactor = 0.15;  //防守因子，用防守者的防守值乘以该因子，让被防守者的命中率下降这个值的大小
    private static int reboundsFactor = 8;
    private static int OTtimes = 1;

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
            if (matchTime <= 0) {
                matchTime = 1;
            }
            System.out.println("现在双方比分：" + team1.getTeamName() + " " + team1.getTotalScore() + " VS " + team2.getTeamName() + " " + team2.getTotalScore());
            System.out.println("");

//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            if (matchTime == 1) {
                if (team1.getTotalScore() == team2.getTotalScore()) {
                    if (OTtimes == 1) {
                        System.out.println("激烈的常规时间结束！！  两队现在进入第 " + OTtimes + " 个加时状态");
                    } else {
                        System.out.println("比分你追我赶，即将进入到第 " + OTtimes + " 个加时时间");
                        System.out.println("双方都显得有些疲惫!!   又得再一次进入加时时间!");
                        System.out.println("这种时候更是考验球员的耐力!!   ");
                    }
                    matchTime = 300;  //加时时间
                    OTtimes++;
                    continue;
                }
                System.out.println("激烈的比赛结束了！！  两队得分情况:");
                printResult(team1);
                printResult(team2);

                if (team1.getTotalScore() > team2.getTotalScore()) {
                    System.out.println("");
                    System.out.println("恭喜 " + team1.getTeamName() + " 喜提胜利");
                } else {

                    System.out.println("恭喜 " + team2.getTeamName() + " 喜提胜利");
                    System.out.println("");
                }
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

        effectAttackAndShoot(attackTeam, defenceTeam, attackingPlayer, defensiveValue, normalShoot2); //用防守方的防守值去影响进攻者的投篮属性

        restoreTheShootRate(attackingPlayer, normalShoot2, normalShoot3); //还原进攻者的正常投篮属性

    }

    private static void restoreTheShootRate(Player attackingPlayer, int normalShoot2, int normalShoot3) {
        attackingPlayer.setTwoShotInRate(normalShoot2);
        attackingPlayer.setThreeShotInRate(normalShoot3);
    }

    private static void effectAttackAndShoot(Team attackTeam, Team defenceTeam, Player attackingPlayer, int defensiveValue, int normalShoot2) {
        //通过防守者防守属性 重新计算进攻者的命中率
        attackingPlayer.setThreeShotInRate((int) (normalShoot2 - (defensiveValue * defenseFactor)));
        attackingPlayer.setTwoShotInRate((int) (normalShoot2 - (defensiveValue * defenseFactor)));

        //将每次进攻的得分统计
        int totalScore = attackTeam.getTotalScore();
        int currentScore = attackingPlayer.shoot();

        countShootInRate(attackingPlayer, currentScore);

        totalScore += currentScore;
        attackTeam.setTotalScore(totalScore);

        if (currentScore == 0) {
            rebound(attackTeam, defenceTeam);
        }
    }

    private static void countShootInRate(Player attackingPlayer, int currentScore) {
        if (currentScore != 0) {
            if (currentScore == 2) {
                int currentTimes = attackingPlayer.getShootIn2Times();
                currentTimes++;
                attackingPlayer.setShootIn2Times(currentTimes);
            } else {
                int currentShootIn3Times = attackingPlayer.getShootIn3Times();
                currentShootIn3Times++;
                attackingPlayer.setShootIn3Times(currentShootIn3Times);
            }
        }
    }

    private static void rebound(Team attackTeam, Team defenceTeam) {
        int reboundNumber = new Random().nextInt(10);
        if (reboundNumber < reboundsFactor) {   //2  8规则！ 这是防守方拿下篮板的概率和情况
            Player getReboundsPlayer = getReboundsPlayerAndCountRebounds(defenceTeam);

            //防守方拿下篮板就进行攻防转换
            System.out.println("");
            System.out.println(getReboundsPlayer.getName() + " 奋力保护好后场篮板，运球过半场");
        } else {
            //如果被进攻方拿下篮板，那就嘿嘿嘿!!!
            //再次组织进攻，并修改球队进攻过的标志为false

            Player getReboundsPlayer = getReboundsPlayerAndCountRebounds(attackTeam);
            int currentFrontcourtRbounds = getReboundsPlayer.getFrontcourtRbounds();
            currentFrontcourtRbounds++;
            getReboundsPlayer.setFrontcourtRbounds(currentFrontcourtRbounds);
            System.out.println(getReboundsPlayer.getName() + " 拼命抢下前场板!! 正在有序的组织进攻!");
            attackTeam.isAttacked = false;
        }

    }

    private static Player getReboundsPlayerAndCountRebounds(Team getReboundsTeam) {
        Player getReboundsPlayer = getReboundsTeam.choosePlayerRebounds();  //选一个球员抢下篮板
        int playerRebounds = getReboundsPlayer.getRebounds();
        playerRebounds++;
        int teamRebounds = getReboundsTeam.getTotalReboundsNumber();
        teamRebounds++;
        getReboundsPlayer.setRebounds(playerRebounds);
        getReboundsTeam.setTotalReboundsNumber(teamRebounds);
        return getReboundsPlayer;
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
        Player PG = team.getPG();
        Player SG = team.getSG();
        Player SF = team.getSF();
        Player PF = team.getPF();
        Player C = team.getC();
        List<Player> teamArry = asList(PG, SG, SF, PF, C);

        System.out.println(team.getTeamName() + "队");
        for (Player player : teamArry) {
            System.out.println(player.getName() + " : " + player.getScore() + "分 " + player.getRebounds() + "篮板 " + player.getFrontcourtRbounds() + " 个前场篮板 " + "两分 " + player.getShootIn2Times() + "/" + player.getShoot2Times() + " 三分 " + player.getShootIn3Times() + "/" + player.getShoot3Times());
        }
        System.out.println("");

    }

    private static List<Player> asList(Player... args) {
        List<Player> result = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            result.add(args[i]);
        }
        return result;
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
