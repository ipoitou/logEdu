package dao;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LearnersDao implements Dao<Learner> {
    private final LearnersData learnersData;



    /**
     * Constructeur à partir du learnersData.<br>

     * @param ld LearnersData associé au Dao
     */
    public LearnersDao(final LearnersData ld) {
        learnersData = ld;
    }


    /**
     * Renvoie la liste de tous les Learners.<br>
     *
     * @return la liste de tous les Learners de MES
     */
    @Override
    public List<Learner> getAll() {
        return new ArrayList<>(learnersData.getLearners().values());
    }



    @Override
    public void add(final Learner learner) {
        // inutilisée, présente pour coller au squelette du Dao
    }


    /**
     * Ajoute un Patient dans MES.<br>

     * @param pw password du Learner à ajouter
     * @param l Learner à ajouter
     */
    public void add(final String pw, final Learner l) {
        learnersData.getLearners().put(pw, l);
    }



    /**
     * Renvoie un Learner à partir de son prénom.<br>

     * @param firstname prénom du Learner à ajouter
     * @return Learner dont le nom est donné en paramètre
     */
    public Learner findByName(final String firstname) {
        for (Learner learner : learnersData.getLearners().values()) {
            if (learner.getName().equals(firstname)) {
                return learner;
            }
        } return null;
    }


    /**
     * Renvoie un Learner à partir de son password.<br>

     * @param pw password du Learner à trouver
     * @return Learner dont le password est donné en paramètre
     */
    public Learner findByPassword(final String pw) {
        return learnersData.getLearners().get(pw);
    }


    /**
     * Renvoie la liste des prescriptions d'un patient données par un professionnel.<br>

     * @param p Patient
     * @param hp professionnel dont on veut les prescriptions
     * @return liste des prescriptions du patient this données par le professionnel en paramètre
     */
    /*public List<Prescription> getPrescriptions(final Patient p, final HealthProfessional hp) {
        return p.getPrescriptions().stream()
                .filter(pr -> pr.getHealthProfessional() == hp)
                .collect(Collectors.toList());
    }
*/

    /**
     * Renvoie la Map des learners de l'application.<br>

     * @return Map de Learners
     */
    public Map<String, Learner> getMapLearner() {
        return learnersData.getLearners();
    }

    public List<Learner> learnersByClass (String classN) {
        List<Learner> list = new ArrayList<>();
        for (Learner learner : getAll()) {
            if (learner.getClassName().equals(classN)) {
                list.add(learner);
            }
        }
        return list;
    }

}
