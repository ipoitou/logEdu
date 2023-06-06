import controller.InitController;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;


/**
 * Main class for the application (structure imposed by JavaFX).
 */
public class App extends Application {

    /**
     * With javafx, start() is called when the application is launched.
     */
    @Override
    public void start(final Stage stage) /*throws ParserConfigurationException, SAXException */ {
        new InitController();
        /*final DataStorage model = new DataStorage();
        final JfxView view = new JfxView(model, stage, 800, 800);*/
        // Second view
        /*new JfxView(model, stage, 600, 600);*/
    }



    /**
     * A main method in case the user launches the application using
     * App as the main class.
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}
