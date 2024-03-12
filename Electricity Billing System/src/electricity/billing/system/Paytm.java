package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Paytm extends JFrame implements ActionListener {
    
    String meter;
    JButton back;
    
    Paytm(String meter) {
        this.meter = meter;
        
        setLayout(null);
        
        // Adjust window dimensions for better spacing
        int windowWidth = 500; // Increased width for a bit more space
        int windowHeight = 400; // Increased height to prevent compression
        
        // Load and position the QR code
        ImageIcon qrCodeIcon = new ImageIcon(ClassLoader.getSystemResource("icon/qr.png"));
        int qrCodeX = (windowWidth - qrCodeIcon.getIconWidth()) / 2; // Center horizontally
        int qrCodeY = 60; // Position towards the top but leave space for text
        JLabel qrCodeLabel = new JLabel(qrCodeIcon);
        qrCodeLabel.setBounds(qrCodeX, qrCodeY, qrCodeIcon.getIconWidth(), qrCodeIcon.getIconHeight());
        add(qrCodeLabel);
        
        // Instruction label
        JLabel instructionLabel = new JLabel("Scan the QR code for payment", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        instructionLabel.setBounds(0, 20, windowWidth, 25); // Top centered
        add(instructionLabel);
        
        // UPI icon below QR code
        ImageIcon upiIcon = new ImageIcon(ClassLoader.getSystemResource("icon/upi.png"));
        int upiIconX = (windowWidth - upiIcon.getIconWidth()) / 2; // Center horizontally
        int upiIconY = qrCodeY + qrCodeIcon.getIconHeight() + 10; // Just below QR code
        JLabel upiIconLabel = new JLabel(upiIcon);
        upiIconLabel.setBounds(upiIconX, upiIconY, upiIcon.getIconWidth(), upiIcon.getIconHeight());
        add(upiIconLabel);
        
        // Back button
        back = new JButton("Back");
        int backX = (windowWidth - 100) / 2; // Center horizontally
        int backY = upiIconY + upiIcon.getIconHeight() + 20; // Below UPI icon
        back.setBounds(backX, backY, 100, 30);
        back.addActionListener(this);
        add(back);
        
        // Setup JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(windowWidth, windowHeight);
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
            // Assuming PayBill is another frame you want to show
            new PayBill(meter).setVisible(true); 
        }
    }
    
    public static void main(String[] args) {
        new Paytm("");
    }
}
