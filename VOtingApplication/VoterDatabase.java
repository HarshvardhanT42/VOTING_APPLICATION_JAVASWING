import java.util.*;

public class VoterDatabase {
    private static List<Voter> voters = new ArrayList<>();

    public static void addVoter(Voter voter) {
        voters.add(voter);
    }

    public static void deleteVoter(int index) {
        if (index >= 0 && index < voters.size()) {
            voters.remove(index);
        }
    }

    public static List<Voter> getAllVoters() {
        return voters;
    }

    static boolean validateVoter(String userId, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static class Voter {
        private String name;
        private String rollNo;
        private String password;

        public Voter(String name, String rollNo, String password) {
            this.name = name;
            this.rollNo = rollNo;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public String getRollNo() {
            return rollNo;
        }

        public String getPassword() {
            return password;
        }
    }
}
