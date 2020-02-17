package main.java.com.NBA;

public class blueTeam extends Team {
    static private Player PG = new Player("詹姆斯", "PG", 50, 34, 206, 45, 40, 78, 50, 40);
    //    static private Player SG = new Player("三炮徐", "SG", 38, 32, 198, 40, 25, 75, 50, 40);
    static private Player SF = new Player("库兹马", "SF", 45, 33, 206, 30, 18, 72, 30, 30);
    static private Player PF = new Player("戴维斯", "PF", 52, 32, 208, 60, 35, 80, 70, 65);
    static private Player C = new Player("霍华德", "C", 58, 13, 208, 60, 18, 99, 50, 70);


    static private Player SG = new Player("丹尼格林", "SG", 42, 40, 198, 18, 28, 60, 55, 20);

    public blueTeam() {
        super(PG, SG, SF, PF, C);
        super.setTeamName("Lakers");
    }


}
