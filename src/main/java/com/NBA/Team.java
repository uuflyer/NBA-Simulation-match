package main.java.com.NBA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Team {
    private Player PG;
    private Player SG;
    private Player SF;
    private Player PF;
    private Player C;
    private List<Player> teamPlayer = new ArrayList<>();
    private List<Integer> numberOfChoose = new ArrayList<>();
    private int totalChooseRate;
    private String teamName;
    public boolean isAttacked = false;
    private int totalScore = 0;

    public Team(Player PG, Player SG, Player SF, Player PF, Player c) {
        this.PG = PG;
        this.SG = SG;
        this.SF = SF;
        this.PF = PF;
        this.C = c;
        init();
    }

    private void init() {
        teamPlayer.add(PG);
        teamPlayer.add(SG);
        teamPlayer.add(SF);
        teamPlayer.add(PF);
        teamPlayer.add(C);

        for (Player player : teamPlayer) {
            totalChooseRate += player.getChooseRate();
        }

        numberOfChoose.add(PG.getChooseRate());
        numberOfChoose.add(PG.getChooseRate() + SG.getChooseRate());
        numberOfChoose.add(PG.getChooseRate() + SG.getChooseRate() + SF.getChooseRate());
        numberOfChoose.add(PG.getChooseRate() + SG.getChooseRate() + SF.getChooseRate() + PF.getChooseRate());
        numberOfChoose.add(PG.getChooseRate() + SG.getChooseRate() + SF.getChooseRate() + PF.getChooseRate() + C.getChooseRate());
    }


    public Player choosePlayerShoot() {
        //根据球员持球概率进行选择哪位球员进攻
        int randomNumber = new Random().nextInt(totalChooseRate);
        for (int i = 1; i < 6; i++) {
            if (randomNumber <= numberOfChoose.get(0)) {
                return teamPlayer.get(0);
            }
            if (randomNumber > numberOfChoose.get(i - 1) && randomNumber <= numberOfChoose.get(i)) {
                return teamPlayer.get(i);
            }
        }
        throw new RuntimeException("错误异常");
    }



    public Player getPG() {
        return PG;
    }

    public Player getSG() {
        return SG;
    }

    public Player getSF() {
        return SF;
    }

    public Player getPF() {
        return PF;
    }

    public Player getC() {
        return C;
    }

    public Player attack() {
        return choosePlayerShoot();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
