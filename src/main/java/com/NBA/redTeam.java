package main.java.com.NBA;

public class redTeam extends Team {

    static private Player PG = new Player("路威廉姆斯", "PG", 42, 35, 185, 10, 25, 75,30);
    static private Player SG = new Player("保罗乔治", "SG", 43, 38, 203, 45, 30, 66,68);
    static private Player SF = new Player("伦纳德", "SF", 49, 38, 201, 45, 35, 75,70);
    static private Player PF = new Player("莫里斯", "PF", 43, 37, 203, 40, 20, 75,50);
    static private Player C = new Player("哈勒尔", "C", 61, 10, 201, 65, 15, 90,60);



    public redTeam() {
        super(PG, SG, SF, PF, C);
        super.setTeamName("Clippers");
    }



}
