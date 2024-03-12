package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class PayBill extends JFrame implements ActionListener {

    Choice cmonth;
    JButton pay, back;
    String meter;
    JLabel meternumber, labelname, labelunits, labeltotalbill, labelstatus;

    PayBill(String meter) {
        this.meter = meter;
        setLayout(null);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JLabel heading = new JLabel("Electricity Bill");
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setBounds(120, 5, 400, 30);
        add(heading);

        JLabel lblmeternumber = new JLabel("Meter Number:");
        lblmeternumber.setBounds(35, 80, 200, 20);
        add(lblmeternumber);

        meternumber = new JLabel(meter);
        meternumber.setBounds(300, 80, 200, 20);
        add(meternumber);

        JLabel lblname = new JLabel("Name:");
        lblname.setBounds(35, 140, 200, 20);
        add(lblname);

        labelname = new JLabel("");
        labelname.setBounds(300, 140, 200, 20);
        add(labelname);

        JLabel lblmonth = new JLabel("Month:");
        lblmonth.setBounds(35, 200, 200, 20);
        add(lblmonth);

        cmonth = new Choice();
        cmonth.setBounds(300, 200, 100, 20);
        cmonth.add("January");
        cmonth.add("February");
        cmonth.add("March");
        cmonth.add("April");
        cmonth.add("May");
        cmonth.add("June");
        cmonth.add("July");
        cmonth.add("August");
        cmonth.add("September");
        cmonth.add("October");
        cmonth.add("November");
        cmonth.add("December");
        add(cmonth);

        JLabel lblunits = new JLabel("Units:");
        lblunits.setBounds(35, 260, 200, 20);
        add(lblunits);

        labelunits = new JLabel("");
        labelunits.setBounds(300, 260, 200, 20);
        add(labelunits);

        JLabel lbltotalbill = new JLabel("Total Bill:");
        lbltotalbill.setBounds(35, 320, 200, 20);
        add(lbltotalbill);

        labeltotalbill = new JLabel("");
        labeltotalbill.setBounds(300, 320, 200, 20);
        add(labeltotalbill);

        JLabel lblstatus = new JLabel("Status:");
        lblstatus.setBounds(35, 380, 200, 20);
        add(lblstatus);

        labelstatus = new JLabel("");
        labelstatus.setBounds(300, 380, 200, 20);
        labelstatus.setForeground(Color.RED);
        add(labelstatus);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '" + meter + "'");
            if (rs.next()) {
                labelname.setText(rs.getString("name"));
            }

            updateBillInfo("January");
        } catch (Exception e) {
            e.printStackTrace();
        }

        cmonth.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ae) {
                String selectedMonth = cmonth.getSelectedItem();
                updateBillInfo(selectedMonth);
            }
        });

        pay = new JButton("Pay");
        pay.setBackground(Color.BLACK);
        pay.setForeground(Color.WHITE);
        pay.setBounds(100, 460, 100, 25);
        pay.addActionListener(this);
        add(pay);

        back = new JButton("Back");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(230, 460, 100, 25);
        back.addActionListener(this);
        add(back);

        getContentPane().setBackground(Color.WHITE);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/wallet.png"));
        Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(400, 120, 600, 300);
        add(image);

        setVisible(true);
    }

    private void updateBillInfo(String month) {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from bill where meter_no = '" + meter + "' AND month = '" + month + "'");
            if (rs.next()) {
                labelunits.setText(rs.getString("units"));
                labeltotalbill.setText(rs.getString("totalbill"));
                labelstatus.setText(rs.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == pay) {
            try {
                Conn c = new Conn();
                String selectedMonth = cmonth.getSelectedItem();
                c.s.executeUpdate("update bill set status = 'Paid' where meter_no = '" + meter + "' AND month='" + selectedMonth + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
            setVisible(false);
            new Paytm(meter);
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new PayBill("");
    }
}
