import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class drivesafe_desktop {
    private Connection conn;
    private Statement stmt;

    public drivesafe_desktop() {
        try {
            // Create a connection to your database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/drive_safe", "root", "root");

            // Create a statement object to execute SQL statements
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        drivesafe_desktop main = new drivesafe_desktop();
        JFrame frame = new JFrame("DriveSafe Data Query Interface");

        // Create a text field where the user can enter their SQL statement
        JTextField sqlTextField = new JTextField();
        sqlTextField.setPreferredSize(new Dimension(400, 20));

        // Create a button that the user can click to execute the SQL statement
        JButton queryButton = new JButton("Query");
        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the SQL statement from the text field
                    String sql = sqlTextField.getText();

                    // Execute the SQL statement
                    ResultSet rs = main.stmt.executeQuery(sql);

                    // Display the results in a new window
                    DefaultTableModel tableModel = new DefaultTableModel() {
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    JTable table = new JTable(tableModel);
                    JScrollPane scrollPane = new JScrollPane(table);
                    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
                    frame.pack();
                    frame.setVisible(true);

                    // Fill the table model with data from the result set
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        tableModel.addColumn(metaData.getColumnLabel(i));
                    }
                    while (rs.next()) {
                        Object[] rowData = new Object[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            rowData[i - 1] = rs.getObject(i);
                        }
                        tableModel.addRow(rowData);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Add the text field and button to the frame
        JPanel panel = new JPanel();
        panel.add(sqlTextField);
        panel.add(queryButton);
        frame.getContentPane().add(panel, BorderLayout.NORTH);

        // Set the size of the frame to cover 60% of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.6);
        int height = (int) (screenSize.height * 0.6);
        frame.setSize(new Dimension(width, height));

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

}