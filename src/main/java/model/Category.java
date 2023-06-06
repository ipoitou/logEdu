package model;

public class Category {
    private final String name;
    private int level;
    private int [] conversionsByLevel;

    public Category() {
        name = "";
        level = 0;
        conversionsByLevel = new int[]{0, 0, 0};
    }

    public Category(final String name, int level, int n1, int n2, int n3) {
        this.name = name;
        this.level = level;
        conversionsByLevel = new int[] {n1, n2, n3};
    }

    public String getName() {
        return name;
    }

    public int getLevel () {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setConversionsInLevel(int level, int nbConversions) {
        conversionsByLevel[level - 1] = nbConversions;
    }

    public int[] getNbConversions () {
        return conversionsByLevel;
    }
}
