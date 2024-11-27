import java.awt.*;
import javax.swing.*;

public class VoterPanel extends JFrame {
    private String voterRollNo; // To track the logged-in voter

    public VoterPanel(String rollNo) {
        this.voterRollNo = rollNo;
        setTitle("Voter Panel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display voter information
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.add(new JLabel("Logged in as: " + voterRollNo));
        add(headerPanel, BorderLayout.NORTH);

        // Election List
        JList<String> electionList = new JList<>(ElectionDatabase.getElections());
        add(new JScrollPane(electionList), BorderLayout.CENTER);

        // Voting Button
        JButton voteButton = new JButton("Vote");
        voteButton.addActionListener(e -> {
            String selectedElection = electionList.getSelectedValue();
            if (selectedElection != null) {
                new VotingPanel(voterRollNo, selectedElection);
            } else {
                JOptionPane.showMessageDialog(this, "Please select an election.");
            }
        });
        add(voteButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}class VotingPanel extends JFrame {
    public VotingPanel(String voterRollNo, String electionName) {
        setTitle("Vote for: " + electionName);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fetch candidates for the election
        String[] candidates = ElectionDatabase.getCandidates(electionName);
        ButtonGroup candidateGroup = new ButtonGroup();
        JPanel candidatesPanel = new JPanel(new GridLayout(candidates.length, 1));

        for (String candidate : candidates) {
            JRadioButton button = new JRadioButton(candidate);
            candidateGroup.add(button);
            candidatesPanel.add(button);
        }
        add(candidatesPanel, BorderLayout.CENTER);

        // Vote Button
        JButton submitVoteButton = new JButton("Submit Vote");
        submitVoteButton.addActionListener(e -> {
            ButtonModel selectedButton = candidateGroup.getSelection();
            if (selectedButton != null) {
                String selectedCandidate = selectedButton.getActionCommand();
                if (ElectionDatabase.vote(voterRollNo, electionName, selectedCandidate)) {
                    JOptionPane.showMessageDialog(this, "Vote successfully cast!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "You have already voted!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a candidate.");
            }
        });
        add(submitVoteButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}

