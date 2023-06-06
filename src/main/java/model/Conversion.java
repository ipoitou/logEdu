package model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class Conversion {

    private Unit unit1;
    private Unit unit2;
    private String nb1;
    private String nb2;
    private double n1;
    private double n2;
    private int rang;

    public Conversion(String category, int niveau, int nbFalse) {
        String base = "";
        if(category.equals("Longueurs") || category.equals("Contenances")) {
            rang = 1;
            if (category.equals("Longueurs")) {
                base = "m";
            } else {
                base = "L";
            }
        }
        else {
            if(category.equals("Aires")) {
                rang = 2;
                base = "m²";
            }
            else {
                rang = 3;
                base = "m³";
            }
        }
        //List<String> prefixes = Arrays.asList("k", "h", "da", "", "d", "c", "m");
        Random random = new Random();
        int puissance = random.nextInt(5) - 2;  // puissance de 10 entre -4 et 2
        n1 = (random.nextInt(9999) + 1) * (double) pow(10,puissance);
        if (puissance < 0) {
            n1 = Math.round(n1 / (double) pow(10,puissance)) * (double) pow(10,puissance);
        }
        DecimalFormat df = new DecimalFormat("##.######",
                new DecimalFormatSymbols(Locale.FRENCH));
        nb1 = df.format(n1);

        int prefixNum1 = random.nextInt(7);
        unit1 = new Unit(base, prefixNum1);

        int prefixNum2 = random.nextInt(7);
        while (prefixNum2 == prefixNum1 || abs(prefixNum2 - prefixNum1) > 3 || abs(prefixNum2 - prefixNum1) * rang > 6
                                            || (puissance + rang * (prefixNum2 - prefixNum1) > 3)   // pour ne pas avoir un nombre entier de plus 7 chiffres
                                            || (puissance + rang * (prefixNum2 - prefixNum1) < -3)) {  // pour ne pas avoir un nombre décimal avec plus de 3 zéros derrière la virgule
            prefixNum2 = random.nextInt(7);
        }
        n2 = n1 * (double) pow(10, rang * (prefixNum2 - prefixNum1));
        if (puissance + rang * (prefixNum2 - prefixNum1) < 0) {
            n2 = Math.round(n2 / (double) pow(10,puissance + rang * (prefixNum2 - prefixNum1))) * (double) pow(10,puissance + rang * (prefixNum2 - prefixNum1));
        }

        nb2 = df.format(n2);
        unit2 = new Unit(base, prefixNum2);
    }

    public String getNb1() {
        return nb1;
    }

    public Unit getUnit1() {
        return unit1;
    }

    public String getNb2() {
        return nb2;
    }

    public Unit getUnit2() {
        return unit2;
    }

    public double getN1() {
        return n1;
    }

    public double getN2() {
        return n2;
    }

    public int getRang() {
        return rang;
    }
}
