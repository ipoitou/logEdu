package view;

import controller.QuizController;
import dao.LearnersDao;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.Conversion;
import model.Learner;

import java.io.File;

/**
 * Classe abstraite qui gère la vue d'un quiz.<br>
 *
 */
public abstract class LevelView {
    private BorderPane levelView = new BorderPane();
    protected Learner activeLearner;
    protected String category;
    protected Button validationButton;
    protected LearnersDao lDao;
    private int width;
    protected QuizController qController;
    protected ProgressBar pgbar;
    private int nbTrue;
    private int nbFalse;


    /**
     * Constructeur de la partie haute et la partie gauche de la page du quiz.<br>
     *
     * @param cv ConversionsView
     * @param lDao LearnersDao
     * @param activeLearner Learner, apprenant connecté
     * @param width largeur fenêtre
     * @param color String, couleur de la barre de progression
     * @param category String, catégorie du quiz
     *
     */
    public LevelView(ConversionsView cv, LearnersDao lDao, Learner activeLearner, int width, String color, String category) {
        qController = new QuizController(cv, lDao);
        this.width = width;
        levelView.setStyle("-fx-alignment: center;");
        this.activeLearner = activeLearner;
        pgbar = new ProgressBar();
        nbTrue = 0;
        nbFalse = 0;

        // zone en-tête
        HBox header = new HBox(200);
        VBox backBox = new VBox();
        backBox.setPrefWidth(700);
        backBox.setStyle("-fx-alignment: center;");
        Button backButton = new Button("Retour au profil");
        backButton.setFont(Font.font("Dyuthi", 20));
        backButton.setStyle("-fx-alignment: center; -fx-font-weight: bold;\n");
        backButton.setOnAction(event -> qController.back(event, activeLearner, cv));
        backBox.getChildren().add(backButton);

        int level = 0;
        if (category.equals("Longueurs")) {
            level = activeLearner.getLengthLevel() + 1;
        }
        if (category.equals("Aires")) {
            level = activeLearner.getAreaLevel() + 1;
        }
        if (category.equals("Contenances")) {
            level = activeLearner.getCapacityLevel() + 1;
        }
        if (category.equals("Volumes")) {
            level = activeLearner.getVolumeLevel() + 1;
        }
        if (level == 4) {
            level = 3;
        }
        Label l = new Label("Le Monde Des Conversions\n" + category + ", en route pour le niveau " + Integer.toString(level));
        l.setPrefWidth(width);
        l.setTextAlignment(TextAlignment.CENTER);
        l.setFont(Font.font("Dyuthi", 40));
        l.setStyle("-fx-alignment: center; -fx-font-weight: bold;\n");
        header.getChildren().addAll(backBox, l);
        levelView.setTop(header);


        AnchorPane left = new AnchorPane();
        left = leftView(activeLearner, color);

        levelView.setLeft(left);

    }


    /**
     * Génère la partie gauche de la page du quiz.<br>
     *
     * @param activeLearner Learner, apprenant connecté
     * @param color String, couleur de la barre de progression
     *
     * @return AnchorPane, partie gauche de la fenêtre
     *
     */
    private AnchorPane leftView(Learner activeLearner, String color) {
        AnchorPane left = new AnchorPane();
        left.setPrefWidth(200);
        pgbar.setStyle("-fx-accent: " + color);
        pgbar.setProgress(0.2 * activeLearner.getCorrectAnswer());
        pgbar.setPrefHeight(20.0);
        pgbar.setPrefWidth(300.0);
        AnchorPane.setTopAnchor(pgbar, 15.0);
        AnchorPane.setLeftAnchor(pgbar, 20.0);
        String imageURI = new File("data/avatars/" + activeLearner.getAvatar()).toURI().toString();
        Image image = new Image(imageURI);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(100 + activeLearner.getCorrectAnswer() * 80);
        imageView.setPreserveRatio(true);
        AnchorPane.setBottomAnchor(imageView, 10.0);
        AnchorPane.setLeftAnchor(imageView, 10.0 - imageView.getFitWidth()/2);
        left.getChildren().addAll(pgbar, imageView);
        return left;
    }


    /**
     * Getteur de la page du quiz.<br>
     *
     * @return Pane, page du quiz
     *
     */
    public Pane getLevelView() {
        return levelView;
    }


    /**
     * Psitionne le quiz dans la page.<br>
     *
     * @param quizView VBox, quiz à positionner
     *
     */
    public void setQuiz(VBox quizView) {
        levelView.setCenter(quizView);
    }


    /**
     * Génère la partie quiz.<br>
     *
     * @param category String
     *
     * @return VBox, partie quiz
     *
     */
    public abstract VBox quiz(String category);


    /**
     * Génère la partie quiz.<br>
     *
     * @param category String
     * @param c Conversion
     * @param answer String
     *
     * @return VBox, partie quiz
     *
     */
    public abstract VBox quiz(String category, Conversion c, String answer);


    /**
     * Génère la partie avec le bouton de validation.<br>
     *
     * @param color String, couleur du bouton
     * @param answer String, réponse de l'apprenant
     * @param correct String, réponse correcte
     * @param level int, niveau du quiz
     *
     * @return HBox, partie avec le bouton de validation
     *
     */
    public abstract HBox validationBox(String color, String answer, String correct, int level);
}
