package view;

import controller.TeacherController;
import dao.LearnersDao;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.Learner;

import java.io.File;


/**
 * Classe qui gère la vue de l'enseignant.<br>
 *
 */
public class TeacherView {
    private Pane teacherView = new VBox();
    final ConversionsView conversionsView;
    final LearnersDao lDao;
    private int width;
    //final HomeController hController;
    final TeacherController tController;


    /**
     * Constructeur de la page de l'enseignant, avec choix de la classe.<br>
     * Appelée par ConversionsView.updateRoot()
     *
     * @param cv ConversionsView à mettre à jour
     * @param lDao LearnersDao
     * @param width int, largeur de la fenêtre
     *
     */
    public TeacherView(ConversionsView cv, LearnersDao lDao, int width) {
        conversionsView = cv;
        this.lDao = lDao;
        tController = new TeacherController(conversionsView);
        this.width = width;
        setTeacherView();
    }


    /**
     * Constructeur de la page de l'enseignant, avec choix de la classe.<br>
     * Appelée par ConversionsView.updateRoot(String)
     *
     * @param cv ConversionsView à mettre à jour
     * @param lDao LearnersDao
     * @param width int, largeur de la fenêtre
     * @param className String, classe choisie
     *
     */
    public TeacherView(ConversionsView cv, LearnersDao lDao, int width, String className) {
        conversionsView = cv;
        this.lDao = lDao;
        tController = new TeacherController(conversionsView);
        this.width = width;
        setTeacherView(className);
    }


    /**
     * Génère la page de l'enseignant avec les infos des élèves.<br>
     *
     * @param className String, nom de la classe choisie
     *
     */
    private void setTeacherView(String className) {
        teacherView.getChildren().clear();

        HBox header = new HBox(200);
        VBox backBox = new VBox();
        backBox.setPrefWidth(700);
        backBox.setStyle("-fx-alignment: center;");
        Button backButton = new Button("Choisir une autre classe");
        backButton.setFont(Font.font("Dyuthi", 20));
        backButton.setStyle("-fx-alignment: center; -fx-font-weight: bold;\n");
        backButton.setOnAction(event -> tController.teacherHome(event));
        backBox.getChildren().add(backButton);

        Label l = new Label("Le Monde Des Conversions\n" + className);
        l.setPrefWidth(width);
        l.setWrapText(true);
        l.setTextAlignment(TextAlignment.CENTER);
        l.setFont(Font.font("Dyuthi", 40));
        l.setStyle("-fx-alignment: center; -fx-font-weight: bold;\n");
        l.setBackground(new Background(new BackgroundFill(Color.rgb(254, 254, 226),
                null,
                null)));
        header.getChildren().addAll(backBox, l);


        ScrollPane classView = new ScrollPane();

        VBox learnersList = new VBox();
        learnersList.setBackground(new Background(new BackgroundFill(Color.rgb(254, 254, 226),
                null,
                null)));
        /*learnersList.getChildren().add(l);*/
        learnersList.setPrefWidth(width);

        for (Learner learner: lDao.learnersByClass(className)) {
            learnersList.getChildren().add(learnerBox(learner));
        }
        classView.setContent(learnersList);
        teacherView.getChildren().addAll(header, classView);
    }


