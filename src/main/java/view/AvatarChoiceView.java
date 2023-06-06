package view;

import controller.HomeController;
import dao.LearnersDao;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.Learner;

import java.io.File;


/**
 * Classe qui gère la vue pour choisir l'avatar.<br>
 *
 */
public class AvatarChoiceView {
    private BorderPane home = new BorderPane();

    private ConversionsView conversionsView;
    private HomeController hController;


    /**
     * Constructeur de la vue avec les avatars à choisir.<br>
     * Appelée par ConversionsView.updatRoot(Learner, boolean)
     *
     * @param cv ConversionsView à mettre à jour
     * @param l Learner, l'apprenant connecté, peut être null
     * @param ld LearnersDao
     *
     */
    public AvatarChoiceView(ConversionsView cv, Learner l, LearnersDao ld) {
        conversionsView = cv;


        VBox centerBox = new VBox();
        Pane learnerBox = new HBox();

        hController = new HomeController(conversionsView, ld);


        final TilePane avatarChoice = new TilePane();
        avatarChoice.setHgap(20);
        avatarChoice.setVgap(20);

        File dir = new File("data/avatars");
        File[] liste = dir.listFiles();
        for (int i = 0; i < liste.length; i++) {
            if (liste[i].isFile()) {
                String imageChoiceURI = new File("data/avatars/" + liste[i].getName()).toURI().toString();
                Image imageChoice = new Image(imageChoiceURI);
                ImageView avatarChoiceImage = new ImageView();
                avatarChoiceImage.setImage(imageChoice);
                avatarChoiceImage.setFitHeight(100);
                avatarChoiceImage.setFitWidth(75);
                avatarChoiceImage.setPreserveRatio(false);
                /*Integer inti = i;*/
                Button choiceButton = new Button();
                choiceButton.setGraphic(avatarChoiceImage);
                int finalI = i;
                choiceButton.setOnAction(event -> hController.avatarChange(event, l, liste[finalI].getName()));

                avatarChoice.getChildren().add(choiceButton);
            }
        }
        centerBox.getChildren().addAll(learnerBox, avatarChoice);
        home.setCenter(centerBox);
        home.setStyle("-fx-alignment: center;");
    }


    /**
     * Getteur de la vue avec les avatars à choisir.<br>
     * Appelée par ConversionsView.updatRoot(Learner, boolean)
     *
     * @return Pane, fenêtre avec tous les avatars
     *
     */
    public Pane getAvatarChoiceView() {
        return home;
    }

}
