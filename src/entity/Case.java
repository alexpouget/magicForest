package entity;

import ia.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alex on 05/11/2016.
 */
public class Case {

    private boolean wind;
    private boolean hole;
    private boolean shit;
    private boolean monster;
    private boolean portal;

    private List<Edge> cases;
    private List<Variable> variables;

    public Case() {

        Variable hole = new Variable("Hole");
        Variable monster = new Variable("Monster");
        variables = new ArrayList<>();
        variables.add(hole);
        variables.add(monster);
    }

    public boolean isWind() {
        return wind;
    }

    public void setWind(boolean wind) {
        this.wind = wind;
    }

    public boolean isHole() {
        return hole;
    }

    public void setHole(boolean hole) {
        this.hole = hole;
    }

    public boolean isShit() {
        return shit;
    }

    public void setShit(boolean shit) {
        this.shit = shit;
    }

    public boolean isMonster() {
        return monster;
    }

    public void setMonster(boolean monster) {
        this.monster = monster;
    }

    public boolean isPortal() {
        return portal;
    }

    public void setPortal(boolean portal) {
        this.portal = portal;
    }

    public List<Edge> getCases() {
        return cases;
    }

    public void setCases(List<Edge> cases) {
        this.cases = cases;
    }
}
