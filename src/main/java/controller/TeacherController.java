package controller;

import dao.LearnersDao;
import javafx.event.ActionEvent;
import view.ConversionsView;
import view.TeacherView;

public class TeacherController {
    private ConversionsView cv;

    public TeacherController(ConversionsView cv) {
        this.cv = cv;
    }

    public void classStatistics(final ActionEvent event, String className, ConversionsView cv) {
        cv.updateRoot(className);

    }

    public void conversionsView(final ActionEvent event, ConversionsView cv) {
        cv.updateRoot(null, false);
    }

    public void teacherHome(final ActionEvent event) {
        cv.updateRoot();
    }
}
