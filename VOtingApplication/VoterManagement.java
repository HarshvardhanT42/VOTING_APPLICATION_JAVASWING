import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VoterManagement extends JFrame {
    private DefaultTableModel voterModel;

    public VoterManagement() {
        setTitle("Voter Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table for displaying voters
        voterModel = new DefaultTableModel(new String[]{"Name", "Roll No", "Password", "Delete"}, 0);
        JTable voterTable = new JTable(voterModel);
        add(new JScrollPane(voterTable), BorderLayout.CENTER);

        // Panel for adding new voter
        JPanel addVoterPanel = new JPanel(new GridLayout(4, 2));
        JTextField nameField = new JTextField();
        JTextField rollNoField = new JTextField();
        JTextField birthdateField = new JTextField();
        JButton addVoterButton = new JButton("Add Voter");

        addVoterPanel.add(new JLabel("Name:"));
        addVoterPanel.add(nameField);
        addVoterPanel.add(new JLabel("Roll No:"));
        addVoterPanel.add(rollNoField);
        addVoterPanel.add(new JLabel("Birthdate (DD-MM-YYYY):"));
        addVoterPanel.add(birthdateField);
        addVoterPanel.add(addVoterButton);

        add(addVoterPanel, BorderLayout.NORTH);

        // Add Voter Logic
        addVoterButton.addActionListener(e -> {
            String name = nameField.getText();
            String rollNo = rollNoField.getText();
            String birthdate = birthdateField.getText();
            if (!name.isEmpty() && !rollNo.isEmpty() && birthdate.matches("\\d{2}-\\d{2}-\\d{4}")) {
                String password = birthdate.replace("-", "");
                voterModel.addRow(new Object[]{name, rollNo, password, "Delete"});
            } else {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });

        // Delete Voter Logic
        voterTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = voterTable.rowAtPoint(e.getPoint());
                int col = voterTable.columnAtPoint(e.getPoint());
                if (col == 3) { // Delete button
                    voterModel.removeRow(row);
                }
            }
        });

        setVisible(true);
    }
}
