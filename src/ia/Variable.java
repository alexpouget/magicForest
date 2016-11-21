package ia;

/**
 * Created by alex on 06/11/2016.
 */
public class Variable {

    private String name;
    private double coef;

    public Variable() {
    }

    public Variable(String name) {
        this.name = name;
        this.coef = 0;
    }

    public Variable(double coef, String name) {
        this.coef = coef;
        this.name = name;
    }

    public double getCoef() {
        return coef;
    }

    public void setCoef(double coef) {
        this.coef = coef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
