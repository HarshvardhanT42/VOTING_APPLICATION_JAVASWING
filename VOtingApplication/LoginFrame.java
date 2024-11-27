import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Create UI components
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        // Dropdown for login selection (Admin or Voter)
        JLabel roleLabel = new JLabel("Select Role:");
        String[] roles = {"Admin", "Voter"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);

        // Add components to frame
        add(roleLabel);
        add(roleComboBox);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);

        // ActionListener for login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedRole = (String) roleComboBox.getSelectedItem();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (selectedRole.equals("Admin")) {
                    if (username.equals("admin") && password.equals("admin123")) {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Admin Login Successful!");
                        new ElectionManagement();  // Open Election Management window
                        dispose();  // Close the login window after successful login
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Invalid Admin Credentials!");
                    }
                } else if (selectedRole.equals("Voter")) {
                    // For voter login: Need to ask for roll number and password (birthdate-based)
                    String rollNo = username;  // Assuming username field has roll number
                    String enteredPassword = password;
                    // Assuming you have a method to validate voter credentials
                    if (isValidVoter(rollNo, enteredPassword)) {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Voter Login Successful!");
                        new VoterDashboard();  // Open Voter Dashboard window
                        dispose();  // Close the login window after successful login
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Invalid Voter Credentials!");
                    }
                }
            }
        });

        setVisible(true);
    }

    // This method will check if the entered rollNo and password match the stored voter info
    private boolean isValidVoter(String rollNo, String enteredPassword) {
        // This is just a mockup. Replace with your actual voter validation logic.
        // For example, checking from a database or a list of voters
        // Password is birthdate in DDMMYYYY format, assuming simple storage like this:
        return enteredPassword.equals("01011990");  // Example: birthdate password for demo
    }

    public static void main(String[] args) {
        new LoginFrame();
    }

    private static class VoterDashboard {

        public VoterDashboard() {
        }
    }
}
