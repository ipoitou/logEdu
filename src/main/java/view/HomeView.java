package view;

import controller.HomeController;
import controller.TeacherController;
import dao.LearnersDao;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.File;


/**
 * Classe qui gère la page d'accueil.<br>
 *
 */
public class HomeView {
    private BorderPane home = new BorderPane();
    private int width;
    final HomeController hController;
    final TeacherController tController;


    /**
     * Constructeur de la vue de démarrage.<br>
     * Appelée par ConversionsView.updatRoot(Learner, boolean)
     *
     * @param cv ConversionsView à mettre à jour
     * @param lDao LearnersDao
     * @param width int, largeur de la fenêtre
     *
     */
    public HomeView(ConversionsView cv, LearnersDao lDao, int width) {
        hController = new HomeController(cv, lDao);
        tController = new TeacherController(cv);
        this.width = width;
        home.setStyle("-fx-alignment: center;");

        // zone en-tête
        VBox titleBox = new VBox();
        Label l = new Label("Le Monde Des Conversions");
        l.setTextAlignment(TextAlignment.CENTER);
        l.setFont(Font.font("Dyuthi", 80));
        l.setStyle("-fx-alignment: center; -fx-font-weight: bold;\n");

        String imageURI = new File("data/montagnes.png").toURI().toString();
        Image image = new Image(imageURI);
        ImageView montagnes = new ImageView();
        montagnes.setImage(image);
        montagnes.setFitWidth(width/10);
        montagnes.setPreserveRatio(true);

        titleBox.getChildren().addAll(l, montagnes);
        titleBox.setStyle("-fx-alignment: center;");

        home.setTop(titleBox);

        // zone de connexion de l'apprenant
        home.setCenter(logBox(cv, lDao));

        // zone de connexion de l'enseignant
        home.setBottom(teacherBox(cv, lDao));
    }


    /**
     * Génère la partie pour se connecter ou s'enregistrer.<br>
     *
     * @param cv ConversionsView à mettre à jour
     * @param lDao LearnersDao
     *
     */
    private Pane logBox(ConversionsView cv, LearnersDao lDao) {
        Pane logBox = new HBox();
        logBox.setPrefWidth(width/2);
        logBox.setStyle("-fx-alignment: center;");

        // partie gauche, déjà inscrit
        Pane login = new VBox();

        Label registered = new Label("Déjà inscrit\n\n");
        registered.setStyle("-fx-alignment: left;" + " -fx-font-weight: bold;\n" + "-fx-font: 30 arial;\n");
        login.getChildren().add(registered);

        Label classLabel = new Label("Entre ta classe : ");
        classLabel.setStyle(" -fx-font-weight: bold;\n");
        TextField classText = new TextField();
        classText.setPromptText("6A ou 5E ...");
        HBox boxClass = new HBox(classLabel, classText);

        Label firstName = new Label("Entre ton prénom : ");
        firstName.setStyle(" -fx-font-weight: bold;\n");
        TextField firstNameT = new TextField();
        firstNameT.setPromptText("Prénom");
        HBox boxFirstName = new HBox(firstName, firstNameT);

        Label password = new Label("Entre ton mot de passe : ");
        password.setStyle(" -fx-font-weight: bold;\n");
        PasswordField passwordT = new PasswordField();
        passwordT.setPromptText("Mot de passe");
        HBox boxPassword = new HBox(password, passwordT);

        Button loginButton = new Button("Valider");
        loginButton.setOnAction(event -> hController.login(event, passwordT.getText(), firstNameT.getText(), classText.getText()));

        login.getChildren().addAll(boxClass, boxFirstName, boxPassword, loginButton);

        //pour séparer les 2 box
        Pane centre = new VBox();
        centre.prefWidth(200);
        Label vide = new Label("                             ");
        centre.getChildren().add(vide);

        // partie droite, pas encore inscrit
        Pane registration = new VBox();

        Label notRegistered = new Label("Pas encore inscrit\n\n");
        notRegistered.setStyle("-fx-alignment: right;" + " -fx-font-weight: bold;\n" + "-fx-font: 30 arial;\n");
        registration.getChildren().add(notRegistered);

        Label classLabel2 = new Label("Entre ta classe : ");
        classLabel2.setStyle(" -fx-font-weight: bold;\n");
        TextField classText2 = new TextField();
        classText2.setPromptText("6A ou 5E ...");
        HBox boxClass2 = new HBox(classLabel2, classText2);

        Label firstName2 = new Label("Entre ton prénom : ");
        firstName2.setStyle(" -fx-font-weight: bold;\n");
        TextField firstNameT2 = new TextField();
        firstNameT2.setPromptText("Prénom");
        HBox boxFirstName2 = new HBox(firstName2, firstNameT2);

        Label password2 = new Label("Choisis un mot de passe : ");
        password2.setStyle(" -fx-font-weight: bold;\n");
        PasswordField passwordT2 = new PasswordField();
        passwordT2.setPromptText("Mot de passe");
        HBox boxPassword2 = new HBox(password2, passwordT2);

        Button registrationButton = new Button("Valider");
        registrationButton.setOnAction(event -> hController.registration(event, passwordT2.getText(), firstNameT2.getText(), classText2.getText()));

        registration.getChildren().addAll(boxClass2, boxFirstName2, boxPassword2, registrationButton);

        logBox.getChildren().addAll(login, centre, registration);
        return logBox;
    }


    /**
     * Génère la zone de connexion de l'enseignant.<br>
     *
     * @param cv ConversionsView à mettre à jour
     * @param lDao LearnersDao
     *
     */
    private Pane teacherBox(ConversionsView cv, LearnersDao lDao) {
        Pane teacherBox = new HBox();
        Pane teacherLog = new VBox();
        teacherLog.setPrefWidth(width/4);
        //teacherBox.setStyle("-fx-alignment: center;");

        Label teacherLabel = new Label("Espace enseignant\n\n");
        teacherLabel.setStyle("-fx-alignment: left;" + " -fx-font-weight: bold;\n" + "-fx-font: 30 arial;\n");
        teacherLog.getChildren().add(teacherLabel);

        Label userLabel = new Label("Nom d'utilisateur : ");
        userLabel.setStyle(" -fx-font-weight: bold;\n");
        TextField userText = new TextField();
        HBox boxUser = new HBox(userLabel, userText);

        Label password3 = new Label("Mot de passe : ");
        password3.setStyle(" -fx-font-weight: bold;\n");
        PasswordField passwordT3 = new PasswordField();
        HBox boxPassword3 = new HBox(password3, passwordT3);

        teacherLog.getChildren().addAll(boxUser, boxPassword3);

        Button loginButton3 = new Button("Valider");
        loginButton3.setOnAction(event -> tController.teacherHome(event));



        teacherBox.getChildren().addAll(teacherLog, loginButton3);
        teacherBox.setPrefWidth(width/4);
        BorderPane.setAlignment(teacherBox, Pos.CENTER);
        teacherBox.setStyle("-fx-alignment: center;");
        return teacherBox;
    }


    /**
     * Getteur de la vue de démarrage.<br>
     * Appelée par ConversionsView.updatRoot(Learner, boolean)
     *
     * @return Pane, fenêtre de démarrage
     *
     */
    public Pane getHome() {
        return home;
    }

}
