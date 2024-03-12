package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {

    JButton loginButton, signupButton;
    JTextField usernameField;
    JPasswordField passwordField;
    Choice userTypeChoice;

    Login() {
        setTitle("Login Screen");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300); // Increased size for a larger login panel
        setLocationRelativeTo(null); // Center the window

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome to Electricity DBMS ");
        welcomeLabel.setBounds(50, 20, 300, 30); // Adjusted position and size
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20)); // Set font size and style
        add(welcomeLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 70, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(160, 70, 180, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 110, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 110, 180, 25);
        add(passwordField);

        JLabel userTypeLabel = new JLabel("Login as:");
        userTypeLabel.setBounds(50, 150, 100, 25);
        add(userTypeLabel);

        userTypeChoice = new Choice();
        userTypeChoice.add("Admin");
        userTypeChoice.add("Customer");
        userTypeChoice.setBounds(160, 150, 180, 25);
        add(userTypeChoice);

        loginButton = new JButton("Login");
        loginButton.setBounds(50, 200, 140, 25);
        loginButton.addActionListener(this);
        add(loginButton);

        signupButton = new JButton("Signup");
        signupButton.setBounds(200, 200, 140, 25);
        signupButton.addActionListener(this);
        add(signupButton);

        setVisible(true);
    }

    @Override
public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == loginButton) {
        String susername = usernameField.getText();
        String spassword = new String(passwordField.getPassword()); // Securely fetch the password
        String user = userTypeChoice.getSelectedItem();
        
        Conn c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            c = new Conn();
            String query = "SELECT * FROM login WHERE username = ? AND password = ? AND user = ?";
            pstmt = c.c.prepareStatement(query); // Use PreparedStatement to prevent SQL injection

            pstmt.setString(1, susername);
            pstmt.setString(2, spassword);
            pstmt.setString(3, user);

            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String meter = rs.getString("meter_no"); // Ensure this column exists for all user types, or handle cases where it doesn't
                setVisible(false);
                new Project(user, meter); // Pass user type and meter number to the next screen
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Login");
                usernameField.setText("");
                passwordField.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            // Ensure resources are closed to avoid leaks
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (c != null) c.c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } else if (ae.getSource() == signupButton) {
        setVisible(false);
        new Signup(); // Open Signup screen
    }
}


    public static void main(String[] args) {
        new Login();
    }
}
