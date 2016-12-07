package ia;

import entity.Case;
import entity.Hero;
import gui.Window;
import utils.Constants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static java.lang.Thread.sleep;

/**
 * Created by alex on 05/11/2016.
 */
public class Agent{

    private Hero hero;
    private static Agent agent;
    private List<Rules> rules;
    private int level;
    private Boolean windVariable[][];
    private Boolean smellVariable[][];
    private Boolean holeVariable[][];
    private Boolean monsterVariable[][];
    private Boolean outVariable[][];
    private String dir;
    private HashMap<String,Boolean> mapDir;

    private Agent() {
        instanciateMapDir();
        level = 1;
        hero = Hero.getInstance();
        dir = "right";
        rules = new ArrayList<>();

        //if out actions levelUp
        Rules r = new Rules();
        r.setLabel(1);
        r.get_true().add("OutVariable");
        r.getActions().add("levelUp");
        rules.add(r);
        //if out actions levelUp
        r = new Rules();
        r.setLabel(2);
        r.get_true().add("MonsterVariable");
        r.getActions().add("death");
        rules.add(r);
        //if out actions levelUp
        r = new Rules();
        r.setLabel(3);
        r.get_true().add("HoleVariable");
        r.getActions().add("death");
        rules.add(r);
        //if visited and dir available change dir
        r = new Rules();
        r.setLabel(4);
        r.get_trueDir().add("Visited");
        r.setDirAvailable(true);
        r.getActions().add("changeDir");
        rules.add(r);
        //if pas odeur pas vent et jamais visiter move;
        r = new Rules();
        r.setLabel(5);
        r.get_false().add("SmellVariable");
        r.get_false().add("WindVariable");
        r.get_falseDir().add("Visited");
        r.getActions().add("move");
        rules.add(r);
        //if pas odeur pas vent et visiter move;
        r = new Rules();
        r.setLabel(6);
        r.get_false().add("SmellVariable");
        r.get_false().add("WindVariable");
        r.get_trueDir().add("Visited");
        r.getActions().add("move");
        rules.add(r);
        //if odeur pas vent et jamais visiter shoot and move;
        r = new Rules();
        r.setLabel(7);
        r.get_true().add("SmellVariable");
        r.get_false().add("WindVariable");
        r.get_falseDir().add("Visited");
        r.getActions().add("shoot");
        r.getActions().add("move");
        rules.add(r);
        //if vent et pas visiter move
        r = new Rules();
        r.setLabel(8);
        r.get_true().add("WindVariable");
        r.get_falseDir().add("Visited");
        r.getActions().add("move");
        //if vent et visiter move
        rules.add(r);
        r = new Rules();
        r.setLabel(9);
        r.get_true().add("WindVariable");
        r.getActions().add("move");
        rules.add(r);

        newLevel();
    }

    public static Agent getInstance(){
        if(agent == null){
            agent = new Agent();
        }
        return agent;
    }

