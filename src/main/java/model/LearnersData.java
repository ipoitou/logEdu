package model;

import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class LearnersData {
    private Map<String, Learner> learners = new HashMap<>();

    public LearnersData() {
        try {
            NodeList nList = readFile("data/learnersData.xml", "learner");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    String pw;
                    String firstname;
                    String className;
                    String avatar;
                    Element learnerElement = (Element) nNode;
                    pw = learnerElement.getAttribute("pw");
                    firstname = learnerElement.getElementsByTagName("firstname").item(0).getTextContent();
                    className = learnerElement.getElementsByTagName("class").item(0).getTextContent();
                    avatar = learnerElement.getElementsByTagName("avatar").item(0).getTextContent();

                    Node lengthNode = learnerElement.getElementsByTagName("length").item(0);
                    Element levelElement = (Element) lengthNode;
                    String lengthLevel = levelElement.getAttribute("level");
                    String lengthL1 = levelElement.getElementsByTagName("conversions1").item(0).getTextContent();
                    String lengthL2 = levelElement.getElementsByTagName("conversions2").item(0).getTextContent();
                    String lengthL3 = levelElement.getElementsByTagName("conversions3").item(0).getTextContent();

                    Node areaNode = learnerElement.getElementsByTagName("area").item(0);
                    levelElement = (Element) areaNode;
                    String areaLevel = levelElement.getAttribute("level");
                    String areaL1 = levelElement.getElementsByTagName("conversions1").item(0).getTextContent();
                    String areaL2 = levelElement.getElementsByTagName("conversions2").item(0).getTextContent();
                    String areaL3 = levelElement.getElementsByTagName("conversions3").item(0).getTextContent();

                    Node capacityNode = learnerElement.getElementsByTagName("capacity").item(0);
                    levelElement = (Element) capacityNode;
                    String capacityLevel = levelElement.getAttribute("level");
                    String capacityL1 = levelElement.getElementsByTagName("conversions1").item(0).getTextContent();
                    String capacityL2 = levelElement.getElementsByTagName("conversions2").item(0).getTextContent();
                    String capacityL3 = levelElement.getElementsByTagName("conversions3").item(0).getTextContent();

                    Node volumeNode = learnerElement.getElementsByTagName("volume").item(0);
                    levelElement = (Element) volumeNode;
                    String volumeLevel = levelElement.getAttribute("level");
                    String volumeL1 = levelElement.getElementsByTagName("conversions1").item(0).getTextContent();
                    String volumeL2 = levelElement.getElementsByTagName("conversions2").item(0).getTextContent();
                    String volumeL3 = levelElement.getElementsByTagName("conversions3").item(0).getTextContent();

                    /*Node levelsNode = learnerElement.getElementsByTagName("levels").item(0);
                    Element levelsElement = (Element) levelsNode;
                    String l = levelsElement.getElementsByTagName("length").item(0).getTextContent();
                    String a = levelsElement.getElementsByTagName("area").item(0).getTextContent();
                    String c = levelsElement.getElementsByTagName("capacity").item(0).getTextContent();
                    String v = levelsElement.getElementsByTagName("volume").item(0).getTextContent();*/
                    Learner learner = new Learner.LearnerBuilder(firstname, pw, className)
                            .setAvatar(avatar)
                            .setLength(valueOf(lengthLevel), valueOf(lengthL1), valueOf(lengthL2), valueOf(lengthL3))
                            .setArea(valueOf(areaLevel), valueOf(areaL1), valueOf(areaL2), valueOf(areaL3))
                            .setCapacity(valueOf(capacityLevel), valueOf(capacityL1), valueOf(capacityL2), valueOf(capacityL3))
                            .setVolume(valueOf(volumeLevel), valueOf(volumeL1), valueOf(volumeL2), valueOf(volumeL3))
                            .build();


                    learners.put(pw, learner);
                }
            }

        } catch (ParserConfigurationException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (SAXException ex) {
            throw new RuntimeException(ex);
        }
    }


    /**
     * Lis un fichier xml et filtre suivant le tag.<br>
     *
     * @param fileName String chemin du fichier
     * @param tag String filtre
     *
     * @return NodeList

     */
    private static NodeList readFile(final String fileName, final String tag)
            throws ParserConfigurationException, IOException, SAXException {
        File file = new File(fileName);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(file);
        document.getDocumentElement().normalize();
        return document.getElementsByTagName(tag);
    }

    public Map<String, Learner> getLearners() {
        return learners;
    }



}
