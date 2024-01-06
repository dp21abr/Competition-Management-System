package Model;

import java.io.*;
import java.util.*;


public class CompetitorList {
    private ArrayList<Competitor> competitors;

    public CompetitorList() {
        this.competitors = new ArrayList<>();
    }

    public ArrayList<Competitor> getCompetitors() {
        return competitors;
    }

    public Competitor getCompetitorWithHighestScore() {
        Competitor highestScoreCompetitor = null;
        double maxScore = Double.MIN_VALUE;

        for (Competitor competitor : competitors) {
            double overallScore = competitor.getOverallScore();
            if (overallScore > maxScore) {
                maxScore = overallScore;
                highestScoreCompetitor = competitor;
            }
        }

        return highestScoreCompetitor;
    }

    public HashMap<String, Double> getStatisticsReport() {
        HashMap<String, Double> statisticsReport = new HashMap<>();
        double totalScore = 0;
        double minScore = Double.MAX_VALUE;
        double maxScore = Double.MIN_VALUE;

        for (Competitor competitor : competitors) {
            double overallScore = competitor.getOverallScore();
            totalScore += overallScore;

            if (overallScore < minScore) {
                minScore = overallScore;
            }

            if (overallScore > maxScore) {
                maxScore = overallScore;
            }
        }

        double averageScore = totalScore / competitors.size();

        statisticsReport.put("Total Score", totalScore);
        statisticsReport.put("Average Score", averageScore);
        statisticsReport.put("Maximum Score", maxScore);
        statisticsReport.put("Minimum Score", minScore);

        return statisticsReport;
    }

    public HashMap<Integer, Integer> getFrequencyReport() {
        HashMap<Integer, Integer> frequencyReport = new HashMap<>();

        for (Competitor competitor : competitors) {
            int[] scores = competitor.getScoreArray();
            for (int score : scores) {
                frequencyReport.put(score, frequencyReport.getOrDefault(score, 0) + 1);
            }
        }

        return frequencyReport;
    }

    public String getCompetitorsDetails() {
        StringBuilder competitorsDetails = new StringBuilder("Competitors Details:\n");

        for (Competitor competitor : competitors) {
            competitorsDetails.append(competitor.getFullDetails()).append("\n");
        }

        return competitorsDetails.toString();
    }

    public List<String> getResultsByCategory(String category) {
        List<String> results = new ArrayList<>();

        for (Competitor competitor : competitors) {
            if (competitor.getCategory().equalsIgnoreCase(category)) {
                String result = "Competitor Number: " + competitor.getCompetitorNumber() +
                        ", Name: " + competitor.getName().getFullName() +
                        ", Overall Score: " + competitor.getOverallScore();
                results.add(result);
            }
        }

        return results;
    }

    // Method to view short details of a competitor using competitor number as a parameter
    public String viewShortDetailsByNumber(int competitorNumber) {
        Competitor competitor = getCompetitorByNumber(competitorNumber);
        if (competitor != null) {
            return competitor.getShortDetails();
        } else {
            return "Competitor not found with number: " + competitorNumber;
        }
    }

    // Method to view full details of a competitor using competitor number as a parameter
    public String viewFullDetailsByNumber(int competitorNumber) {
        Competitor competitor = getCompetitorByNumber(competitorNumber);
        if (competitor != null) {
            return competitor.getFullDetails();
        } else {
            return "Competitor not found with number: " + competitorNumber;
        }
    }

    // Method to view details of all competitors in a given category
    public String viewDetailsByCategory(String category) {
        StringBuilder categoryDetails = new StringBuilder();
        int count = 1;
        for (Competitor competitor : competitors) {
            if (competitor.getCategory().equalsIgnoreCase(category)) {
                categoryDetails.append("\n").append(count).append(".").append(competitor.getFullDetails()).append("\n");
                count++;
            }
        }
        // Print or display the accumulated details
        return categoryDetails.toString();
    }

    public String viewDetailsByLevel(CompetitorLevel level) {
        StringBuilder levelDetails = new StringBuilder();
        int count = 1;
        for (Competitor competitor : competitors) {
            if (competitor.getLevel() == level) {
                levelDetails.append("\n").append(count).append(".").append(competitor.getFullDetails()).append("\n");
                count++;
            }
        }
        // Print or display the accumulated details
        return levelDetails.toString();
    }

