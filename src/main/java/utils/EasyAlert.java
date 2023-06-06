package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/** 
 * A simple utility to display an error message in a popup window. Juste use
 * {@code EasyAlert.alert("message").}
 */
public interface EasyAlert {
    /**
     * Display msg in a popup window and block until the user clicks OK.
     * @param msg message à afficher
     */
    static void alert(String msg) {
        Alert a = new Alert(AlertType.ERROR);
        a.setContentText(msg);
        a.showAndWait();
    }
}
