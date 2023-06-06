package controller;

import dao.LearnersDao;
import model.Conversion;
import model.Learner;
import model.Unit;
import view.ConversionsView;
import javafx.event.ActionEvent;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static java.lang.Math.log10;
import static java.lang.Math.pow;

public class QuizController {
    private ConversionsView cv;
    private LearnersDao lDao;

    public QuizController(final ConversionsView cv, LearnersDao lDao) {
        this.cv = cv;
        this.lDao = lDao;
    }


    public void back(final javafx.event.ActionEvent event, Learner learner, ConversionsView conversionsView) {
        conversionsView.updateRoot(learner, false);
    }


    public void levelSelection(ActionEvent event, Learner activeLearner, String titre, Conversion c, Unit answerUnit) {
        cv.updateRoot(activeLearner, titre, c, answerUnit);
    }


    public void levelSelection(ActionEvent event, Learner activeLearner, String titre, Conversion c, Double answer) {
        cv.updateRoot(activeLearner, titre, c, answer);
    }


    /**
     * Dirige vers updateRoot de la vue avec le message adapté après correction .<br>
     * Appelée par le bouton de validation de Level2View et Level3View
     *
     * @param level int, niveau dans la catégorie
     * @param answer Double, réponse de l'apprenant
     * @param c Conversion, conversion traitée
     * @param category String, catégorie Longueurs, Aires, Contenances ou Volumes
     * @param activeLearner Learner, apprenant connecté
     */
    public void correction(ActionEvent event, int level, Double answer, Conversion c, String category, Learner activeLearner) {
        if (answer == c.getN2()) {
            activeLearner.incrementCorrectAnswer();
            if (activeLearner.getCorrectAnswer() == 5) {
                activeLearner.incrementLevelCategory(category);
            }
            cv.updateRoot(level, activeLearner, category, c, "Bravo");
        } else {
            activeLearner.decreaseCorrectAnswer();
            if (answer / c.getN1() == c.getN1() / c.getN2() && activeLearner.getWrongDirection() < 3) {
                activeLearner.incrementWrongDirection();
                cv.updateRoot(level, activeLearner, category, c, "Sens");
            } else {
                System.out.println(answer / c.getN1());
                System.out.println(c.getN2() / c.getN1());
                System.out.println(activeLearner.getWrongRank());
                if (((answer / c.getN1() > 1 && c.getN2() / c.getN1() > 1) || (answer / c.getN1() < 1 && c.getN2() / c.getN1() < 1))  && activeLearner.getWrongRank() < 3) {
                    activeLearner.incrementWrongRank();
                    cv.updateRoot(level, activeLearner, category, c, "Rangs");
                } else {
                    activeLearner.incrementWrongAnswer();
                    cv.updateRoot(level, activeLearner, category, c, "Beaucoup");
                }
            }
        }
    }


    /**
     * Dirige vers updateRoot de la vue avec le message adapté après correction .<br>
     * Appelée par le bouton de validation de Level1View
     *
     * @param level int, niveau dans la catégorie
     * @param answerUnit Unit, réponse de l'apprenant
     * @param c Conversion, conversion traitée
     * @param category String, catégorie Longueurs, Aires, Contenances ou Volumes
     * @param activeLearner Learner, apprenant connecté
     */
    public void correction(ActionEvent event, int level, Unit answerUnit, Conversion c, String category, Learner activeLearner) {
        if (answerUnit.getUnitString().equals(c.getUnit2().getUnitString())) {
            activeLearner.incrementCorrectAnswer();
            if (activeLearner.getCorrectAnswer() == 5) {
                activeLearner.incrementLevelCategory(category);
            }
            cv.updateRoot(level, activeLearner, category, c, "Bravo");
        } else {
            activeLearner.decreaseCorrectAnswer();
            if (answerUnit.getUnitRank() - c.getUnit1().getUnitRank() == c.getUnit1().getUnitRank() - c.getUnit2().getUnitRank() && activeLearner.getWrongDirection() < 3) {
                activeLearner.incrementWrongDirection();
                cv.updateRoot(level, activeLearner, category, c, "Sens");
            } else {
                if (((answerUnit.getUnitRank() > c.getUnit1().getUnitRank() && c.getUnit2().getUnitRank() > c.getUnit1().getUnitRank()) || (answerUnit.getUnitRank() < c.getUnit1().getUnitRank() && c.getUnit2().getUnitRank() < c.getUnit1().getUnitRank())) && activeLearner.getWrongRank() < 2) {
                    activeLearner.incrementWrongRank();
                    cv.updateRoot(level, activeLearner, category, c, "Rangs");
                } else {
                    activeLearner.incrementWrongAnswer();
                    cv.updateRoot(level, activeLearner, category, c, "Beaucoup");
                }
            }
        }
    }

    public void next(ActionEvent event, String category, Learner activeLearner) {
        if (activeLearner.getCorrectAnswer() == 5) {
            cv.updateRoot(activeLearner, false);    // retour à la page de l'apprenant
        } else {
            cv.updateRoot(activeLearner, category);
        }
    }

    public void leftArrow(ActionEvent event, Learner activeLearner, String category, Conversion c, double answer) {
        String[] parts = c.getNb1().split(",");
        int nombreDecimales;    // nombre de décimales de nb1
        if (parts.length == 2) {    // nb1 a une partie décimale
            nombreDecimales = parts[1].length();
        } else {
            nombreDecimales = 0;
        }
        double quotient = answer / c.getN1();
        double newAnswer = answer / 10;
        if (nombreDecimales - log10(quotient) + 1 > 0) {    //newAnswer a des décimales
            newAnswer = Math.round(newAnswer * (double) pow(10, nombreDecimales - log10(quotient) + 1)) / (double) pow(10, nombreDecimales - log10(quotient) + 1);
        }

        DecimalFormat df = new DecimalFormat("##.######",
                new DecimalFormatSymbols(Locale.FRENCH));
        String answerString = df.format(answer);
        String[] answerParts = answerString.split(",");
        if (answerParts.length == 2) {    //newAnswer a une partie décimale
            if (answerParts[1].length() <= 4) {   //newAnswer a 5 décimales ou moins
                cv.updateRoot(activeLearner, category, c, newAnswer, true, true);
            }
            if (answerParts[1].length() == 5) {    //newAnswer a 6 décimales
                cv.updateRoot(activeLearner, category, c, newAnswer, false, true);
            }
        }
        if (answerParts.length == 1) {    //newAnswer est un entier
            cv.updateRoot(activeLearner, category, c, newAnswer, true, true);
        }
    }


    public void rightArrow(ActionEvent event, Learner activeLearner, String category, Conversion c, double answer) {
        String[] parts = c.getNb1().split(",");
        int nombreDecimales;    // nombre de décimales de nb1
        if (parts.length == 2) {
            nombreDecimales = parts[1].length();
        } else {
            nombreDecimales = 0;
        }
        double quotient = answer / c.getN1();
        double newAnswer = answer * 10;
        if (nombreDecimales - log10(quotient) - 1 > 0) {    //newAnswer a des décimales
            newAnswer = Math.round(newAnswer * (double) pow(10, nombreDecimales - log10(quotient) - 1)) / (double) pow(10, nombreDecimales - log10(quotient) - 1);
        }
        if (nombreDecimales - log10(quotient) - 1 >= -5) {    //newAnswer a des décimales
            cv.updateRoot(activeLearner, category, c, newAnswer, true, true);
        }
        if (nombreDecimales - log10(quotient) - 1 == -6) {    //newAnswer a des décimales
            cv.updateRoot(activeLearner, category, c, newAnswer, true, false);
        }
    }
}