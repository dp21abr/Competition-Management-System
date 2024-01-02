package Model;

import java.util.Arrays;

public class CyclingCompetitor extends Competitor {

    public CyclingCompetitor(Name name, String country, CompetitorLevel level,  int age, String email, int[] scores) {
        super(name, country, level, age, email, scores);
    }
    public String getCategory() {
        return "Cycling";
    }

    public double getOverallScore() {
        int n = getLevel().getScoreWeight();

        if (n > getScoreArray().length) {
            n = getScoreArray().length;  // Adjust to avoid index out of bounds
        }

        // Sort scores in descending order
        int[] sortedScores = Arrays.copyOf(getScoreArray(), getScoreArray().length);
        Arrays.sort(sortedScores);
        int sumTopNScores = 0;

        // Calculate the sum of the top n scores
        for (int i = getScoreArray().length - 1; i >= getScoreArray().length - n; i--) {
            sumTopNScores += sortedScores[i];
        }

        // Calculate the average of the top n scores
        return (double) sumTopNScores / n;
    }

    @Override
    public String getFullDetails() {
        String details = "Competitor number " + getCompetitorNumber() +
                ", name " + getName().getFullName() +
                ", country " + getCountry() +
                ".\n" + getName().getFirstAndLastName() +
                " is a " + getLevel() +
                " aged " + getAge() +
                " and received these scores: " + Arrays.toString(getScoreArray()) +
                "\nThis gives him an overall score of " + getOverallScore() +
                " specific details for Cycling Competitor.";
        return details;
    }

    @Override
    public String getShortDetails() {
        String shortDetails = "CN " + getCompetitorNumber() +
                " (" + getName().getInitials() + ") has overall score " + getOverallScore() +
                " specific short details for Cycling Competitor.";
        return shortDetails;
    }
}

