package controller;

import dao.LearnersDao;
import javafx.event.ActionEvent;
import model.Learner;
import view.ConversionsView;

public class LearnerController {

    private LearnersDao lDao;
    public LearnerController(LearnersDao ld) {
        lDao = ld;
    }

    public void openAvatarChoice(final ActionEvent event, ConversionsView cv, Learner l, LearnersDao ld) {
        cv.updateRoot(l, true);
    }

    public void quiz(final ActionEvent event, Learner activeLearner, String titre, ConversionsView conversionsView) {
        activeLearner.initWrongAnswer();
        activeLearner.initWrongDirection();
        activeLearner.initWrongRank();
        activeLearner.initCorrectAnswer();
        conversionsView.updateRoot(activeLearner, titre);
    }
}
