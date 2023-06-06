package controller;

import dao.LearnersDao;
import model.LearnersData;
import view.ConversionsView;
import javafx.stage.Stage;



/**
 * Classe qui contrôle l'initialisation.<br>

 *
 */
public class InitController {

    private Stage stage = new Stage();
    /**
     * Constructeur par défaut.<br>
     * Initialise la base de données de l'application
     * Instancie la vue principale de l'application

     *
     */
    public InitController() {
        final LearnersData ld = new LearnersData();
        final LearnersDao lDao = new LearnersDao(ld);
        new ConversionsView(lDao, stage, 1280, 720);
    }
}
