import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Interface extends JFrame {

    // 1. Δήλωση μεταβλητών
    private JTextField nameField;
    private JTextField priceField;
    private JTextField categoryField; // Νέα μεταβλητή
    private JTextField qtyField;      // Νέα μεταβλητή
    private JButton submitButton;
    private JButton delButton;
    private JTable productTable;
    private DefaultTableModel tableModel;

    public Interface() {
        // Βασικές ρυθμίσεις παραθύρου
        setTitle("Inventory Manager v1.0");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- ΦΟΡΜΑ ΕΙΣΑΓΩΓΗΣ ---
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Product Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        formPanel.add(priceField);

        formPanel.add(new JLabel("Category:"));
        categoryField = new JTextField();
        formPanel.add(categoryField);

        formPanel.add(new JLabel("Quantity:"));
        qtyField = new JTextField();
        formPanel.add(qtyField);

        submitButton = new JButton("Save Product");
        delButton = new JButton("Delete product");
        formPanel.add(new JLabel("")); // Κενό για στοίχιση
        formPanel.add(submitButton);
        formPanel.add(delButton);

        add(formPanel, BorderLayout.NORTH);

        // --- ΠΙΝΑΚΑΣ ΠΡΟΪΟΝΤΩΝ ---
        String[] columnNames = {"Product Name", "Price", "Category", "Quantity"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        JScrollPane sp = new JScrollPane(productTable);
        add(sp, BorderLayout.CENTER);

        // Events
        submitButton.addActionListener(e -> handleSave());
        delButton.addActionListener(e->handleDelete());

        // Φόρτωση δεδομένων αμέσως
        loadTableData();
    }

private void loadTableData() {
        tableModel.setRowCount(0); 
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product");

            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String category = rs.getString("category");
                int quantity = rs.getInt("quantity");

                Object[] row = {name, price, category, quantity};
                tableModel.addRow(row);
            }
        } catch (Exception e) { // ΑΛΛΑΓΗ ΕΔΩ: από SQLException σε Exception
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }

    private void addProductToDatabase(String name, double price, String category, int quantity){
        String sql = "INSERT INTO product (name, price, category, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            // Αντικατάσταση των ερωτηματικών (?) με τις πραγματικές τιμές
        pstmt.setString(1, name);
        pstmt.setDouble(2, price);
        pstmt.setString(3, category);
        pstmt.setInt(4, quantity);

        pstmt.executeUpdate(); // Εκτέλεση της εντολής
        System.out.println("Product saved successfully!");

        }catch(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saving to database: " + e.getMessage());
    }
    }

    private void handleSave() {
    // 1. Παίρνουμε ΠΑΝΤΑ πρώτα το κείμενο ως String
    String nameStr = nameField.getText();
    String priceStr = priceField.getText();
    String categoryStr = categoryField.getText();
    String qtyStr = qtyField.getText();

    // 2. Έλεγχος αν είναι κενά (χρησιμοποιώντας τα String που μόλις φτιάξαμε)
    if (nameStr.isEmpty() || priceStr.isEmpty() || categoryStr.isEmpty() || qtyStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill all fields!");
        return;
    }

    try {
        // 3. Μετατροπή των String σε αριθμούς
        double priceValue = Double.parseDouble(priceStr);
        int qtyValue = Integer.parseInt(qtyStr);

        // 4. ΚΛΗΣΗ της μεθόδου SQL (Εδώ είναι που γίνεται το Insert!)
        addProductToDatabase(nameStr, priceValue, categoryStr, qtyValue);
        //deleteFromDatabase(nameStr);
        
        // 5. Ενημέρωση UI
        JOptionPane.showMessageDialog(this, "Product saved successfully!");
        loadTableData(); // Ανανέωση του πίνακα

        // Καθαρισμός πεδίων
        nameField.setText("");
        priceField.setText("");
        categoryField.setText("");
        qtyField.setText("");

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Price and Quantity must be valid numbers!");
    }
}

private void deleteFromDatabase(String name) {
    String sql = "DELETE FROM product WHERE name = ?"; // Το (?) δεν χρειάζεται παρενθέσεις, αρκεί το ?
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, name);
        
        // ΑΥΤΗ Η ΓΡΑΜΜΗ ΛΕΙΠΕΙ:
        int rowsDeleted = pstmt.executeUpdate(); 
        
        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(this, "Product deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Product not found!");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error deleting: " + e.getMessage());
    }
}

private void handleDelete() {
    String nameStr = nameField.getText();

    if (nameStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter the Product Name to delete.");
        return;
    }

    deleteFromDatabase(nameStr);
    loadTableData(); // Ανανέωση πίνακα για να εξαφανιστεί η γραμμή
    nameField.setText("");
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interface().setVisible(true);
        });
    }
}
