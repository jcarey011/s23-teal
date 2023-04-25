import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.URISyntaxException;
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
                Path outputPath = templatePath.resolveSibling("map.html");
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

        // Create a text field where the user can enter their SQL statement
        JTextField sqlTextField = new JTextField();
        sqlTextField.setPreferredSize(new Dimension(400, 20));

        // Create a button that the user can click to execute the SQL statement
        JButton queryButton = new JButton("Query");
        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the SQL statement from the text field
                    sql = sqlTextField.getText();

                    // Execute the SQL statement
                    ResultSet rs = stmt.executeQuery(sql);

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
    
       /* // Create a "Show Map" button
        JButton showMapButton = new JButton("Show Map");
        showMapButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Query the database for locations
                    String sql = "SELECT Location FROM incident;";
                    ResultSet rs = stmt.executeQuery(sql);

                    // STATIC HTML FILE containing GMaps JavaScript API code
                    URI uri = new URI("C:/Users/daves/OneDrive/Desktop/ODUSpring23/FinalTeal/s23-teal/drivesafe_app/src/map.html");
                    Desktop.getDesktop().browse(uri);
                } catch (SQLException | IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });*/

        JButton showMapButton = new JButton("Show Map");
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

        // Add the text field and button to the frame
        JPanel panel = new JPanel();
        panel.add(sqlTextField);
        panel.add(queryButton);
        panel.add(showMapButton);
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
    public void show() {
    }
}