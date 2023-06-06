# Le monde des conversions

Il s'agit d'un projet réalisé pour l'UE de M1 Informatique "Logiciels Educatifs". 
Le but était de concevoir un logiciel éducatif respectant les règles d'un EIAH. Le domaine de ce logiciel est les mathématiques et en particulier les conversions. Il est à destination des élèves de 6ème et 5ème et de leur enseignant. Il a été implémenté en java.


# Auteur

@**RAMPON Ildès**  89064800
**mail** : ildes.rampon@etu.univ-lyon1.fr

----


# Compiler le projet

Commande mvn compile


# Exécuter le projet

Commande mvn exec:java


# Quelques éléments de la base de données

Les données se trouvent dans data/learnersData.xml

Un apprenant possible déjà enregistré: classe 6B, prénom Alice, mot de passe PWAlice

L'enseignant n'a pas de mot de passe pour l'instant, il suffit de valider.
Une classe avec plusieurs élèves enregistrés: 6F


# Différents fichiers dans src/main/java

|Dossier|Fichiers|
|--|--|
|controller|HomeController.java, InitController.java, LearnerController.java, QuizController.java, TeacherController.java|
|dao|Dao.java, LearnersDao.java|
|model|Category.java, Conversion.java, Learner.java, LearnersData.java, Unit.java|
|utils|EasyAlert.java, EasyClipboard.java|
|view|AvatarChoiceView.java, ConversionsView.java, HomeView.java, LearnerView.java,LevelView.java, Level1View.java, Level2View.java, Level3View.java, MessageView.java, TeacherView.java|

