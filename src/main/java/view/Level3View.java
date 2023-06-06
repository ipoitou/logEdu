package view;

import controller.QuizController;
import dao.LearnersDao;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Conversion;
import model.Learner;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class Level3View extends LevelView{


    double doubleAnswer;



    public Level3View(ConversionsView cv, LearnersDao lDao, Learner activeLearner, int width, String category){
        super(cv, lDao, activeLearner, width, "red", category);
        super.setQuiz(quiz(category));
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
     * @param doubleAnswer String, choix de l'apprenant
     * @param leftOK boolean, indique qu'on peut déplacer la virgule à gauche
     * @param rightOK boolean, indique qu'on peut déplacer la virgule à droite
     *
     */
    public Level3View(ConversionsView cv, LearnersDao lDao, Learner activeLearner, int width, String category, Conversion c, double doubleAnswer, boolean leftOK, boolean rightOK) {
        super(cv, lDao, activeLearner, width, "red", category);
        this.doubleAnswer = doubleAnswer;
        super.setQuiz(quiz(category, c, doubleAnswer, leftOK, rightOK));
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
     * @param msg MessageView, avec félicitations ou aide
     *
     */
    public Level3View(ConversionsView cv, LearnersDao lDao, Learner activeLearner, int width, String category, MessageView msg) {
        super(cv, lDao, activeLearner, width, "red", category);
        super.setQuiz(msg.getMessage());
        qController = new QuizController(cv, lDao);
        this.category = category;
    }


    /**
     * Génère la partie quiz du niveau 3.<br>
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
                                "Pour cela, complète la conversion en déplaçant la " +
                                "virgule\nà l'aide des boutons et en validant ton choix.\n");
        l.setFont(Font.font("Dyuthi", 20));
        l.setStyle("-fx-font-weight: bold;\n");
        quizView.getChildren().add(l);

        Conversion c = new Conversion(category, activeLearner.findLevelCategory(category), activeLearner.getWrongAnswer());
        doubleAnswer = c.getN1();


        HBox conversionBox = new HBox();
        conversionBox.setStyle("-fx-alignment: center;");
        VBox leftPartConversion = new VBox(10);
        Label conversionLabel1 = new Label(c.getNb1() + " " + c.getUnit1().getUnitString() + " = ");
        conversionLabel1.setFont(Font.font("Dyuthi", 60));
        conversionLabel1.setStyle("-fx-font-weight: bold;\n");
        leftPartConversion.getChildren().add(conversionLabel1);


        VBox centerPartConversion = new VBox(10);
        Label conversionLabel2 = new Label(c.getNb1() + " ");
        conversionLabel2.setFont(Font.font("Dyuthi", 60));
        conversionLabel2.setStyle("-fx-font-weight: bold;\n");
        centerPartConversion.getChildren().add(conversionLabel2);

        centerPartConversion.getChildren().add(arrowBox(category, c, true, true));
        centerPartConversion.getChildren().add(validationBox("lightgrey", c.getN1(), c, 3));

        VBox rightPartConversion = new VBox(10);
        Label conversionLabel3 = new Label(c.getUnit2().getUnitString());
        conversionLabel3.setFont(Font.font("Dyuthi", 60));
        conversionLabel3.setStyle("-fx-font-weight: bold;\n");
        rightPartConversion.getChildren().add(conversionLabel3);

        conversionBox.getChildren().addAll(leftPartConversion, centerPartConversion, rightPartConversion);

        quizView.getChildren().add(conversionBox);
        return quizView;
    }


    /**
     * Génère la partie quiz du niveau 3.<br>
     *
     * @param category String
     *
     * @return VBox, partie quiz
     *
     */

    public VBox quiz(String category, Conversion c, double answer, boolean leftOK, boolean rightOK) {
        VBox quizView = new VBox(10);
        quizView.setStyle("-fx-alignment: center;");
        Label l = new Label("Fais grandir ton avatar pour atteindre le niveau. \n" +
                "Pour cela, complète la conversion en déplaçant la " +
                "virgule\nà l'aide des boutons et en validant ton choix.\n");
        l.setFont(Font.font("Dyuthi", 20));
        l.setStyle("-fx-font-weight: bold;\n");
        quizView.getChildren().add(l);

        doubleAnswer = answer;


        HBox conversionBox = new HBox();
        conversionBox.setStyle("-fx-alignment: center;");
        VBox leftPartConversion = new VBox(10);
        Label conversionLabel1 = new Label(c.getNb1() + " " + c.getUnit1().getUnitString() + " = ");
        conversionLabel1.setFont(Font.font("Dyuthi", 60));
        conversionLabel1.setStyle("-fx-font-weight: bold;\n");
        leftPartConversion.getChildren().add(conversionLabel1);

        DecimalFormat df = new DecimalFormat("##.######",
                new DecimalFormatSymbols(Locale.FRENCH));

        VBox centerPartConversion = new VBox(10);
        Label conversionLabel2 = new Label(df.format(answer) + " ");
        conversionLabel2.setFont(Font.font("Dyuthi", 60));
        conversionLabel2.setStyle("-fx-font-weight: bold;\n");
        centerPartConversion.getChildren().add(conversionLabel2);

        centerPartConversion.getChildren().add(arrowBox(category, c, leftOK, rightOK));
        centerPartConversion.getChildren().add(validationBox("yellowgreen", doubleAnswer, c, 3));

        VBox rightPartConversion = new VBox(10);
        Label conversionLabel3 = new Label(c.getUnit2().getUnitString());
        conversionLabel3.setFont(Font.font("Dyuthi", 60));
        conversionLabel3.setStyle("-fx-font-weight: bold;\n");
        rightPartConversion.getChildren().add(conversionLabel3);

        conversionBox.getChildren().addAll(leftPartConversion, centerPartConversion, rightPartConversion);

        quizView.getChildren().add(conversionBox);
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
    private HBox arrowBox(String category, Conversion c, boolean leftOK, boolean rightOK) {
        HBox arrowBox = new HBox(10);
        arrowBox.setStyle("-fx-alignment: center;");
        String fichier = "";
        if (leftOK) {
            fichier = "data/flechegaucheverte.png";
        } else {
            fichier = "data/flechegaucherouge.png";
        }
        String imageURI = new File(fichier).toURI().toString();
        Image img = new Image(imageURI);
        ImageView view = new ImageView(img);
        view.setFitWidth(40);
        view.setPreserveRatio(true);
        Button leftbutton = new Button();
        leftbutton.maxWidth(40);
        leftbutton.setGraphic(view);

        if (rightOK) {
            fichier = "data/flechedroiteverte.png";
        } else {
            fichier = "data/flechedroiterouge.png";
        }
        imageURI = new File(fichier).toURI().toString();
        img = new Image(imageURI);
        view = new ImageView(img);
        view.setFitWidth(40);
        view.setPreserveRatio(true);
        Button rightbutton = new Button();
        rightbutton.maxWidth(40);
        rightbutton.setGraphic(view);

        leftbutton.setOnAction(event -> qController.leftArrow(event, activeLearner, category, c, doubleAnswer));
        rightbutton.setOnAction(event -> qController.rightArrow(event, activeLearner, category, c, doubleAnswer));

        arrowBox.getChildren().addAll(leftbutton, rightbutton);

        return arrowBox;
    }


    /**
     * Génère la partie avec le bouton de validation.<br>
     *
     * @param color String, couleur du bouton
     * @param answer Double
     * @param c Conversion, réponse correcte
     * @param level int
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
    @Override
    public VBox quiz(String category, Conversion c, String answer) {

        return null;
    }

    @Override
    public HBox validationBox(String color, String answer, String correct, int level) {
        return null;
    }
}
