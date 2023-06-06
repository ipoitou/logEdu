package controller;

import dao.LearnersDao;
import javafx.event.ActionEvent;
import model.Learner;
import utils.EasyAlert;
import view.ConversionsView;

public class HomeController {
    private final LearnersDao lDao;
    private final ConversionsView conversionsView;

    public HomeController(ConversionsView cv, LearnersDao ld) {
        lDao = ld;
        conversionsView = cv;
    }

    public void login(final ActionEvent event, final String pw, String firstname, String className) {
        if (pw != "" && firstname != "" && className != "") {
            Learner learner = lDao.findByPassword(pw);
            if (learner != null && learner.getName().equals(firstname) && learner.getClassName().equals(className)) {
                conversionsView.updateRoot(learner, false);
            }
            else {
                EasyAlert.alert("Il y a une erreur dans la classe et/ou le prénom et/ou le mot de passe");
                return;
            }
        }
    }

    public void registration(final ActionEvent event, final String pw, String firstname, String className) {
        if (pw != "" && firstname != "" && className != "") {
            Learner learner = lDao.findByPassword(pw);
            if (learner == null) {
                learner = new Learner.LearnerBuilder(firstname, pw, className).build();
                lDao.add(pw, learner);
                conversionsView.updateRoot(learner, false);
            }
            else {
                if (learner.getName().equals(firstname)) {
                    EasyAlert.alert("Le profil existe déjà");
                }
            }
        }
    }

    public void avatarChange(final ActionEvent event, Learner l, String image) {
        l.setAvatar(image);
        conversionsView.updateRoot(l, false);
    }
}
