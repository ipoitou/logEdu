package model;


import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class Learner {
    private final String name;
    private final String password;
    private final String className;
    private String avatar;
    private List<Category> categories = new ArrayList<>();
    private int wrongDirection;
    private int wrongRank;
    private int wrongAnswer;    // nombre de mauvaise réponses dans un quiz
    private int correctAnswer;  // nombre de bonnes réponses dans un quiz
    private String lengthLevel;
    private String areaLevel;
    private String capacityLevel;
    private String volumeLevel;


    public Learner(String name) {
        this.name = name;
        password = null;
        className = null;
        avatar = "avatar0.png";
        categories.add(new Category("Longueurs", 0, 0, 0, 0));
        categories.add(new Category("Aires", 0, 0, 0, 0));
        categories.add(new Category("Contenances", 0, 0, 0, 0));
        categories.add(new Category("Volumes", 0, 0, 0, 0));
    }

    public Learner(String name, String pw, String className, String avatar, String l, String a, String c, String v) {
        this.name = name;
        password = pw;
        this.className = className;
        this.avatar = avatar;
        lengthLevel = l;
        areaLevel = a;
        capacityLevel = c;
        volumeLevel = v;
    }


    private Learner(final Learner.LearnerBuilder builder) {
        this.name = builder.name;
        this.password = builder.password;
        this.className = builder.className;
        this.avatar = builder.avatar;
        this.categories = builder.categories;
        this.wrongAnswer = builder.wrongAnswer;
        this.correctAnswer = builder.correctAnswer;
    }




    public static class LearnerBuilder {
        private final String name;
        private final String password;
        private final String className;
        private String avatar;
        private List<Category> categories = new ArrayList<>();
        private int wrongAnswer;
        private int correctAnswer;

        public LearnerBuilder(final String name, final String pw, final String className) {
            this.name = name;
            this.password = pw;
            this.className = className;
            this.wrongAnswer = 0;
            this.correctAnswer = 0;
            avatar = "avatar0.png";
            categories.add(new Category("Longueurs", 0, 0, 0, 0));
            categories.add(new Category("Aires", 0, 0, 0, 0));
            categories.add(new Category("Contenances", 0, 0, 0, 0));
            categories.add(new Category("Volumes", 0, 0, 0, 0));
        }


        public Learner.LearnerBuilder setAvatar(final String avatar) {
            this.avatar = avatar;
            return this;
        }


        public Learner.LearnerBuilder setLength(final int level, final int n1, final int n2, final int n3) {
            categories.get(0).setLevel(level);
            categories.get(0).setConversionsInLevel(1, n1);
            categories.get(0).setConversionsInLevel(2, n2);
            categories.get(0).setConversionsInLevel(3, n3);
            return this;
        }

        public Learner.LearnerBuilder setArea(final int level, final int n1, final int n2, final int n3) {
            categories.get(1).setLevel(level);
            categories.get(1).setConversionsInLevel(1, n1);
            categories.get(1).setConversionsInLevel(2, n2);
            categories.get(1).setConversionsInLevel(3, n3);
            return this;
        }

        public Learner.LearnerBuilder setCapacity(final int level, final int n1, final int n2, final int n3) {
            categories.get(2).setLevel(level);
            categories.get(2).setConversionsInLevel(1, n1);
            categories.get(2).setConversionsInLevel(2, n2);
            categories.get(2).setConversionsInLevel(3, n3);
            return this;
        }

        public Learner.LearnerBuilder setVolume(final int level, final int n1, final int n2, final int n3) {
            categories.get(3).setLevel(level);
            categories.get(3).setConversionsInLevel(1, n1);
            categories.get(3).setConversionsInLevel(2, n2);
            categories.get(3).setConversionsInLevel(3, n3);
            return this;
        }


        public Learner build() {
            return new Learner(this);
        }
    }







    public void setAvatar(String image) {
        avatar = image;
    }

    public void setLengthLevel(String l) {
        lengthLevel = l;
    }

    public void setAreaLevel(String l) {
        areaLevel = l;
    }

    public void setCapacityLevel(String l) {
        capacityLevel = l;
    }

    public void setVolumeLevel(String l) {
        volumeLevel = l;
    }


    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getClassName() {
        return className;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getWrongAnswer() {
        return wrongAnswer;
    }

    public int getWrongDirection() {
        return wrongDirection;
    }

    public int getWrongRank() {
        return wrongRank;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public int getLengthLevel() {
        return categories.get(0).getLevel();
    }

    public int getAreaLevel() {
        return categories.get(1).getLevel();
    }

    public int getCapacityLevel() {
        return categories.get(2).getLevel();
    }

    public int getVolumeLevel() {
        return categories.get(3).getLevel();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public int findLevelCategory(String categoryName) {
        int level = 0;
        for (Category cat: categories) {
            if(cat.getName().equals(categoryName)) {
                level =  cat.getLevel();
            }
        }
        return level;
    }

    public void initWrongAnswer() {
        wrongAnswer = 0;
    }


    public void initWrongDirection() {wrongDirection = 0;}


    public void initWrongRank() {wrongRank = 0;}


    public void initCorrectAnswer() {
        correctAnswer = 0;
    }

    public void incrementLevelCategory(String categoryName) {
        for (Category cat: categories) {
            if(cat.getName().equals(categoryName)) {
                cat.setLevel(min(findLevelCategory(categoryName) + 1, 3));
            }
        }
    }


    public void incrementWrongAnswer() {wrongAnswer++;}

    public void incrementWrongDirection() {wrongDirection++;}

    public void incrementWrongRank() {wrongRank++;}


    public void incrementCorrectAnswer() {correctAnswer++;}


    public void decreaseCorrectAnswer() {
        if (correctAnswer > 0) {
            correctAnswer--;
        }
    }
}