    /**
     * Génère la fenêtre de l'enseignant pour choisir la classe.<br>
     * Appelée par ConversionsView.updateRoot(Learner, boolean)
     *
     */
    private void setTeacherView() {
        teacherView.getChildren().clear();

        BorderPane choiceView = new BorderPane();
        choiceView.setStyle("-fx-alignment: center;");

        // zone en-tête

        HBox header = new HBox(200);
        VBox backBox = new VBox();
        backBox.setPrefWidth(700);
        backBox.setStyle("-fx-alignment: center;");
        Button backButton = new Button("Accueil");
        backButton.setFont(Font.font("Dyuthi", 20));
        backButton.setStyle("-fx-alignment: center; -fx-font-weight: bold;\n");
        backButton.setOnAction(event -> tController.conversionsView(event, conversionsView));
        backBox.getChildren().add(backButton);

        Label l = new Label("Le Monde Des Conversions\nChoix de la classe à suivre");
        l.setPrefWidth(width);
        l.setWrapText(true);
        l.setTextAlignment(TextAlignment.CENTER);
        l.setFont(Font.font("Dyuthi", 40));
        l.setStyle("-fx-font-weight: bold;\n");
        //l.setStyle("-fx-alignment: center; -fx-font-weight: bold;\n");
        l.setBackground(new Background(new BackgroundFill(Color.rgb(254, 254, 226),
                null,
                null)));
        header.getChildren().addAll(backBox, l);

        VBox titleBox = new VBox();
        String imageURI = new File("data/montagnes.png").toURI().toString();
        Image image = new Image(imageURI);
        ImageView montagnes = new ImageView();
        montagnes.setImage(image);
        montagnes.setFitWidth(width/10);
        montagnes.setPreserveRatio(true);


        titleBox.getChildren().addAll(header, montagnes);
        titleBox.setStyle("-fx-alignment: center;");

        choiceView.setTop(titleBox);

        Label commentl = new Label("\nPour la classe choisie, vous pourrez voir, \n" +
                                        "pour chaque élève, dans chaque catégorie, \n" +
                                        "le niveau atteint, le nombre de conversions \n" +
                                        "nécessaires pour atteindre chaque niveau.\n\n");
        commentl.setTextAlignment(TextAlignment.CENTER);
        commentl.setWrapText(true);
        commentl.setFont(Font.font("Dyuthi", 20));
        commentl.setStyle("-fx-alignment: center; -fx-font-weight: bold;\n");

        Label choiceLabel = new Label("Entrer la classe : ");
        choiceLabel.setFont(Font.font("Dyuthi", 20));
        choiceLabel.setStyle(" -fx-font-weight: bold;\n");
        TextField choiceText = new TextField();
        choiceText.setPromptText("6A ou 5E ...");
        HBox classChoiceBox = new HBox(choiceLabel, choiceText);

        Button submitButton = new Button("Valider");
        submitButton.setOnAction(event -> tController.classStatistics(event, choiceText.getText(), conversionsView));

        VBox choiceButtonBox = new VBox(classChoiceBox, submitButton);
        VBox choiceBox = new VBox(commentl, choiceButtonBox);
        choiceBox.setFillWidth(false);

        choiceBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(choiceBox, Pos.CENTER);
        choiceView.setCenter(choiceBox);

        teacherView.getChildren().add(choiceView);
    }


    /**
     * Génère le cadre avec les infos d'un apprenant.<br>
     *
     * @param learner Learner, apprenant passé en paramètre
     *
     * @return HBox, cadre d'un apprenant
     *
     */
    private HBox learnerBox(Learner learner) {
        HBox learnerBox = new HBox(80);
        learnerBox.setPrefWidth(width);
        learnerBox.setBackground(new Background(new BackgroundFill(Color.rgb(254, 254, 226),
                null,
                null)));

        Label nameLabel = new Label(learner.getName());
        nameLabel.setWrapText(true);
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        nameLabel.setFont(Font.font("Dyuthi", 30));
        nameLabel.setStyle("-fx-alignment: center; -fx-font-weight: bold;\n");
        VBox nameBox = new VBox(nameLabel);
        nameBox.setPrefWidth(100);
        learnerBox.getChildren().add(nameBox);

        //info sur chaque catégorie
        for(int i = 0; i < 4; i++) {
            VBox categoryBox = new VBox();

            Label nameCategory = new Label(learner.getCategories().get(i).getName() + "\n");
            nameCategory.setTextAlignment(TextAlignment.CENTER);
            nameCategory.setFont(Font.font("Dyuthi", 30));
            nameCategory.setStyle("-fx-alignment: center; -fx-font-weight: bold;\n");
            categoryBox.getChildren().add(nameCategory);

            //info sur chaque niveau de la catégorie
            for(int j = 0; j < 3; j++) {
                String atteint = "";
                if (learner.getCategories().get(i).getLevel() >= j + 1) {
                    atteint = "atteint";
                }
                else {
                    atteint = "pas atteint";
                }
                Label levelLabel = new Label("Niveau " + Integer.toString(j + 1) + " : " + atteint);
                levelLabel.setTextAlignment(TextAlignment.CENTER);
                levelLabel.setFont(Font.font("Dyuthi", 20));
                levelLabel.setStyle("-fx-alignment: center; -fx-font-weight: bold;\n");

                Label conversionsLabel = new Label(Integer.toString(learner.getCategories().get(i).getNbConversions()[j]) + " conversions réalisées\n");
                conversionsLabel.setTextAlignment(TextAlignment.CENTER);
                conversionsLabel.setFont(Font.font("Dyuthi", 15));
                conversionsLabel.setStyle("-fx-alignment: center;\n");

                categoryBox.getChildren().addAll(levelLabel, conversionsLabel);
            }
            learnerBox.getChildren().add(categoryBox);
        }
        learnerBox.setStyle("-fx-border-style: solid inside;");
        return learnerBox;
    }


    /**
     * Getteur de la vue de démarrage.<br>
     * Appelée par ConversionsView.updatRoot() et ConversionsView.updatRoot(String)
     *
     * @return Pane, fenêtre de l'enseignant
     *
     */
    public Pane getTeacherView() {
        return teacherView;
    }
}