    public void findMove(){
        int local = level;
        if (!dirExist(dir)) {
            changeDir();
        }
        addKnow();//add connaissance de la case actuelle

        //check rules ajouter une a une et dans ordre d'interet execute l'action de la premiere regle qui colle
        for (Rules r : rules
                ) {
            if (r.isOK(hero.getPosX(), hero.getPosY(), dir)) {
                for (String s : r.getActions()
                        ) {
                    try {
                        Method m = this.getClass().getMethod(s);
                        m.invoke(this);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }

    public void move(){
        //reset diravailable
        instanciateMapDir();
        hero.move(dir);
        hero.setPoint(hero.getPoint()-1);
    }

    public void levelUp(){
        hero.setPoint(hero.getPoint()+(10*(level+2)));
        level ++;
        hero.setPosX(0);
        hero.setPosY(0);
        hero.resetKnowledge(level);
        newLevel();
        Window.getInstance().newForest();
        Window.getInstance().repaint();
    }

    public void death(){
        System.out.println("death");
        hero.setPoint(hero.getPoint()-(10*(level+2)));
        hero.setLife(hero.getLife()+1);
        hero.setPosX(0);
        hero.setPosY(0);
    }

    public void shoot(){
        switch (dir){
            case "right":
                monsterVariable[hero.getPosX()][hero.getPosY()+1] = false;
                break;
            case "left":

                monsterVariable[hero.getPosX()][hero.getPosY()+1] = false;
                break;
            case "up":
                monsterVariable[hero.getPosX()-1][hero.getPosY()] = false;
                break;
            case "down":
                monsterVariable[hero.getPosX()+1][hero.getPosY()] = false;
                break;
        }
        hero.setPoint(hero.getPoint()-10);
        hero.setShootUsed(hero.getShootUsed()+1);
    }

    public void addKnow(){
        hero.getVisited()[hero.getPosX()][hero.getPosY()] = true;

        if(holeVariable[hero.getPosX()][hero.getPosY()]){
            hero.getKnowledge()[hero.getPosX()][hero.getPosY()] = Constants.HOLE;
        }
        if(monsterVariable[hero.getPosX()][hero.getPosY()]){
            hero.getKnowledge()[hero.getPosX()][hero.getPosY()] = Constants.MONSTER;
        }
    }

    public void newLevel() {
        int nbCase = level + 2;
        windVariable = new Boolean[nbCase][nbCase];
        smellVariable = new Boolean[nbCase][nbCase];
        holeVariable = new Boolean[nbCase][nbCase];
        monsterVariable = new Boolean[nbCase][nbCase];
        outVariable = new Boolean[nbCase][nbCase];

        for (int i = 0; i < nbCase; i++) {
            for (int y = 0; y < nbCase; y++) {
                windVariable[i][y] = new Boolean(false);
                smellVariable[i][y] = new Boolean(false);
                holeVariable[i][y] = new Boolean(false);
                monsterVariable[i][y] = new Boolean(false);
                outVariable[i][y] = new Boolean(false);
            }
        }

        //add Object
        for (int i = 0; i < nbCase; i++) {
            for (int y = 0; y < nbCase; y++) {
                int randomNum = 0 + (int) (Math.random() * 100);
                switch (randomNum % 10) {
                    //hole
                    case 1:
                        addObject(i, y,holeVariable,windVariable);
                        break;
                    //monster
                    case 2:
                        addObject(i, y,monsterVariable,smellVariable);
                        break;
                    //portal
                    case 3:
                        addPortal(i, y);
                        break;
                    default:
                        break;
                }
            }
        }

    }

    private void addPortal(int i, int y) {

        outVariable[i][y] = true;
    }

    private void addObject(int i, int y,Boolean[][] object,Boolean[][] indice) {
        object[i][y] = true;
        if(0<i){
            indice[i-1][y] = true;
        }
        if(level+2>i+1){
            indice[i+1][y] = true;
        }
        if(0<y){
            indice[i][y-1] = true;
        }
        if(level+2>y+1){
            indice[i][y+1] = true;
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Boolean[][] getMonsterVariable() {
        return monsterVariable;
    }

    public void setMonsterVariable(Boolean[][] monsterVariable) {
        this.monsterVariable = monsterVariable;
    }

    public Boolean[][] getWindVariable() {
        return windVariable;
    }

    public void setWindVariable(Boolean[][] windVariable) {
        this.windVariable = windVariable;
    }

    public Boolean[][] getSmellVariable() {
        return smellVariable;
    }

    public void setSmellVariable(Boolean[][] smellVariable) {
        this.smellVariable = smellVariable;
    }

    public Boolean[][] getHoleVariable() {
        return holeVariable;
    }

    public void setHoleVariable(Boolean[][] holeVariable) {
        this.holeVariable = holeVariable;
    }

    public Boolean[][] getOutVariable() {
        return outVariable;
    }

    public void setOutVariable(Boolean[][] outVariable) {
        this.outVariable = outVariable;
    }

    public Hero getHero() {
        return hero;
    }

    public Boolean[][] getVisited() {
        return hero.getVisited();
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public boolean dirAvailable(){
        for (Map.Entry<String, Boolean> entry : mapDir.entrySet())
        {
            //si une dir false reste dir donc true
            if(!entry.getValue()){
               return true;
            }
        }
        return false;
    }

    //remettre a 0 a chaque inference
    public void instanciateMapDir() {
        mapDir = new HashMap<>();
        mapDir.put("down",false);
        mapDir.put("left",false);
        mapDir.put("up",false);
        mapDir.put("right",false);
    }

    //si dir existe
    public boolean dirExist(String dir) {
        switch (dir){
            case "right":
                if(hero.getPosY()+1>level+1){
                    return false;
                }
                break;
            case "left":
                if(hero.getPosY()-1<0){
                    return false;
                }
                break;
            case "up":
                if(hero.getPosX()-1<0){
                    return false;
                }
                break;
            case "down":
                if(hero.getPosX()+1>level+1){
                    return false;
                }
                break;
        }
        return true;
    }

    public void changeDir() {
        for (Map.Entry<String, Boolean> entry : mapDir.entrySet())
        {
            if(!entry.getValue()){
                mapDir.put(entry.getKey(),true);
                if(dirExist(entry.getKey())) {
                    dir = entry.getKey();
                    break;
                }
            }
        }

    }


}
