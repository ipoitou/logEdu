package view;

import controller.QuizController;
import dao.LearnersDao;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Conversion;
import model.Learner;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class MessageView {
    private VBox message;
    private ConversionsView cv;
    private LearnersDao lDao;


    public MessageView(Learner activeLearner, String category, ConversionsView cv, LearnersDao lDao) {
        this.cv = cv;
        this.lDao = lDao;
        message = new VBox(10);
        message.setStyle("-fx-alignment: center;");
        message.getChildren().add(bravo(activeLearner, category));

        HBox nextBox = new HBox();
        nextBox.setStyle("-fx-alignment: center;");
        Button nextButton = new Button("Suite");
        nextButton.setFont(Font.font("Dyuthi", 30));
        nextButton.setStyle("-fx-font-weight: bold;\n" + "-fx-background-color: yellowgreen");
        QuizController qController = new QuizController(cv, lDao);
        nextButton.setOnAction(event -> qController.next(event, category, activeLearner));
        nextBox.getChildren().add(nextButton);
        message.getChildren().add(nextBox);
    }



    public MessageView(String msg, Learner activeLearner, String category, Conversion c, ConversionsView cv, LearnersDao lDao) {
        this.cv = cv;
        this.lDao = lDao;
        message = new VBox();
        message.setStyle("-fx-alignment: center;");
        VBox labelsBox = new VBox();
        Label answerLabel = new Label(c.getNb1() + " " + c.getUnit1().getUnitString() + " = " + c.getNb2() + " " + c.getUnit2().getUnitString() + "\n\n");
        answerLabel.setFont(Font.font("Dyuthi", 30));
        answerLabel.setStyle("-fx-font-weight: bold;\n");
        labelsBox.getChildren().add(answerLabel);
        Label label = new Label();
        if (msg.equals("Sens")) {
            label = direction(activeLearner.getWrongDirection(), c, category);
            labelsBox.getChildren().add(label);

            HBox labelsHBox = new HBox();
            labelsHBox.setStyle("-fx-alignment: center;");
            labelsHBox.getChildren().add(labelsBox);

            message.getChildren().add(labelsHBox);
        }
        if (msg.equals("Rangs")) {
            label = ranks(activeLearner.getWrongRank(), c, category);
            labelsBox.getChildren().add(label);

            HBox labelsHBox = new HBox();
            labelsHBox.setStyle("-fx-alignment: center;");
            labelsHBox.getChildren().add(labelsBox);

            message.getChildren().add(labelsHBox);
        }
        if (msg.equals("Beaucoup")) {
            message = beaucoup(category, activeLearner);


        }


        Button nextButton = new Button("Suite");
        nextButton.setFont(Font.font("Dyuthi", 30));
        nextButton.setStyle("-fx-font-weight: bold;\n" + "-fx-background-color: yellowgreen");
        QuizController qController = new QuizController(cv, lDao);
        nextButton.setOnAction(event -> qController.next(event, category, activeLearner));
        message.getChildren().add(nextButton);
    }

    private Label bravo(Learner activeLearner, String category) {
        String text = "Bravo, la conversion était correcte.\n";
        if (activeLearner.getCorrectAnswer() == 5) {
            text += "Ton avatar est assez grand, \ntu as atteint le niveau " + (activeLearner.findLevelCategory(category));
        } else {
            text += "Ton avatar a grandi, tu peux \npasser à la conversion suivante.";
        }
        Label label = new Label(text);
        label.setFont(Font.font("Dyuthi", 40));
        label.setStyle("-fx-font-weight: bold;\n");
        return label;
    }


    private Label direction(int wrongDirection, Conversion c, String category) {
        String text = "";
        if (wrongDirection == 1) {
            text = text + "Pose-toi la question:\n\"1 " + c.getUnit2().getUnitString() + " est-il plus petit ou plus grand que 1 " + c.getUnit1().getUnitString() + " ?\"\n\nContinue à t'entraîner, tu vas y arriver.";
        }
        if (wrongDirection == 2) {
            String objet = "";
            switch (category) {
                case "Longueurs" : objet = "segment";
                                    break;
                case "Aires" : objet = "carré";
                                break;
                case "Contenances" : objet = "récipient";
                                    break;
                case "Volumes" : objet = "cube";
                                break;
                default: break;
            }
            text = text + "Pose-toi la question:\n\"Peut-on mettre plus de "  + c.getUnit1().getUnitString() + " ou plus \n" +
                    "de " + c.getUnit2().getUnitString() + " dans un " + objet + " donné ?\"\n\nContinue à t'entraîner, tu vas y arriver.";
        }
        if (wrongDirection == 3) {
            double nb = pow(10, (c.getUnit2().getUnitRank() - c.getUnit1().getUnitRank()) * c.getRang());
            DecimalFormat df = new DecimalFormat("##.######",
                    new DecimalFormatSymbols(Locale.FRENCH));
            text = text + "1 " + c.getUnit1().getUnitString() + " = " + df.format(nb) + " "+ c.getUnit2().getUnitString() + "\n\nContinue à t'entraîner, tu vas y arriver.";
        }
        Label label = new Label(text);
        label.setFont(Font.font("Dyuthi", 30));
        label.setStyle("-fx-font-weight: bold;\n");
        return label;
    }

    private Label ranks(int wrongRank, Conversion c, String category) {
        System.out.println("dans ranks");
        String text = "";
        if(wrongRank < 3) {
            System.out.println("dans if");
            if (category.equals("Longueurs") || category.equals("Contenances")) {
                text += ranksLengthCapacity(c);
            } else {
                text += ranksAreaVolume(wrongRank, c, category);
            }
        } else {
            double nb = pow(10, (c.getUnit2().getUnitRank() - c.getUnit1().getUnitRank()) * c.getRang());
            DecimalFormat df = new DecimalFormat("##.######",
                    new DecimalFormatSymbols(Locale.FRENCH));
            text = text + "1 " + c.getUnit1().getUnitString() + " = " + df.format(nb) + " "+ c.getUnit2().getUnitString() + "\n\n";
        }
        text += "Continue à t'entraîner, tu vas y arriver.";

        Label label = new Label(text);
        label.setFont(Font.font("Dyuthi", 30));
        label.setStyle("-fx-font-weight: bold;\n");
        return label;
    }

    private String ranksLengthCapacity(Conversion c) {
        String text = "";
        if (c.getUnit1().getUnitRank() == 3) {
            text += c.getUnit2().getPrefixString() + " signifie ";
            int unitsGap = c.getUnit2().getUnitRank() - c.getUnit1().getUnitRank();
            double nb = pow(10, abs(unitsGap));
            DecimalFormat df = new DecimalFormat("##.######",
                    new DecimalFormatSymbols(Locale.FRENCH));
            text += df.format(nb) + " fois ";
            if (unitsGap > 0) {
                text += "moins.\n\n";
            } else {
                text += "plus.\n\n";
            }
        } else {
            if (c.getUnit2().getUnitRank() == 3) {
                text += c.getUnit1().getPrefixString() + " signifie ";
                int unitsGap = c.getUnit1().getUnitRank() - c.getUnit2().getUnitRank();
                double nb = pow(10, abs(unitsGap));
                DecimalFormat df = new DecimalFormat("##.######",
                        new DecimalFormatSymbols(Locale.FRENCH));
                text += df.format(nb) + " fois ";
                if (unitsGap > 0) {
                    text += "moins.\n\n";
                } else {
                    text += "plus.\n\n";
                }
            } else {
                if (c.getUnit1().getUnitRank() - c.getUnit2().getUnitRank() == 1 || c.getUnit2().getUnitRank() - c.getUnit1().getUnitRank() == 1) {
                    text += c.getUnit1().getUnitString() + " et " + c.getUnit2().getUnitString() + " sont des unités voisines.\n\n";
                }
            }
        }
        System.out.println(text);
        return text;
    }


    private String ranksAreaVolume(int wrongRank, Conversion c, String category) {
        String cat = "";
        String rangs = "";
        if (category.equals("Aires")) {
            cat = "aires";
            rangs = "deux";
        } else {
            cat = "volumes";
            rangs = "trois";
        }
        String text = "";
        if (wrongRank == 1) {
            text += "Pour les " + cat + ", le tableau de conversion a une particularité.\n\n";
        } else {
            text += "Pour les " + cat + ", pour chaque changement d'unité, on doit décaler de " + rangs + " rangs.\n\n";
        }
        return text;
    }


    private VBox beaucoup(String category, Learner activeLearner) {
        VBox message = new VBox();
        message.setStyle("-fx-alignment: center;");
        switch (activeLearner.getWrongAnswer()) {
            case 1: String text = "J'ai l'impression que tu ne connais pas bien toutes les unités. \nRegarde-les et retiens-les.\n";
                    if (category.equals("Longueurs")) {
                        text += "km - hm - dam - m - dm - cm - mm";
                    }
                    if (category.equals("Contenances")) {
                        text += "kL - hL - daL - L - dL - cL - mL";
                    }
                    if (category.equals("Aires")) {
                        text += "km² - hm² - dam² - m² - dm² - cm² - mm²";
                    }
                    if (category.equals("Volumes")) {
                        text += "km³ - hm³ - dam³ - m³ - dm³ - cm³ - mm³";
                    }
                    Label label = new Label(text);
                    label.setFont(Font.font("Dyuthi", 30));
                    label.setStyle("-fx-font-weight: bold;\n");
                    message.getChildren().add(label);
                    break;
            case 2: String text2 = "Regarde bien le tableau de conversion :\n";
                    Label label2 = new Label(text2);
                    label2.setFont(Font.font("Dyuthi", 30));
                    label2.setStyle("-fx-font-weight: bold;\n");
                    message.getChildren().add(label2);
                    String imageURI = new File("data/Tableau" + category + ".png").toURI().toString();
                    Image image = new Image(imageURI);
                    ImageView imageview = new ImageView();
                    imageview.setImage(image);
                    message.getChildren().add(imageview);
                    break;
            default: String text3 = "Appelle ton professeur.\n";
                    Label label3 = new Label(text3);
                    label3.setFont(Font.font("Dyuthi", 30));
                    label3.setStyle("-fx-font-weight: bold;\n");
                    message.getChildren().add(label3);
                    break;
        }
        return message;
    }
    /*private Label dommage(String msg) {
        String text = "Dommage la conversion était fausse.\nContinue à t'entraîner, tu vas y arriver.\n" + msg;
        Label label = new Label(text);
        label.setFont(Font.font("Dyuthi", 40));
        label.setStyle("-fx-font-weight: bold;\n");
        return label;
    }*/


    public VBox getMessage() {
        return message;
    }
}
