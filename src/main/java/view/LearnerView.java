package view;

import controller.LearnerController;
import dao.LearnersDao;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.Learner;

import java.io.File;


/**
 * Classe qui gère la vue de l'apprenant.<br>
 *
 */
public class LearnerView {
    private BorderPane home = new BorderPane();
    private Learner activeLearner;
    private ConversionsView conversionsView;
    private LearnerController lController;
    private LearnersDao lDao;


    /**
     * Constructeur de la vue du profil de l'apprenant.<br>
     * Appelée par ConversionsView.updatRoot(Learner, boolean)
     *
     * @param cv ConversionsView à mettre à jour
     * @param l Learner, apprenant connecté
     * @param width int, largeur de la fenêtre
     * @param ld LearnersDao
     *
     */
    public LearnerView(ConversionsView cv, Learner l, int width, LearnersDao ld) {
        activeLearner = l;
        conversionsView = cv;
        lDao = ld;
        Label lTitre = new Label("Le Monde Des Conversions");
        lTitre.setTextAlignment(TextAlignment.CENTER);
        lTitre.setAlignment(Pos.TOP_CENTER);
        lTitre.setFont(Font.font("Dyuthi", 40));
        lTitre.setStyle("-fx-alignment: center; -fx-font-weight: bold;\n");
        home.setTop(lTitre);

        VBox centerBox = new VBox();
        Pane learnerBox = new HBox();
        String imageURI = new File("data/avatars/" + activeLearner.getAvatar()).toURI().toString();
        Image image = new Image(imageURI);
        ImageView avatar = new ImageView();
        avatar.setImage(image);
        avatar.setFitWidth(200);
        avatar.setPreserveRatio(true);

        VBox prenomBox = new VBox();
        Label lPrenom = new Label(activeLearner.getName());
        lPrenom.setFont(Font.font("Dyuthi", 40));
        lPrenom.setStyle("-fx-font-weight: bold;\n");
        Button avatarButton = new Button("Changer d'avatar");
        lController = new LearnerController(ld);
        avatarButton.setOnAction(event -> lController.openAvatarChoice(event, conversionsView, activeLearner, lDao));
        prenomBox.getChildren().addAll(lPrenom, avatarButton);
        prenomBox.setAlignment(Pos.CENTER_LEFT);


        learnerBox.getChildren().addAll(avatar,prenomBox);
        learnerBox.setStyle("-fx-alignment: center; ");


        Pane levelsBox = new HBox();
        levelsBox.getChildren().addAll(lBox("Longueurs", width),
                                        new Label("          "),
                                        lBox("Aires", width),
                                        new Label("          "),
                                        lBox("Contenances", width),
                                        new Label("          "),
                                        lBox("Volumes", width));
        levelsBox.setStyle("-fx-alignment: justify;");

        centerBox.getChildren().addAll(learnerBox, levelsBox);
        home.setCenter(centerBox);
        home.setStyle("-fx-alignment: center;");
    }


    /**
     * Génère la partie avec les montagnes d'une catégorie.<br>
     *
     * @param titre String
     * @param width int, largeur de la fenêtre
     *
     */
    private Pane lBox (String titre, int width) {
        Pane levelBox = new AnchorPane();
        Label lTitre = new Label(titre);
        lTitre.setTextAlignment(TextAlignment.CENTER);
        lTitre.setFont(Font.font("Dyuthi", 40));
        lTitre.setStyle("-fx-alignment: center; -fx-font-weight: bold;\n");

        AnchorPane.setTopAnchor(lTitre, 0.0);
        AnchorPane.setLeftAnchor(lTitre, 0.0);
        int level = 0;
        if (titre.equals("Longueurs")) {
            level = activeLearner.getLengthLevel();
        }
        if (titre.equals("Aires")) {
            level = activeLearner.getAreaLevel();
        }
        if (titre.equals("Contenances")) {
            level = activeLearner.getCapacityLevel();
        }
        if (titre.equals("Volumes")) {
            level = activeLearner.getVolumeLevel();
        }
        if (level == 3) {
            level = 2;
        }
        Button levelButton = new Button("Objectif niveau " + Integer.toString(level+1));
        levelButton.setFont(Font.font("Dyuthi", 20));
        levelButton.setStyle("-fx-alignment: center; -fx-font-weight: bold;\n");
        levelButton.setOnAction(event -> lController.quiz(event, activeLearner, titre, conversionsView));

        AnchorPane.setTopAnchor(levelButton, 60.0);
        AnchorPane.setLeftAnchor(levelButton, 0.0);

        String imageURI = new File("data/montagnes.png").toURI().toString();
        Image image = new Image(imageURI);
        ImageView levelImage = new ImageView();
        levelImage.setImage(image);
        levelImage.setFitWidth(width/5);
        levelImage.setPreserveRatio(true);

        AnchorPane.setTopAnchor(levelImage, 120.0);
        AnchorPane.setLeftAnchor(levelImage, 0.0);

        String imageVisageURI = new File("data/visages/" + activeLearner.getAvatar()).toURI().toString();
        Image imageVisage = new Image(imageVisageURI);
        ImageView levelVisage = new ImageView();
        levelVisage.setImage(imageVisage);
        levelVisage.setFitWidth(width/15);
        levelVisage.setPreserveRatio(true);

        int levelToSearch = 0;
        if (titre.equals("Longueurs")) {
            levelToSearch = activeLearner.getLengthLevel();
        }
        if (titre.equals("Aires")) {
            levelToSearch = activeLearner.getAreaLevel();
        }
        if (titre.equals("Contenances")) {
            levelToSearch = activeLearner.getCapacityLevel();
        }
        if (titre.equals("Volumes")) {
            levelToSearch = activeLearner.getVolumeLevel();
        }
        double posTop = 0.0;
        double posLeft = 0.0;
        if (levelToSearch == 0) {
            posTop = 280.0;
            posLeft = 170.0;
        }
        if (levelToSearch == 1) {
            posTop = 170.0;
            posLeft = 70.0;
        }
        if (levelToSearch == 2) {
            posTop = 140.0;
            posLeft = 160.0;
        }
        if (levelToSearch == 3) {
            posTop = 100.0;
            posLeft = 90.0;
        }
        AnchorPane.setTopAnchor(levelVisage, posTop);
        AnchorPane.setLeftAnchor(levelVisage, posLeft);

        levelBox.getChildren().addAll(lTitre, levelButton, levelImage, levelVisage);
        //levelBox.setStyle("-fx-background-radius: 10 10 10 10;" + "-fx-border-color: darkkhaki;");

        return levelBox;
    }


    /**
     * Getteur de la vue du profil de l'apprenant.<br>
     * Appelée par ConversionsView.updatRoot(Learner, boolean)
     *
     * @return Pane, fenêtre du profil
     *
     */
    public Pane getLearnerView() {
        return home;
    }

}
