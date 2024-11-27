import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ElectionManagement extends JFrame {
    private Map<String, DefaultTableModel> electionTables = new HashMap<>();
    private String selectedElection = null;

    public ElectionManagement() {
        setTitle("Election Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for creating elections
        JPanel createElectionPanel = new JPanel(new GridLayout(3, 1));
        JTextField electionNameField = new JTextField();
        JButton createElectionButton = new JButton("Create Election");

        createElectionPanel.add(new JLabel("Enter Election Name:"));
        createElectionPanel.add(electionNameField);
        createElectionPanel.add(createElectionButton);

        add(createElectionPanel, BorderLayout.NORTH);

        // Election List Table
        DefaultTableModel electionListModel = new DefaultTableModel(new String[]{"Election Name"}, 0);
        JTable electionListTable = new JTable(electionListModel);
        add(new JScrollPane(electionListTable), BorderLayout.CENTER);

        // View Election Button
        JPanel actionPanel = new JPanel();
        JButton viewElectionButton = new JButton("View Selected Election");
        actionPanel.add(viewElectionButton);
        add(actionPanel, BorderLayout.SOUTH);

        // Create Election Logic
        createElectionButton.addActionListener(e -> {
            String electionName = electionNameField.getText();
            if (!electionName.isEmpty()) {
                if (!electionTables.containsKey(electionName)) {
                    electionTables.put(electionName, new DefaultTableModel(new String[]{"Candidate Name", "Roll No", "Vote Count", "Delete"}, 0));
                    electionListModel.addRow(new Object[]{electionName});
                    JOptionPane.showMessageDialog(this, "Election created successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Election name already exists!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid election name!");
            }
        });

        // View Selected Election
        viewElectionButton.addActionListener(e -> {
            int selectedRow = electionListTable.getSelectedRow();
            if (selectedRow != -1) {
                selectedElection = (String) electionListModel.getValueAt(selectedRow, 0);
                openElectionManagementPage(selectedElection);
            } else {
                JOptionPane.showMessageDialog(this, "Please select an election to view!");
            }
        });

        setVisible(true);
    }

    // Manage Election Details (Add Candidates, Start/End Election)
    private void openElectionManagementPage(String electionName) {
        JFrame electionFrame = new JFrame("Manage Election: " + electionName);
        electionFrame.setSize(800, 600);
        electionFrame.setLayout(new BorderLayout());

        DefaultTableModel model = electionTables.get(electionName);
        JTable candidateTable = new JTable(model);
        electionFrame.add(new JScrollPane(candidateTable), BorderLayout.CENTER);

        // Add Candidate Panel
        JPanel addCandidatePanel = new JPanel(new GridLayout(3, 2));
        JTextField candidateNameField = new JTextField();
        JTextField rollNoField = new JTextField();
        JButton addCandidateButton = new JButton("Add Candidate");

        addCandidatePanel.add(new JLabel("Candidate Name:"));
        addCandidatePanel.add(candidateNameField);
        addCandidatePanel.add(new JLabel("Roll No:"));
        addCandidatePanel.add(rollNoField);
        addCandidatePanel.add(addCandidateButton);

        electionFrame.add(addCandidatePanel, BorderLayout.NORTH);

        // Start and End Election Buttons
        JPanel actionPanel = new JPanel();
        JButton startElectionButton = new JButton("Start Election");
        JButton endElectionButton = new JButton("End Election");
        actionPanel.add(startElectionButton);
        actionPanel.add(endElectionButton);
        electionFrame.add(actionPanel, BorderLayout.SOUTH);

        // Add Candidate Logic
        addCandidateButton.addActionListener(e -> {
            String candidateName = candidateNameField.getText();
            String rollNo = rollNoField.getText();
            if (!candidateName.isEmpty() && !rollNo.isEmpty()) {
                model.addRow(new Object[]{candidateName, rollNo, 0, "Delete"});
            } else {
                JOptionPane.showMessageDialog(electionFrame, "Please enter valid candidate details!");
            }
        });

        // Delete Candidate Logic
        candidateTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = candidateTable.rowAtPoint(e.getPoint());
                int col = candidateTable.columnAtPoint(e.getPoint());
                if (col == 3) { // Delete button
                    model.removeRow(row);
                }
            }
        });

        // Start Election Logic
        startElectionButton.addActionListener(e -> JOptionPane.showMessageDialog(electionFrame, "Election '" + electionName + "' started!"));

        // End Election Logic
        endElectionButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(electionFrame, "Election '" + electionName + "' ended!");
            Map<String, Integer> results = new HashMap<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                String candidateName = (String) model.getValueAt(i, 0);
                int votes = (int) model.getValueAt(i, 2);
                results.put(candidateName, votes);
            }
            String winner = results.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("No winner");
            JOptionPane.showMessageDialog(electionFrame, "Winner: " + winner);
        });

        electionFrame.setVisible(true);
    }
}
