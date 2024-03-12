package electricity.billing.system;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Signup extends JFrame implements ActionListener {

    JButton create, back;
    Choice accountType;
    JTextField meter, username, name, password, invitationCode;
    
    Signup() {
        setBounds(600, 250, 700, 500);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(30, 30, 650, 400); // Adjusted for extra field
        panel.setBorder(new TitledBorder(new LineBorder(new Color(173, 216, 230), 2), "Create Account", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(172, 216, 230)));
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setForeground(new Color(34, 139, 34));
        add(panel);

        JLabel heading = new JLabel("Create Account As");
        heading.setBounds(100, 50, 140, 20);
        heading.setForeground(Color.GRAY);
        heading.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(heading);

        accountType = new Choice();
        accountType.add("Customer");
        accountType.add("Admin");
        accountType.setBounds(260, 50, 150, 20);
        panel.add(accountType);

        JLabel lblmeter = new JLabel("Meter Number");
        lblmeter.setBounds(100, 90, 140, 20);
        lblmeter.setForeground(Color.GRAY);
        lblmeter.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(lblmeter);

        meter = new JTextField();
        meter.setBounds(260, 90, 150, 20);
        panel.add(meter);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(100, 130, 140, 20);
        lblusername.setForeground(Color.BLUE);
        lblusername.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(lblusername);

        username = new JTextField();
        username.setBounds(260, 130, 150, 20);
        panel.add(username);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(100, 170, 140, 20);
        lblname.setForeground(Color.GRAY);
        lblname.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(lblname);

        name = new JTextField();
        name.setBounds(260, 170, 150, 20);
        panel.add(name);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(100, 210, 140, 20);
        lblpassword.setForeground(Color.BLUE);
        lblpassword.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(lblpassword);

        password = new JTextField();
        password.setBounds(260, 210, 150, 20);
        panel.add(password);

        JLabel lblInvitationCode = new JLabel("Invitation Code");
        lblInvitationCode.setBounds(100, 250, 140, 20);
        lblInvitationCode.setForeground(Color.BLUE);
        lblInvitationCode.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(lblInvitationCode);

        invitationCode = new JTextField();
        invitationCode.setBounds(260, 250, 150, 20);
        panel.add(invitationCode);

        create = new JButton("Create");
        create.setBackground(Color.BLACK);
        create.setForeground(Color.WHITE);
        create.setBounds(140, 300, 120, 25);
        create.addActionListener(this);
        panel.add(create);

        back = new JButton("Back");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(300, 300, 120, 25);
        back.addActionListener(this);
        panel.add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/sign-up.png"));
        Image i2 = i1.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(475, 50, 150, 150);
        panel.add(image);

        setVisible(true);

        meter.addKeyListener(new KeyAdapter() {
    @Override
    public void keyReleased(KeyEvent e) {
        String sMeter = meter.getText();
        try {
            Conn con = new Conn();
            ResultSet rs = con.s.executeQuery("select * from customer where meter_no = '"+sMeter+"'");
            if(rs.next()) {
                name.setText(rs.getString("name"));
            } else {
                name.setText(""); // Clear name if meter number is invalid
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
});

    lblmeter.setVisible(true);
    meter.setVisible(true);
    name.setEditable(false); // Assuming you want 'name' not editable for "Customer"
    lblInvitationCode.setVisible(false); // Assuming you add these for the "Admin"
    invitationCode.setVisible(false); // Assuming you add these for the "Admin"

    // Adjust your ItemListener for accountType to handle changes
    accountType.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent ae) {
            String user = accountType.getSelectedItem();
            if (user.equals("Customer")) {
                lblmeter.setVisible(true);
                meter.setVisible(true);
                name.setEditable(false);
                lblInvitationCode.setVisible(false);
                invitationCode.setVisible(false);
            } else if (user.equals("Admin")) {
                lblmeter.setVisible(false);
                meter.setVisible(false);
                name.setEditable(true);
                lblInvitationCode.setVisible(true);
                invitationCode.setVisible(true);
            }
        }
    });

        setVisible(true);
    }

    
    public void actionPerformed(ActionEvent ae) {
    if(ae.getSource() == create) {
        String atype = accountType.getSelectedItem();
        String susername = username.getText().trim();
        String sname = name.getText().trim();
        String spassword = password.getText().trim();
        String smeter = meter.getText().trim();
        String sInvitationCode = invitationCode.getText().trim();

        // Check for empty fields, adjust for customer/admin-specific fields
        if (susername.isEmpty() || spassword.isEmpty() || sname.isEmpty() || (atype.equals("Customer") && smeter.isEmpty()) || (atype.equals("Admin") && sInvitationCode.isEmpty() && !"ADMIN@123".equals(sInvitationCode))) {
            JOptionPane.showMessageDialog(null, "All fields must be filled out");
            return;
        }

        // Invitation code validation only for Admin
        if (atype.equals("Admin") && !"ADMIN@123".equals(sInvitationCode)) {
            JOptionPane.showMessageDialog(null, "Invalid invitation code for Admin");
            return;
        }

try {
    Conn c = new Conn();
    String query = "";

    if (atype.equals("Admin")) {
        // Admin account creation logic remains the same
        query = "insert into login(username, name, password, user, invitation_code) values('" + susername + "', '" + sname + "', '" + spassword + "', '" + atype + "', '" + sInvitationCode + "')";
    } else {
        // Customer logic with meter number check and conditional update
        if (!smeter.isEmpty()) {
            ResultSet rs = c.s.executeQuery("select * from login where meter_no = '" + smeter + "'");
            if (rs.next()) {
                String existingUsername = rs.getString("username");
                if (existingUsername == null || existingUsername.isEmpty()) {
                    // Perform update operation since username is empty
                    query = "update login set username = '" + susername + "', name = '" + sname + "', password = '" + spassword + "', user = '" + atype + "' where meter_no = '" + smeter + "'";
                } else {
                    JOptionPane.showMessageDialog(null, "Meter_no or username already registered");
                    return;
                }
            } else {
                // If meter number does not exist, insert new entry
                query = "insert into login(meter_no, username, name, password, user) values('" + smeter + "', '" + susername + "', '" + sname + "', '" + spassword + "', '" + atype + "')";
            }
        } else {
            JOptionPane.showMessageDialog(null, "Meter number cannot be empty for a customer account.");
            return;
        }
    }

    if (!query.isEmpty()) {
        c.s.executeUpdate(query);
        JOptionPane.showMessageDialog(null, "Operation successful.");
        setVisible(false);
        new Login();
    }
} catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage());
}

    }else if (ae.getSource() == back) {
        setVisible(false);
        new Login();
    }
}
    public static void main(String[] args) {
        new Signup();
    }
}
