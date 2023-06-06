package view;

import dao.LearnersDao;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Conversion;
import model.Learner;
import model.Unit;

import java.io.File;

/**
 * Classe qui gère la vue principale de l'application.<br>
 *
 */
public class ConversionsView {
    private HBox root = new HBox(10);
    private final int width;
    private Learner activeLearner = null;
    private final LearnersDao lDao;

    /**
     * Constructeur de la vue principale.<br>
     *
     * @param ld LearnersDao
     * @param stage Stage
     * @param width largeur fenêtre
     * @param height hauteur fenêtre
     *
     */
    public ConversionsView(final LearnersDao ld, final Stage stage, final int width, final int height) {
        lDao = ld;
        this.width = width;
        String imageURI = new File("data/montagnesIcon.png").toURI().toString();
        Image icon = new Image(imageURI);
        stage.getIcons().add(icon);
        stage.setTitle("Conversions");
        updateRoot(null, false);

        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Affiche le profil de l'apprenant ou les avatars à choisir.<br>
     * Appelée par HomeController.login, HomeController.registration, HomeController.avatarChange,
     * LearnerController.openAvatarChoice, QuizController.back, QuizController.next ConversionsView
     *
     * @param l Learner, l'apprenant connecté, peut être null
     * @param avatarChoice boolean
     */
    public void updateRoot(Learner l, boolean avatarChoice) {
        root.getChildren().clear();
        if (l == null) {

            root.getChildren().add(new HomeView(this, lDao, width).getHome());
        }
        else {
            if (avatarChoice == false) {
                activeLearner = l;
                LearnerView learnerView = new LearnerView(this, activeLearner, width, lDao);

                root.getChildren().add(learnerView.getLearnerView());
            }
            else {
                activeLearner = l;
                AvatarChoiceView avatarChoiceView = new AvatarChoiceView(this, activeLearner, lDao);
                root.getChildren().add(avatarChoiceView.getAvatarChoiceView());

            }
        }
        root.setBackground(new Background(new BackgroundFill(Color.rgb(254, 254, 226),
                                                            null,
                                                            null)));
        root.setAlignment(Pos.CENTER);
    }


    /**
     * Affiche la page enseignant avec la classe choisie.<br>
     * Appelée par TeacherController.classStatistics
     *
     * @param className String, la classe sélectionnée
     *
     */
    public void updateRoot(String className) {
        root.getChildren().clear();
        TeacherView teacherView = new TeacherView(this, lDao, width, className);

        root.getChildren().add(teacherView.getTeacherView());
    }


    /**
     * Affiche la page enseignant pour choisir la classe.<br>
     * Appelée par TeacherController.teacherHome
     *
     */
    public void updateRoot() {
        root.getChildren().clear();
        TeacherView teacherView = new TeacherView(this, lDao, width);

        root.getChildren().add(teacherView.getTeacherView());
    }


    /**
     * Dirige vers la page avec une conversion.<br>
     * Appelée par QuizController.next et LearnerController.quiz
     *
     * @param l Learner, l'apprenant connecté
     * @param categoryName String, la catégorie du quiz
     *
     */
    public void updateRoot(Learner l, String categoryName) {
        root.getChildren().clear();
        if(l.findLevelCategory(categoryName) == 0) {
            Level1View levelView = new Level1View(this, lDao, l, width, categoryName);
            root.getChildren().add(levelView.getLevelView());
        }
        if(l.findLevelCategory(categoryName) == 1) {
            Level2View levelView = new Level2View(this, lDao, l, width, categoryName);
            root.getChildren().add(levelView.getLevelView());
        }
        if(l.findLevelCategory(categoryName) >= 2) {
            Level3View levelView = new Level3View(this, lDao, l, width, categoryName);
            root.getChildren().add(levelView.getLevelView());
        }
    }


    /**
     * Met à jour la page, avec la réponse affichée.<br>
     * Appelée par QuizController.level1Selection
     *
     * @param l Learner, l'apprenant connecté
     * @param categoryName String, la catégorie du quiz
     * @param c Conversion, conversion générée à la page précédente
     * @param answerUnit Unit
     *
     */
    public void updateRoot(Learner l, String categoryName, Conversion c, Unit answerUnit) {
        root.getChildren().clear();
        if(l.findLevelCategory(categoryName) == 0) {
            Level1View levelView = new Level1View(this, lDao, l, width, categoryName, c, answerUnit);
            root.getChildren().add(levelView.getLevelView());
        }
        /*if(l.findLevelCategory(categoryName) == 1) {
            Level2View levelView = new Level2View(this, lDao, l, width, categoryName, c, answerUnit);
            root.getChildren().add(levelView.getLevelView());
        }*/
        /*if(l.findLevelCategory(categoryName) >= 2) {
            Level3View levelView = new Level3View(this, lDao, l, width, categoryName);
            root.getChildren().add(levelView.getLevelView());
        }*/
    }


    /**
     * Met à jour la page, avec la réponse affichée.<br>
     * Appelée par QuizController.level1Selection
     *
     * @param l Learner, l'apprenant connecté
     * @param categoryName String, la catégorie du quiz
     * @param c Conversion, conversion générée à la page précédente
     * @param answer String, la réponse de l'apprenant
     *
     */
    public void updateRoot(Learner l, String categoryName, Conversion c, Double answer) {
        root.getChildren().clear();
        /*if(l.findLevelCategory(categoryName) == 0) {
            Level1View levelView = new Level1View(this, lDao, l, width, categoryName, c, answer);
            root.getChildren().add(levelView.getLevelView());
        }*/
        if(l.findLevelCategory(categoryName) == 1) {
            Level2View levelView = new Level2View(this, lDao, l, width, categoryName, c, answer);
            root.getChildren().add(levelView.getLevelView());
        }
    }


    /**
     * Met à jour la page, avec la réponse affichée.<br>
     * Appelée par QuizController.level1Selection
     *
     * @param l Learner, l'apprenant connecté
     * @param categoryName String, la catégorie du quiz
     * @param c Conversion, conversion générée à la page précédente
     * @param answer String, la réponse de l'apprenant
     *
     */
    public void updateRoot(Learner l, String categoryName, Conversion c, double answer, boolean leftOK, boolean rightOK) {
        root.getChildren().clear();
        Level3View levelView = new Level3View(this, lDao, l, width, categoryName, c, answer, leftOK, rightOK);
        root.getChildren().add(levelView.getLevelView());
    }

    /**
     * Met à jour la page, avec un message "Bravo" ou une aide.<br>
     * Appelée par QuizController.correction
     *
     * @param activeLearner Learner, l'apprenant connecté
     * @param category String, la catégorie du quiz
     * @param c Conversion, la conversion traitée
     * @param message String, "Bravo", "Sens", Rangs" ou "Beaucoup"
     *
     */
    public void updateRoot(int level, Learner activeLearner, String category, Conversion c, String message) {
        root.getChildren().clear();
        MessageView msgView = null;
        switch(message) {
            case "Bravo": msgView = new MessageView(activeLearner, category, this, lDao);
                            break;
            case "Sens": msgView = new MessageView(message, activeLearner, category, c, this, lDao);
                            break;
            case "Rangs": msgView = new MessageView(message, activeLearner, category, c, this, lDao);
                            break;
            case "Beaucoup": msgView = new MessageView(message, activeLearner, category, c, this, lDao);
                            break;
            default: break;
        }

        if (level == 1) {
            Level1View levelView = new Level1View(this, lDao, activeLearner, width, category, msgView);
            root.getChildren().add(levelView.getLevelView());
        }
        if (level == 2) {
            Level2View levelView = new Level2View(this, lDao, activeLearner, width, category, msgView);
            root.getChildren().add(levelView.getLevelView());
        }
        if (level == 3) {
            Level3View levelView = new Level3View(this, lDao, activeLearner, width, category, msgView);
            root.getChildren().add(levelView.getLevelView());
        }
    }
}
