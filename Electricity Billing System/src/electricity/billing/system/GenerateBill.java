package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.print.PrinterException; // Import for printing support

public class GenerateBill extends JFrame implements ActionListener {

    String meter;
    JButton bill, print; // Declare a print JButton
    Choice cmonth;
    JTextArea area;

    GenerateBill(String meter) {
        this.meter = meter;

        setSize(400, 800);
        setLocation(550, 30);

        setLayout(new BorderLayout());

        JPanel panel = new JPanel();

        JLabel heading = new JLabel("Generate Bill");
        JLabel meternumber = new JLabel(meter);

        cmonth = new Choice();

        // Add months to the choice
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        for (String month : months) {
            cmonth.add(month);
        }

        area = new JTextArea(50, 15);
        area.setText("\n\n\t--------Click on the---------\n\t Generate Bill Button to get\n\tthe bill of the Selected Month");
        area.setFont(new Font("Arial", Font.ITALIC, 14));

        JScrollPane pane = new JScrollPane(area);

        bill = new JButton("Generate Bill");
        bill.addActionListener(this);

        print = new JButton("Print Bill"); // Initialize the print JButton
        print.addActionListener(this); // Add ActionListener to the print button

        panel.add(heading);
        panel.add(meternumber);
        panel.add(cmonth);
        add(panel, "North");

        add(pane, "Center");

        JPanel bottomPanel = new JPanel(); // Create a panel to hold buttons
        bottomPanel.add(bill);
        bottomPanel.add(print); // Add the print button to the panel
        add(bottomPanel, "South"); // Add the panel to the JFrame

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == bill) {
            try {
            Conn c = new Conn();
            
            String month = cmonth.getSelectedItem();
            
            area.setText("\t * GO Power Supply Limited *\n  ELECTRICITY BILL GENERATED FOR THE MONTH \n\t OF "+month+", 2023\n\n");
            
            ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '"+meter+"'");
        
            if(rs.next()) {
                area.append("\n    Customer Name :      " + rs.getString("name"));
                area.append("\n    Meter Number :       " + rs.getString("meter_no"));
                area.append("\n    Address :\t          " + rs.getString("address"));
                area.append("\n    City :               " + rs.getString("city"));
                area.append("\n    State :              " + rs.getString("state"));
                area.append("\n    Email :              " + rs.getString("email"));
                area.append("\n    Phone :              " + rs.getString("phone"));
                area.append("\n-----------------------------------------------------------");
                area.append("\n\n Meter info : \n");
            }
            
            rs = c.s.executeQuery("select * from meter_info where meter_no = '"+meter+"'");
        
            if(rs.next()) {
                area.append("\n    Meter Location:      " + rs.getString("meter_location"));
                area.append("\n    Meter Type:          " + rs.getString("meter_type"));
                area.append("\n    Phase Code:          " + rs.getString("phase_code"));
                area.append("\n    Bill Type:           " + rs.getString("bill_type"));
                area.append("\n    Days:                " + rs.getString("days"));
                area.append("\n-----------------------------------------------------------");
                area.append("\n");
            }
            
            rs = c.s.executeQuery("select * from tax");
        
            if(rs.next()) {
                area.append("\n Tax amount :\n");
                area.append("\n    Cost Per Unit:       " + rs.getString("cost_per_unit"));
                area.append("\n    Meter Rent:          " + rs.getString("meter_rent"));
                area.append("\n    Service Charge:      " + rs.getString("service_charge"));
                area.append("\n    Service Tax:         " + rs.getString("service_tax"));
                area.append("\n    Fixed min charges:   " + rs.getString("fixed_tax"));
                area.append("\n");
            }
            
            rs = c.s.executeQuery("select * from bill where meter_no = '"+meter+"' and month='"+month+"'");
        
            if(rs.next()) {
                area.append("\n Billing Units :\n");
                area.append("\n    Current Month:       " + rs.getString("month"));
                area.append("\n    Units Consumed:      " + rs.getString("units"));
                area.append("\n    Total Charges:       " + rs.getString("totalbill"));
                area.append("\n-----------------------------------------------------------");
                area.append("\n    Total Payable:    Rs. " + rs.getString("totalbill"));
                area.append("\n\n Thank you :)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        } else if (ae.getSource() == print) {
            // Implement print functionality
            try {
                area.print(); // Attempt to print the JTextArea content
            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, "Printing error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new GenerateBill("");
    }
}
