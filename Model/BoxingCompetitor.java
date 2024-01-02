package Model;

import java.util.Arrays;
public class BoxingCompetitor extends Competitor {
    public BoxingCompetitor(Name name, String country, CompetitorLevel level,  int age, String email, int[] scores) {
        super(name, country, level, age, email, scores);
    }
    public String getCategory() {
        return "Boxing";
    }

    public double getOverallScore() {
        double sum = Arrays.stream(getScoreArray()).sum();
        return sum / getScoreArray().length;
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
                " specific details for Boxing Competitor.";
        return details;
    }

    @Override
    public String getShortDetails() {
        String shortDetails = "CN " + getCompetitorNumber() +
                " (" + getName().getInitials() + ") has overall score " + getOverallScore() +
                " specific short details for Boxing Competitor.";
        return shortDetails;
    }

}

