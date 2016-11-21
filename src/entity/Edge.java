package entity;

/**
 * Created by alex on 06/11/2016.
 */
public class Edge {
    private Case from;
    private Case to;
    private String name;

    public Edge() {
    }

    public Edge(Case from, Case to) {
        this.from = from;
        this.to = to;
    }

    public Edge(Case from, Case to, String name) {
        this.from = from;
        this.to = to;
        this.name = name;
    }

    public Case getFrom() {
        return from;
    }

    public void setFrom(Case from) {
        this.from = from;
    }

    public Case getTo() {
        return to;
    }

    public void setTo(Case to) {
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
