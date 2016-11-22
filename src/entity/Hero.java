package entity;

import utils.Constants;

import java.util.stream.Stream;

/**
 * Created by alex on 05/11/2016.
 */
public class Hero {

    private int posX;
    private int posY;
    private Case actualCase;
    private int knowledge[][];
    private Boolean visited[][];
    private static Hero hero;
    private int life;
    private int shootUsed;
    private int point;

    public static Hero getInstance(){
        if(hero==null){
            hero = new Hero();
        }
        return hero;
    }

    private Hero() {
        life = 0;
        shootUsed = 0;
        point =0;
        this.posY = 0;
        this.posX = 0;
        resetKnowledge(1);

    }

    public void resetKnowledge(int level){
        knowledge = new int[level+2][level+2];
        visited = new Boolean[level+2][level+2];
        for(int i = 0;i<level+2;i++) {
            for (int y = 0; y < level + 2; y++) {
                knowledge[i][y] = Constants.UNKNOW;
                visited[i][y] = new Boolean(false);
            }
        }
    }

    private Hero(int posY, int posX) {
        this.posY = posY;
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public Case getActualCase() {
        return actualCase;
    }

    public void setActualCase(Case actualCase) {
        this.actualCase = actualCase;
    }

    public int[][] getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(int[][] knowledge) {
        this.knowledge = knowledge;
    }

    public Boolean[][] getVisited() {
        return visited;
    }

    public void setVisited(Boolean[][] visited) {
        this.visited = visited;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getShootUsed() {
        return shootUsed;
    }

    public void setShootUsed(int shootUsed) {
        this.shootUsed = shootUsed;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void move(String dir){
        switch (dir){
            case "right":
                posY++;
                break;
            case "left":
                posY--;
                break;
            case "up":
                posX--;
                break;
            case "down":
                posX++;
                break;
        }
    }
}
