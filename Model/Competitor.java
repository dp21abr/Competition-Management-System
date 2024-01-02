package Model;

import java.util.Arrays;

abstract public class Competitor {
    private int competitorNumber;
    private Name name;
    private String country;
    private CompetitorLevel level;
    private String category;
    private int age;
    private String email;
    private int[] scores;
    private double overallScore;


    public Competitor(Name name, String country, CompetitorLevel level, int age, String email, int[] scores) {
        this.name = name;
        this.country = country;
        this.level = level;
        this.age = age;
        this.email = email;
        this.scores = scores;
    }

    public abstract String getCategory();

    public void setCategory(String Category) {
        this.category = Category;
    }

    public abstract double getOverallScore();
    public void setOverallScore(double overallScore) {
        this.overallScore = overallScore;
    }

    public int getCompetitorNumber() {
        return competitorNumber;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public CompetitorLevel getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = CompetitorLevel.valueOf(level);
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {return email;}

    public void setEmail(String email){ this.email = email;}

    public void setAge(int age) {
        this.age = age;
    }

    public void setCompetitorNumber(int compNo) {
        this.competitorNumber = compNo;
    }

    public int[] getScoreArray() {
        return scores;
    }

    public void setScoreArray(int[] scores) {
        this.scores = Arrays.copyOf(scores, scores.length);
    }



    public String getFullDetails() {
        String details = "Competitor number " + competitorNumber +
                ", name " + name.getFullName() +
                ", country " + country +
                ".\n" + name.getFirstAndLastName() +
                " is a " + level +
                " aged " + age +
                " and received these scores: " + Arrays.toString(scores) +
                "\nThis gives him an overall score of " + getOverallScore() +
                "." + "\n";
        return details;
    }

    public String getShortDetails() {
        String shortDetails = "CN " + competitorNumber +
                " (" + name.getInitials() + ") has overall score " + getOverallScore();
        return shortDetails;
    }

}
