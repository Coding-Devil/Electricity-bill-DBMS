package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class Project extends JFrame implements ActionListener {

    String atype, meter;

    Project(String atype, String meter) {
        this.atype = atype;
        this.meter = meter;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Dashboard Panel");

        // Custom panel with gradient background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(135, 206, 235); // Light Sky Blue
                Color color2 = new Color(25, 25, 112); // Midnight Blue
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
        JLabel welcomeLabel = new JLabel("Welcome, " + (atype.equals("Admin") ? "Admin!" : "Customer!"), JLabel.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 32));
        welcomeLabel.setForeground(Color.BLACK);
        backgroundPanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.setOpaque(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

        // Check user type and add buttons accordingly
        if (atype.equals("Admin")) {
            addButton(buttonPanel, "New Customer", "/icon/newuser.png");
            addButton(buttonPanel, "Customer Details", "/icon/details.png");
            addButton(buttonPanel, "Deposit Details", "/icon/deposit.png");
            addButton(buttonPanel, "Calculate Bill", "/icon/bill.png");
        } else if (atype.equals("Customer")) {
            addButton(buttonPanel, "View Information", "/icon/view.png");
            addButton(buttonPanel, "Update Information", "/icon/update.png");
            addButton(buttonPanel, "Pay Bill", "/icon/card.png");
            addButton(buttonPanel, "Generate Bill", "/icon/receipt.png");
        }

        centralPanel.add(Box.createVerticalGlue());
        centralPanel.add(buttonPanel);
        centralPanel.add(Box.createVerticalGlue());

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        backgroundPanel.add(exitButton, BorderLayout.SOUTH);

        backgroundPanel.add(centralPanel, BorderLayout.CENTER);

        add(backgroundPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addButton(JPanel panel, String text, String iconPath) {
    URL imgUrl = getClass().getResource(iconPath);
    if (imgUrl != null) {
        ImageIcon icon = new ImageIcon(imgUrl);
        JButton button = new JButton(text, icon);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.BOTTOM);
        button.setPreferredSize(new Dimension(200, 200)); // Adjust the width and height as needed
        button.setFont(new Font("Serif", Font.BOLD, 16)); // Optional: Adjust the font size of the button text
        button.addActionListener(this);
        panel.add(button);
    } else {
        System.err.println("Resource not found: " + iconPath);
    }
}




@Override
public void actionPerformed(ActionEvent ae) {
    String msg = ae.getActionCommand();
    switch (msg) {
        case "New Customer":
            // Assuming NewCustomer is a class that handles this action
            new NewCustomer();
            break;
        case "Customer Details":
            // Assuming CustomerDetails is a class that handles this action
            new CustomerDetails();
            break;
        case "Deposit Details":
            // Assuming DepositDetails is a class that handles this action
            new DepositDetails();
            break;
        case "Calculate Bill":
            // Assuming CalculateBill is a class that handles this action
            new CalculateBill();
            break;
        case "View Information":
            // Assuming ViewInformation is a class that takes 'meter' as argument
            new ViewInformation(meter);
            break;
        case "Update Information":
            // Assuming UpdateInformation is a class that takes 'meter' as argument
            new UpdateInformation(meter);
            break;
        case "Pay Bill":
            // Assuming PayBill is a class that takes 'meter' as argument
            new PayBill(meter);
            break;
        case "Generate Bill":
            // Assuming GenerateBill is a class that takes 'meter' as argument
            new GenerateBill(meter);
            break;
        case "Exit":
            setVisible(false); // Hide this window
            // Assuming Login is the class that shows the login screen
            new Login();
            break;
        default:
            // Handle any other unexpected action commands here
            System.out.println("Unexpected action: " + msg);
            break;
    }
}


    public static void main(String[] args) {
        new Project("Admin", ""); // For testing, use "Admin" or "Customer"
    }
}
