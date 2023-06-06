package model;

public class Unit {
    private String base;
    private int rank;
    private String prefixChar;
    private String prefixString;


    public Unit(String base, int rank) {
        this.base = base;
        this.rank = rank;
        switch (rank) {
            case 0: prefixChar = "k";
                    prefixString = "kilo";
                    break;
            case 1: prefixChar = "h";
                    prefixString = "hecto";
                    break;
            case 2: prefixChar = "da";
                    prefixString = "déca";
                    break;
            case 4: prefixChar = "d";
                    prefixString = "déci";
                    break;
            case 5: prefixChar = "c";
                    prefixString = "centi";
                    break;
            case 6: prefixChar = "m";
                    prefixString = "milli";
                    break;
            default: prefixChar = "";
                    prefixString = "";
                    break;
        }

    }

    public String getPrefixChar () {
        return prefixChar;
    }



    public String getPrefixString () {
        return prefixString;
    }


    public String getBaseUnit() {
        return base;
    }


    public int getUnitRank () {
        return rank;
    }


    public String getUnitString () {
        return prefixChar + base;
    }
}
