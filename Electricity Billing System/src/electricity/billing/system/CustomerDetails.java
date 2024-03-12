package electricity.billing.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;

public class CustomerDetails extends JFrame implements ActionListener {

    JTable table;
    JButton update, print, searchButton;
    JTextField searchField;
    DefaultTableModel model;

    public CustomerDetails() {
        super("Customer Details");

        setSize(800, 400);
        setLocationRelativeTo(null);

        // Assuming the column names based on the table structure provided
        String[] columnNames = {"Name", "Meter No", "Address", "City", "State", "Email", "Phone"};

        model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only single row selection

        // Load data
        loadData();

        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        // Update and Print buttons
        update = new JButton("Update");
        update.addActionListener(this);
        print = new JButton("Print");
        print.addActionListener(this);

        // Search field and button
        searchField = new JTextField(15);
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);

        JPanel northPanel = new JPanel();
        northPanel.add(new JLabel("Search Name:"));
        northPanel.add(searchField);
        northPanel.add(searchButton);
        add(northPanel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel();
        southPanel.add(update);
        southPanel.add(print);
        add(southPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void loadData() {
        try {
            Conn c = new Conn();
            String query = "SELECT name, meter_no, address, city, state, email, phone FROM customer";
            ResultSet rs = c.s.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("name"), rs.getString("meter_no"),
                        rs.getString("address"), rs.getString("city"),
                        rs.getString("state"), rs.getString("email"), rs.getString("phone")});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading data");
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            // Update selected row
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                updateRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to update");
            }
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error printing table");
                e.printStackTrace();
            }
        } else if (ae.getSource() == searchButton) {
            String searchText = searchField.getText();
            searchCustomer(searchText);
        }
    }

    private void searchCustomer(String text) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        if (text.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    private void updateRow(int row) {
        String name = model.getValueAt(row, 0).toString();
        String meterNo = model.getValueAt(row, 1).toString(); // Primary key
        String address = model.getValueAt(row, 2).toString();
        String city = model.getValueAt(row, 3).toString();
        String state = model.getValueAt(row, 4).toString();
        String email = model.getValueAt(row, 5).toString();
        String phone = model.getValueAt(row, 6).toString();

        try {
            Conn c = new Conn();
            String query = "UPDATE customer SET name = ?, address = ?, city = ?, state = ?, email = ?, phone = ? WHERE meter_no = ?";
            PreparedStatement pst = c.s.getConnection().prepareStatement(query);

            pst.setString(1, name);
            pst.setString(2, address);
            pst.setString(3, city);
            pst.setString(4, state);
            pst.setString(5, email);
            pst.setString(6, phone);
            pst.setString(7, meterNo);

            int updated = pst.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(null, "Update Successful");
            } else {
                JOptionPane.showMessageDialog(null, "Update Failed: No rows affected.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Update Failed");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CustomerDetails();
    }
}
