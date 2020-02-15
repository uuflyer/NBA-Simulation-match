package main.java.com.NBA;

public class blueTeam extends Team {
    static private Player PG = new Player("库里", "PG", 48, 44, 191, 15, 30, 60, 15, 20);
    static private Player SG = new Player("汤普森", "SG", 46, 42, 198, 25, 25, 65, 55, 30);
    static private Player SF = new Player("杜兰特", "SF", 49, 38, 208, 45, 35, 78, 62, 55);
    static private Player PF = new Player("格林", "PF", 44, 32, 198, 50, 15, 75, 60, 60);
    static private Player C = new Player("鲁尼", "C", 58, 18, 206, 65, 10, 92, 55, 75);


    public blueTeam() {
        super(PG, SG, SF, PF, C);
        super.setTeamName("Warriors");
    }


}
