package view;

import controller.QuizController;
import dao.LearnersDao;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Conversion;
import model.Learner;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import static java.lang.Math.log10;
import static java.lang.Math.pow;

public class Level2View extends LevelView{
    //private Button validationButton;
    private String answer;
    private Double doubleAnswer;



    public Level2View(ConversionsView cv, LearnersDao lDao, Learner activeLearner, int width, String category){
        super(cv, lDao, activeLearner, width, "blue", category);
        super.setQuiz(quiz(category));
        this.category = category;
        answer = "";
    }


    /**
     * Constructeur de la partie centrale de la page du quiz.<br>
     * le choix de l'apprenant est affiché, et le bouton de validation est vert
     *
     * @param cv ConversionsView
     * @param lDao LearnersDao
     * @param activeLearner Learner, apprenant connecté
     * @param width largeur fenêtre
     * @param category String, catégorie du quiz
     * @param c Conversion générée précédemment
     * @param answer String, choix de l'apprenant
     *
     */
    public Level2View(ConversionsView cv, LearnersDao lDao, Learner activeLearner, int width, String category, Conversion c, Double answer) {
        super(cv, lDao, activeLearner, width, "blue", category);
        doubleAnswer = answer;
        super.setQuiz(quiz(category, c, answer));
        qController = new QuizController(cv, lDao);
        this.category = category;
    }


    /**
     * Constructeur de la partie centrale de la page du quiz.<br>
     * message réponse et aide éventuelle
     *
     * @param cv ConversionsView
     * @param lDao LearnersDao
     * @param activeLearner Learner, apprenant connecté
     * @param width largeur fenêtre
     * @param category String, catégorie du quiz
     * @param msg MessageView, vue avec félicitations ou aide
     *
     */
    public Level2View(ConversionsView cv, LearnersDao lDao, Learner activeLearner, int width, String category, MessageView msg) {
        super(cv, lDao, activeLearner, width, "blue", category);
        super.setQuiz(msg.getMessage());
        qController = new QuizController(cv, lDao);
        this.category = category;
    }

    /**
     * Génère la partie quiz du niveau 2.<br>
     *
     * @param category String
     *
     * @return VBox, partie quiz
     *
     */
    @Override
    public VBox quiz(String category) {
        VBox quizView = new VBox(10);
        quizView.setStyle("-fx-alignment: center;");
        Label l = new Label("Fais grandir ton avatar pour atteindre le niveau. \n" +
                                "Pour cela, complète la conversion en choisissant le bon nombre " +
                                "et en validant ton choix.\n");
        l.setFont(Font.font("Dyuthi", 20));
        l.setStyle("-fx-font-weight: bold;\n");
        quizView.getChildren().add(l);

        Conversion c = new Conversion(category, activeLearner.findLevelCategory(category), activeLearner.getWrongAnswer());

        Label conversionLabel = new Label(c.getNb1() + " " + c.getUnit1().getUnitString() + " = " + " .......... " + c.getUnit2().getUnitString());
        conversionLabel.setFont(Font.font("Dyuthi", 60));
        conversionLabel.setStyle("-fx-font-weight: bold;\n");
        quizView.getChildren().add(conversionLabel);

        quizView.getChildren().add(numbersBox(category, c));

        quizView.getChildren().add(validationBox("lightgrey", doubleAnswer, c, 2));

        return quizView;
    }

    @Override
    public VBox quiz(String category, Conversion c, String answer) {
        return null;
    }

    @Override
    public HBox validationBox(String color, String answer, String correct, int level) {
        return null;
    }


    /**
     * Génère la partie quiz du niveau 1.<br>
     *
     * @param category String
     * @param c Conversion, générée précédemment
     * @param answer String, choix de l'apprenant
     *
     * @return VBox, partie quiz
     *
     */
    public VBox quiz(String category, Conversion c, Double answer) {
        VBox quizView = new VBox(10);
        quizView.setStyle("-fx-alignment: center;");
        Label l = new Label("Fais grandir ton avatar pour atteindre le niveau. \n" +
                "Pour cela, complète la conversion en choisissant le bon nombre " +
                "et en validant ton choix.\n");
        l.setFont(Font.font("Dyuthi", 20));
        l.setStyle("-fx-font-weight: bold;\n");
        quizView.getChildren().add(l);
        DecimalFormat df = new DecimalFormat("##.######",
                new DecimalFormatSymbols(Locale.FRENCH));
        Label conversionLabel = new Label(c.getNb1() + " " + c.getUnit1().getUnitString() + " = " + " " + df.format(answer) + " " + c.getUnit2().getUnitString());
        conversionLabel.setFont(Font.font("Dyuthi", 60));
        conversionLabel.setStyle("-fx-font-weight: bold;\n");
        quizView.getChildren().add(conversionLabel);

        quizView.getChildren().add(numbersBox(category, c));

        quizView.getChildren().add(validationBox("yellowgreen", answer, c, 2));

        return quizView;
    }


