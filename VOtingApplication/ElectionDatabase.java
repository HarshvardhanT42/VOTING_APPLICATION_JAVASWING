import java.util.*;

class ElectionDatabase {
    private static Map<String, List<String>> elections = new HashMap<>();
    private static Map<String, Map<String, Integer>> votes = new HashMap<>();
    private static Set<String> voterHistory = new HashSet<>();

    public static String[] getElections() {
        return elections.keySet().toArray(new String[0]);
    }

    public static void addCandidate(String electionName, String candidateName, String rollNo) {
        elections.computeIfAbsent(electionName, k -> new ArrayList<>()).add(candidateName);
    }

    public static String[] getCandidates(String electionName) {
        return elections.getOrDefault(electionName, new ArrayList<>()).toArray(new String[0]);
    }

    public static boolean startElection(String electionName) {
        if (!votes.containsKey(electionName)) {
            votes.put(electionName, new HashMap<>());
            return true;
        }
        return false;
    }

    public static boolean vote(String voterRollNo, String electionName, String candidate) {
        if (!voterHistory.add(voterRollNo + electionName)) {
            return false; // Already voted
        }

        votes.get(electionName).merge(candidate, 1, Integer::sum);
        return true;
    }

    public static String endElection(String electionName) {
        Map<String, Integer> results = votes.get(electionName);
        return results.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
    }
}
