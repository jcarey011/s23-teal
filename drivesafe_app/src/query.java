import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.json.JSONArray;
import java.net.URL;
import java.io.File;
import java.io.FileWriter;

public class query {
    String sql;

    private JSONArray fetchLocations(String sqlcopy) {
        JSONArray locations = new JSONArray();
        try {
            String sql2 = sqlcopy;
            sql2.replace("*", "location");
            ResultSet rs = stmt.executeQuery(sql2);
            while (rs.next()) {
                String location = rs.getString("Location");
                locations.put(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }

    private void generateMapHTML(JSONArray locations) {
        try {
            URL templateURL = getClass().getResource("map_template.html");
            if (templateURL != null) {
                Path templatePath = Paths.get(templateURL.toURI());
                String template = Files.readString(templatePath);
                template = template.replace("/*LOCATIONS_JSON*/", locations.toString());

                // Generate map.html in the same directory as map_template.html
                FileWriter fileWriter = new FileWriter("map.html");
                fileWriter.write(template);
                fileWriter.close();
            } else {
                System.err.println("map_template.html not found");
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private Connection conn;
    private Statement stmt;

    public query() {
        initialize();
    }

    private void initialize() {
        try {
            // Create a connection to your database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tealtest", "root", "root");

            // Create a statement object to execute SQL statements
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("DriveSafe Data Query Interface");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(100, 100, 600, 400);
        frame.getContentPane().setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("DriveSafe Query");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
        titleLabel.setForeground(Color.decode("#333333"));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(titleLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout(10, 10));

        GridBagConstraints c = new GridBagConstraints();

        // Input panel for SELECT text, input box, Query button, and Show Map button
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setLayout(new GridBagLayout());

        // Add the SELECT text field
        JTextField sqlTextField = new JTextField("SELECT");
        sqlTextField.setPreferredSize(new Dimension(70, 20));
        sqlTextField.setEditable(false);
        sqlTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        sqlTextField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 20, 10, 0);
        inputPanel.add(sqlTextField, c);

        // Add the editable SQL text field
        JTextField editableSqlTextField = new JTextField();
        editableSqlTextField.setPreferredSize(new Dimension(400, 20));
        editableSqlTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        editableSqlTextField.requestFocus();

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 5, 10, 20);
        inputPanel.add(editableSqlTextField, c);

        // Create a button that the user can click to execute the SQL statement
        JButton queryButton = new JButton("Query");
        queryButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        queryButton.setBackground(Color.decode("#28A745"));
        queryButton.setForeground(Color.WHITE);
        queryButton.setFocusPainted(false);
        queryButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 20, 20, 10); // for the "Query" button
        inputPanel.add(queryButton, c);

        JButton showMapButton = new JButton("Show Map");
        showMapButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        showMapButton.setBackground(Color.decode("#007BFF"));
        showMapButton.setForeground(Color.WHITE);
        showMapButton.setFocusPainted(false);
        showMapButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        c.gridx = 4;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 10, 20, 20); // for the "Show Map" button
        inputPanel.add(showMapButton, c);

        panel.add(inputPanel, BorderLayout.NORTH);

        // Scroll panel for tables list
        JPanel scrollPanel = new JPanel();
        scrollPanel.setBackground(Color.WHITE);
        scrollPanel.setLayout(new GridBagLayout());

        // Add the tables list
        JLabel tablesLabel = new JLabel("TABLES");
        tablesLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        tablesLabel.setForeground(Color.decode("#333333"));

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 10, 20);
        scrollPanel.add(tablesLabel, c);

        DefaultListModel<String> tablesListModel = new DefaultListModel<>();
        try {
            DatabaseMetaData dbMetaData = conn.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbMetaData.getTables("tealtest", null, "%", types);
            while (rs.next()) {
                tablesListModel.addElement(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        JList<String> tablesList = new JList<>(tablesListModel);
        JScrollPane tablesListScrollPane = new JScrollPane(tablesList);
        tablesListScrollPane.setPreferredSize(new Dimension(150, 150));

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(0, 0, 20, 20);
        scrollPanel.add(tablesListScrollPane, c);

        panel.add(scrollPanel, BorderLayout.WEST);

        JPanel resultsPanel = new JPanel();
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setLayout(new BorderLayout());
        panel.add(resultsPanel, BorderLayout.CENTER);

        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the SQL statement from the text field
                    sql = "SELECT " + editableSqlTextField.getText();

                    // Execute the SQL statement
                    ResultSet rs = stmt.executeQuery(sql);

                    // Hide tables information
                    tablesLabel.setVisible(false);
                    tablesListScrollPane.setVisible(false);

                    // Display the results in a new window
                    DefaultTableModel tableModel = new DefaultTableModel() {
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    JTable table = new JTable(tableModel);
                    JScrollPane scrollPane = new JScrollPane(table);

                    resultsPanel.removeAll();

                    resultsPanel.add(scrollPane, BorderLayout.CENTER);

                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            frame.pack();
                            frame.setVisible(true);
                        }
                    });

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

        showMapButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JSONArray locations = fetchLocations(sql);
                generateMapHTML(locations);
                try {
                    Desktop.getDesktop().browse(new File("map.html").toURI());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.getContentPane().add(panel, BorderLayout.CENTER);

        // Set the size of the frame to cover 60% of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.6);
        int height = (int) (screenSize.height * 0.6);
        frame.setSize(new Dimension(width, height));

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public void show() {
    }
}