package Characters;

import city.cs.engine.*;

public class Chef extends Walker { //create chefs

    private static final Shape chefShape = new PolygonShape(-0.31f, 3.68f, 0.89f, 3.5f, 1.13f, -0.04f, 1.03f, -1.78f, -0.69f, -1.9f, -0.79f, -0.12f, -0.57f, 2.28f);
    private int chefScore = 0;
    private int tomatoCount = 0;
    private boolean dead = true;
    private String team;
    private BodyImage BI;
    private String state;
    private boolean isFrustrated;
    private boolean isAngry;

    public Chef(String team, World w) {
        super(w, chefShape);
        this.team = team;
        addImage(BI);
        state = "none";
    }

    public void addTomatoes() { //add tomato
        tomatoCount = tomatoCount + 10;
    }

    public void shotTomatoes() { //subtract tomato when shot
        tomatoCount = tomatoCount - 1;
        System.out.println(tomatoCount);
    }

    public int getTomatoCount() {
        return tomatoCount;
    }

    public void chefScoreUp() { //add to score
        chefScore++;
    }

    public String getTeam() {
        return team;
    }

    public void setTomatoCount(int tomatoCount) {
        this.tomatoCount = tomatoCount;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean getDead() {
        return dead;
    }

    public void setBI(BodyImage BI) {
        this.removeAllImages();
        this.BI = BI;
        addImage(BI);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setChefScore(int chefScore) {
        this.chefScore = chefScore;
    }

    public int getChefScore() {
        return chefScore;
    }

    public boolean getIsFrustrated() {
        return isFrustrated;
    }

    public boolean getIsAngry() {
        return isAngry;
    }

    public void setIsFrustrated(boolean frustrated) {
        isFrustrated = frustrated;
    }

    public void setIsAngry(boolean angry) {
        isAngry = angry;
    }
}