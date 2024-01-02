package Model;

public enum CompetitorLevel {
    NOVICE(1, 12, 20),
    INTERMEDIATE(2, 18, 30),
    EXPERT(4, 25, 55);

    private final int scoreWeight;
    private final int minAge;
    private final int maxAge;

    CompetitorLevel(int scoreWeight, int minAge, int maxAge) {
        this.scoreWeight = scoreWeight;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public int getScoreWeight() {
        return scoreWeight;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }
}