    /**
     * Génère la partie avec les boutons unité.<br>
     *
     * @param category String
     * @param c Conversion, générée précédemment
     *
     * @return HBox, partie avec les boutons unité
     *
     */
    private HBox numbersBox(String category, Conversion c) {
        HBox numbersBox = new HBox(10);
        numbersBox.setStyle("-fx-alignment: center;");
        List<Double> numbers = new ArrayList<>();
        numbers = numbersToChoice(c);

        Random random = new Random();
        DecimalFormat df = new DecimalFormat("##.######",
                new DecimalFormatSymbols(Locale.FRENCH));
        int index;
        Double numberForButton;
        for (int i = 0; i < 4; i++) {
            index = random.nextInt(4 - i);
            numberForButton = numbers.get(index);
            Button numberButton = new Button(df.format(numberForButton));
            numberButton.setFont(Font.font("Dyuthi", 20));
            Double finalNumberForButton = numberForButton;
            numberButton.setOnAction(event -> qController.levelSelection(event, activeLearner, category, c, finalNumberForButton));
            numbersBox.getChildren().add(numberButton);
            numbers.remove(index);
        }
        return numbersBox;
    }


    public List<Double> numbersToChoice (Conversion c) {
        List<Double> numbers = new ArrayList<>();
        numbers.add(c.getN2());

        String[] parts = c.getNb1().split(",");
        int nombreDecimales;    // nombre de décimales de nb1
        if (parts.length == 2) {
            nombreDecimales = parts[1].length();
        } else {
            nombreDecimales = 0;
        }

        double quotient = c.getN2() / c.getN1();
        double n3 = c.getN1() / quotient;   //décalage dans le mauvais sens;
        if (nombreDecimales + log10(quotient) > 0) {    //n3 a des décimales
            n3 = Math.round(n3 * (double) pow(10, nombreDecimales + log10(quotient))) / (double) pow(10, nombreDecimales + log10(quotient));
        }
        numbers.add(n3);

        double n4;
        double n5;
        if (quotient == 10 || quotient == 0.1) {
            n4 = c.getN2() * quotient;  //décalage de 2 rangs au lieu de 1
            if (nombreDecimales - 2 * log10(quotient) > 0) {    //n4 a des décimales
                n4 = Math.round(n4 * (double) pow(10, nombreDecimales - 2 * log10(quotient))) / (double) pow(10, nombreDecimales - 2 * log10(quotient));
            }
            n5 = n3 / quotient; //décalage de 2 rangs dans le mauvais sens
            if (nombreDecimales + 2 * log10(quotient) > 0) {    //n5 a des décimales
                n5 = Math.round(n5 * (double) pow(10, nombreDecimales + 2 * log10(quotient))) / (double) pow(10, nombreDecimales + 2 * log10(quotient));
            }
        } else {
            n4 = c.getN1() * 10;    //décalage d'un seul rang
            if (nombreDecimales - 1 > 0) {    //n4 a des décimales
                n4 = Math.round(n4 * (double) pow(10, nombreDecimales - 1)) / (double) pow(10, nombreDecimales - 1);
            }
            n5 = c.getN1() / 10;    //décalage d'un seul rang dans le mauvais sens
            if (nombreDecimales + 1 > 0) {    //n5 a des décimales
                n5 = Math.round(n5 * (double) pow(10, nombreDecimales + 1)) / (double) pow(10, nombreDecimales + 1);
            }
        }
        numbers.add(n4);
        numbers.add(n5);
        return numbers;
    }


    /**
     * Génère la partie avec le bouton de validation.<br>
     *
     * @param c Conversion, générée précédemment
     * @param color String, couleur du bouton
     *
     * @return HBox, partie avec le bouton de validation
     *
     */
    public HBox validationBox(String color, Double answer, Conversion c, int level) {
        HBox validationBox = new HBox();
        validationBox.setStyle("-fx-alignment: center;");
        validationButton = new Button("Valider");
        validationButton.setFont(Font.font("Dyuthi",20));
        validationButton.setStyle("-fx-background-color: " + color);
        validationButton.setOnAction(event -> qController.correction(event, level, answer, c, category, activeLearner));
        validationBox.getChildren().add(this.validationButton);
        return validationBox;
    }
}
