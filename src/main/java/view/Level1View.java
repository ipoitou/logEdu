package view;

import controller.QuizController;
import dao.LearnersDao;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Conversion;
import model.Learner;
import model.Unit;

import java.util.Arrays;
import java.util.List;


public class Level1View extends LevelView {
    private Unit answerUnit;


    /**
     * Constructeur de la partie centrale de la page du quiz.<br>
     *
     * @param cv ConversionsView
     * @param lDao LearnersDao
     * @param activeLearner Learner, apprenant connecté
     * @param width largeur fenêtre
     * @param category String, catégorie du quiz
     *
     */
    public Level1View(ConversionsView cv, LearnersDao lDao, Learner activeLearner, int width, String category) {
        super(cv, lDao, activeLearner, width, "yellowgreen", category);
        super.setQuiz(quiz(category));
        answerUnit = null;
        this.category = category;
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
     * @param answerUnit Unit
     *
     */
    public Level1View(ConversionsView cv, LearnersDao lDao, Learner activeLearner, int width, String category, Conversion c, Unit answerUnit) {
        super(cv, lDao, activeLearner, width, "yellowgreen", category);
        this.answerUnit = answerUnit;
        super.setQuiz(quiz(category, c, answerUnit));
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
     * @param msg Message, vue avec félicitations ou aide
     *
     */
    public Level1View(ConversionsView cv, LearnersDao lDao, Learner activeLearner, int width, String category, MessageView msg) {
        super(cv, lDao, activeLearner, width, "yellowgreen", category);
        super.setQuiz(msg.getMessage());
        qController = new QuizController(cv, lDao);
        this.category = category;
    }


    /**
     * Génère la partie quiz du niveau 1.<br>
     * conversion et boutons unité
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
                "Pour cela, complète la conversion en choisissant la bonne unité et en validant ton choix.\n");
        l.setFont(Font.font("Dyuthi", 20));
        l.setStyle("-fx-font-weight: bold;\n");
        quizView.getChildren().add(l);

        Conversion c = new Conversion(category, activeLearner.findLevelCategory(category), activeLearner.getWrongAnswer());

        Label conversionLabel = new Label(c.getNb1() + " " + c.getUnit1().getUnitString() + " = " + c.getNb2() + " .....");
        conversionLabel.setFont(Font.font("Dyuthi", 60));
        conversionLabel.setStyle("-fx-font-weight: bold;\n");
        quizView.getChildren().add(conversionLabel);

        quizView.getChildren().add(unitBox(category, c));

        quizView.getChildren().add(validationBox( "lightgrey", answerUnit, c, 1));

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
     * @param answerUnit Unit, réponse de l'apprenant
     *
     * @return VBox, partie quiz
     *
     */
    public VBox quiz(String category, Conversion c, Unit answerUnit) {
        VBox quizView = new VBox(10);
        quizView.setStyle("-fx-alignment: center;");
        Label l = new Label("Fais grandir ton avatar pour atteindre le niveau. \n" +
                "Pour cela, complète la conversion en choisissant la bonne unité et en validant ton choix.\n");
        l.setFont(Font.font("Dyuthi", 20));
        l.setStyle("-fx-font-weight: bold;\n");
        quizView.getChildren().add(l);

        Label conversionLabel = new Label(c.getNb1() + " " + c.getUnit1().getUnitString() + " = " + c.getNb2() + " " + answerUnit.getUnitString());
        conversionLabel.setFont(Font.font("Dyuthi", 60));
        conversionLabel.setStyle("-fx-font-weight: bold;\n");
        quizView.getChildren().add(conversionLabel);

        quizView.getChildren().add(unitBox(category, c));

        quizView.getChildren().add(validationBox("yellowgreen", answerUnit, c, 1));
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
    private HBox unitBox(String category, Conversion c) {
        HBox unitBox = new HBox(10);
        unitBox.setStyle("-fx-alignment: center;");
        List<String> prefixes = Arrays.asList("k", "h", "da", "", "d", "c", "m");
        String unitBase = "";
        if (category.equals("Contenances")) {
            unitBase += "L";
        } else {
            unitBase += "m";
            if (category.equals("Aires")) {
                unitBase += "²";
            }
            if (category.equals("Volumes")) {
                unitBase += "³";
            }
        }
        Unit answerUnit;
        for (int i = 0; i < 7; i++) {
            answerUnit = new Unit(unitBase, i);
            Button unitButton = new Button(answerUnit.getUnitString());
            unitButton.setFont(Font.font("Dyuthi", 20));
            //String finalUnitForButton = unitForButton;
            Unit finalAnswerUnit = answerUnit;
            unitButton.setOnAction(event -> qController.levelSelection(event, activeLearner, category, c, finalAnswerUnit));
            unitBox.getChildren().add(unitButton);
        }
        return unitBox;
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
    public HBox validationBox(String color, Unit answerUnit, Conversion c, int level) {
        HBox validationBox = new HBox();
        validationBox.setStyle("-fx-alignment: center;");
        validationButton = new Button("Valider");
        validationButton.setFont(Font.font("Dyuthi",20));
        validationButton.setStyle("-fx-background-color: " + color);
        validationButton.setOnAction(event -> qController.correction(event, level, answerUnit, c, category, activeLearner));
        validationBox.getChildren().add(this.validationButton);
        return validationBox;
    }
}
