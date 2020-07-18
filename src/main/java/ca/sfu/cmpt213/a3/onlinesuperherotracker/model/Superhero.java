package ca.sfu.cmpt213.a3.onlinesuperherotracker.model;

/**
 * Object representation of a superhero.
 * Contains information of the hero's name, height, superpower, and number of civilians they saved.
 */
public class Superhero implements Comparable<Superhero>{
    private long id;
    private String name;
    private double heightInCm;
    private int civilianSaveCount;
    private String superPower;

    public Superhero() {}

    public Superhero(long id, String name, double height, int civiliansSaved, String superpower) {
        this.id = id;
        this.name = name;
        this.heightInCm = height;
        this.civilianSaveCount = civiliansSaved;
        this.superPower = superpower;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHeightInCm() {
        return heightInCm;
    }

    public void setHeightInCm(double heightInCm) {
        this.heightInCm = heightInCm;
    }

    public int getCivilianSaveCount() {
        return civilianSaveCount;
    }

    public void setCivilianSaveCount(int civilianSaveCount) {
        this.civilianSaveCount = civilianSaveCount;
    }

    public String getSuperPower() {
        return superPower;
    }

    public void setSuperPower(String superPower) {
        this.superPower = superPower;
    }

    @Override
    public int compareTo(Superhero o) {
        return Integer.compare(this.getCivilianSaveCount(),o.getCivilianSaveCount());
    }
}