    public String viewDetailsByAge(int age) {
        StringBuilder ageDetails = new StringBuilder();
        int count = 1;
        for (Competitor competitor : competitors) {
            if (competitor.getAge() == age) {
                ageDetails.append("\n").append(count).append(".").append(competitor.getFullDetails()).append("\n");
                count++;
            }
        }
        // Print or display the accumulated details
        return ageDetails.toString();
    }


    public void generateFinalReport(String outputFilename) {
        try (PrintWriter writer = new PrintWriter(outputFilename)) {
            // Get competitors' details for the table
            String tableDetails = getCompetitorsDetails();

            // Get details for the competitor with the highest overall score
            Competitor highestScoreCompetitor = getCompetitorWithHighestScore();
            String highestScoreDetails = (highestScoreCompetitor != null) ? highestScoreCompetitor.getFullDetails() : "No competitor found.";

            // Get statistics report
            HashMap<String, Double> statisticsReport = getStatisticsReport();
            String summaryStatistics = formatStatisticsReport(statisticsReport);

            // Get frequency report
            HashMap<Integer, Integer> frequencyReport = getFrequencyReport();
            String frequencyDetails = formatFrequencyReport(frequencyReport);

            // Write the gathered details to the file
            writer.println(tableDetails);
            writer.println("\nCompetitor with Highest Overall Score:");
            writer.println(highestScoreDetails);
            writer.println("\nSummary Statistics:");
            writer.println(summaryStatistics);
            writer.println("\nFrequency Report:");
            writer.println(frequencyDetails);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String formatStatisticsReport(HashMap<String, Double> statisticsReport) {
        StringBuilder formattedReport = new StringBuilder();
        for (HashMap.Entry<String, Double> entry : statisticsReport.entrySet()) {
            formattedReport.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return formattedReport.toString();
    }

    private String formatFrequencyReport(HashMap<Integer, Integer> frequencyReport) {
        StringBuilder formattedReport = new StringBuilder();
        for (HashMap.Entry<Integer, Integer> entry : frequencyReport.entrySet()) {
            formattedReport.append("Score ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" times\n");
        }
        return formattedReport.toString();
    }

    public Competitor getCompetitorByNumber(int competitorNumber) {
        for (Competitor competitor : competitors) {
            if (competitor.getCompetitorNumber() == competitorNumber) {
                return competitor;
            }
        }
        return null; // Return null if not found
    }

    public void registerCompetitor(Competitor competitor) {
        if (validateRegistration(competitor) == 1) {
            competitor.setCompetitorNumber(generateNewCompetitorNumber());
            competitors.add(competitor);
            System.out.println("Competitor registered successfully.");
        } else {
            System.out.println("Failed to register competitor due to validation errors.");
        }
    }

    public int validateRegistration(Competitor newCompetitor) {
        // Check for missing fields in the new competitor
        if (newCompetitor.getName() == null ||
                newCompetitor.getCountry() == null || newCompetitor.getLevel() == null ||
                newCompetitor.getAge() <= 0 || newCompetitor.getScoreArray() == null ||
                newCompetitor.getCategory() == null) {
            System.out.println("Error: Please fill out all fields.");
            return 0;
        }

        // Check for existing competitor with the same email and category
        for (Competitor existingCompetitor : competitors) {
            if (existingCompetitor != newCompetitor &&
                    existingCompetitor.getCategory().equals(newCompetitor.getCategory()) &&
                    existingCompetitor.getEmail().equals(newCompetitor.getEmail())) {
                System.out.println("Error: Competitor with the same email and category already exists. Registration refused.");
                return 0;
            }
            // Check for existing competitor with the same email but different category
            if (existingCompetitor != newCompetitor &&
                    !existingCompetitor.getCategory().equals(newCompetitor.getCategory()) &&
                    existingCompetitor.getName().equals(newCompetitor.getName())) {
                System.out.println("Competitor with the same email already exists for a different category.");
                System.out.println("Allocating a different competitor number for this category.");
                // newCompetitor.setCompetitorNumber(generateNewCompetitorNumber());
                return 1;
            }
        }
        // Check age compatibility with level
        if (newCompetitor.getAge() < newCompetitor.getLevel().getMinAge() ||
                newCompetitor.getAge() > newCompetitor.getLevel().getMaxAge()) {
            System.out.println("Error: Competitor's age is incompatible with the selected level.");
            System.out.println("Please resubmit the form for a different level.");
            return 0;
        }

        // If everything is okay, accept the registration
        System.out.println("Registration accepted and they are allocated a competitor number");
        return 1;
    }

    private int generateNewCompetitorNumber() {
        try (BufferedReader reader = new BufferedReader(new FileReader("competitordata.txt"))) {
            String lastLine = null;

            // Read the last line from the file
            String line;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }

            if (lastLine != null) {
                // Split the last line into fields using commas as separators
                String[] data = lastLine.split(",");

                // Extract the competitor number from the last line
                int lastCompetitorNumber = Integer.parseInt(data[0].trim());

                // Increment the last competitor number to get a new one
                int newCompetitorNumber = lastCompetitorNumber + 1;

                return newCompetitorNumber;
            } else {
                // If the file is empty, start with competitor number 1000
                return 1000;
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., log the error, show a message, etc.)
            return -1; // Return a sentinel value indicating an error
        }
    }

    public void loadCompetitorsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Split line into parts
                String values[] = line.split(",(?![^\\[]*\\])");

                // Ensure the case-insensitive match for CompetitorLevel
                CompetitorLevel level;
                try {
                    // Trim the string to remove leading/trailing spaces
                    level = CompetitorLevel.valueOf(values[3].trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid CompetitorLevel: " + values[3]);
                    return;
                }

                // Extract other values
                int compNo = Integer.parseInt(values[0].trim());
                Name name = new Name(values[1].trim());
                String country = values[2].trim();
                int age = Integer.parseInt(values[4].trim());
                String email = values[5].trim();
                double overallScore = Double.parseDouble(values[8].trim());

                // Extract scores from the array excluding the last element
                String scoresString = values[6].trim();  // assuming scores start at index 6
                scoresString = scoresString.substring(1, scoresString.length() - 1);  // remove leading '[' and trailing ']'
                int[] scores = Arrays.stream(scoresString.split(","))
                        .map(String::trim)
                        .mapToInt(Integer::parseInt)
                        .toArray();

                // Determine the category and create the appropriate competitor type
                Competitor competitor;
                if (values[values.length - 2].trim().equals("Boxing")) {
                    competitor = new BoxingCompetitor(name, country, level, age, email, scores);
                } else if (values[values.length - 2].trim().equals("Cycling")) {
                    competitor = new CyclingCompetitor(name, country, level, age, email, scores);
                } else {
                    // Handle unknown category or provide appropriate fallback
                    System.out.println("Unknown category: " + values[values.length - 1]);
                    return;
                }

                competitor.setCompetitorNumber(compNo);
                addDetails(competitor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDetails(Competitor details)
    {
        competitors.add(details);
    }
    public void saveCompetitors(String file) {
        try {
            // competitor model
            Competitor competitor;
            String saveData;

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < competitors.size(); i++) {
                competitor = competitors.get(i);
                saveData = competitor.getCompetitorNumber() + ", " +
                        competitor.getName().getFullName() + ", " + competitor.getCountry() + ", " +
                        competitor.getLevel() + ", " + competitor.getAge() + ", " +
                        competitor.getEmail() + ", " + Arrays.toString(competitor.getScoreArray()) +
                        ", " + competitor.getCategory() + ", " + competitor.getOverallScore();
                bufferedWriter.write(saveData);
                bufferedWriter.newLine();
            }

            // prevents memory leak
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Inside the CompetitorList class
    public void editCompetitorDetails(int competitorNumber, Name newName, String newCountry, String newLevel, int newAge, String newEmail, int[] newScores, String Category) {
        Competitor competitor = getCompetitorByNumber(competitorNumber);

        if (competitor != null) {
            // Update the competitor's details
            competitor.setCompetitorNumber(competitorNumber);
            competitor.setName(newName);
            competitor.setCountry(newCountry);
            competitor.setLevel(newLevel);
            competitor.setAge(newAge);
            competitor.setEmail(newEmail);
            competitor.setScoreArray(newScores);
            competitor.setCategory(Category);

            // Save the updated list to the file
            saveCompetitors("competitordata.txt");
        } else {
            System.out.println("Competitor not found with number: " + competitorNumber);
        }
    }

    public void removeCompetitor(int competitorNumber) {
        // Get the list of competitors
        ArrayList<Competitor> competitors = getCompetitors();

        // Find the competitor with the specified competitorNumber
        Competitor competitorToRemove = null;
        for (Competitor competitor : competitors) {
            if (competitor.getCompetitorNumber() == competitorNumber) {
                competitorToRemove = competitor;
                break;
            }
        }

        // Remove the competitor if found
        if (competitorToRemove != null) {
            competitors.remove(competitorToRemove);
            saveCompetitors("competitordata.txt");
            System.out.println("Competitor removed successfully.");
        } else {
            System.out.println("Competitor not found with number: " + competitorNumber);
        }
    }

}

