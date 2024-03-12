package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class ViewInformation extends JFrame implements ActionListener {

    JButton cancel;
    
    ViewInformation(String meter) {
        setTitle("Customer Information");
        setBounds(300, 90, 500, 400); // Window size
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("VIEW CUSTOMER INFORMATION ", SwingConstants.CENTER);
        heading.setFont(new Font("Tahoma", Font.BOLD, 18));
        heading.setBounds(50, 20, 400, 30); // Centered and added space from top
        add(heading);
        
        // Labels and positions
        String[] details = {"Name:\t", "Meter Number:\t", "Address:\t", "City:\t", "State:\t", "Email:\t", "Phone:\t"};
        int y = 80; // Increased initial y position for spacing from heading
        
        for (String detail : details) {
            JLabel label = new JLabel(detail, SwingConstants.RIGHT);
            label.setFont(new Font("Tahoma", Font.PLAIN, 16));
            // Calculate x position based on window width to center the labels
            label.setBounds(125, y, 120, 20);
            add(label);
            
            JLabel valueLabel = new JLabel("");
            valueLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
            // Align with label positions and provide adequate space
            valueLabel.setBounds(250, y, 200, 20); // Adjusted to ensure center alignment
            add(valueLabel);
            y += 30; // Spacing between rows
        }
        
        cancel = new JButton("Back");
        cancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        // Move to the left and adjust to be centered vertically at the bottom
        cancel.setBounds(180, 300, 100, 25); // Further to the left and bottom
        cancel.addActionListener(this);
        add(cancel);
        
        // Database operation
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '" + meter + "'");
            if(rs.next()) {
                ((JLabel)getContentPane().getComponent(2)).setText(rs.getString("name"));
                ((JLabel)getContentPane().getComponent(4)).setText(rs.getString("meter_no"));
                ((JLabel)getContentPane().getComponent(6)).setText(rs.getString("address"));
                ((JLabel)getContentPane().getComponent(8)).setText(rs.getString("city"));
                ((JLabel)getContentPane().getComponent(10)).setText(rs.getString("state"));
                ((JLabel)getContentPane().getComponent(12)).setText(rs.getString("email"));
                ((JLabel)getContentPane().getComponent(14)).setText(rs.getString("phone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == cancel) {
            this.dispose();
        }
    }
    
    public static void main(String[] args) {
        new ViewInformation("YourMeterNumberHere");
    }
}
