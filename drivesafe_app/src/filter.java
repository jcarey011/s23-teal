import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class filter {
    private JFrame frame;
    private JTextField textFieldDatef;
    private JTextField textFieldDatet;
    private JTextField textFieldDriverID;
    private JTextField textFieldIncidentType;
    private JTextField textFieldSpeedh;
    private JTextField textFieldSpeedl;
    private JTextField textFieldTimef;
    private JTextField textFieldTimet;
    private JTextField textFieldLocation;
    private JTextField textFieldTripID;
    private JTable table;

    // MySQL database information
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tealtest";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public filter() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 700, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // To prevent from closing the entire program when clicking the 'X'
        frame.getContentPane().setLayout(null);

        JLabel lblDriverId = new JLabel("Driver ID:");
        lblDriverId.setBounds(10, 30, 66, 14);
        frame.getContentPane().add(lblDriverId);

        JLabel lblHazardType = new JLabel("Incident Type:");
        lblHazardType.setBounds(10, 60, 89, 14);
        frame.getContentPane().add(lblHazardType);

        JLabel lblSpeedl = new JLabel("Speed(low):");
        lblSpeedl.setBounds(10, 90, 89, 14);
        frame.getContentPane().add(lblSpeedl);

        JLabel lblSpeedh = new JLabel("Speed(high):");
        lblSpeedh.setBounds(10, 110, 89, 14);
        frame.getContentPane().add(lblSpeedh);

        JLabel lblDatef = new JLabel("Date(from):");
        lblDatef.setBounds(10, 140, 89, 14);
        frame.getContentPane().add(lblDatef);

        JLabel lblDatet = new JLabel("Date(to):");
        lblDatet.setBounds(10, 160, 89, 14);
        frame.getContentPane().add(lblDatet);

        JLabel lblTimef = new JLabel("Time(from):");
        lblTimef.setBounds(10, 190, 89, 14);
        frame.getContentPane().add(lblTimef);

        JLabel lblTimet = new JLabel("Time(to):");
        lblTimet.setBounds(10, 210, 89, 14);
        frame.getContentPane().add(lblTimet);

        JLabel lblLocation = new JLabel("Location:");
        lblLocation.setBounds(10, 240, 66, 14);
        frame.getContentPane().add(lblLocation);

        JLabel lblTrip = new JLabel("Trip ID:");
        lblTrip.setBounds(10, 270, 66, 14);
        frame.getContentPane().add(lblTrip);


        textFieldDriverID = new JTextField();
        textFieldDriverID.setBounds(100, 30, 86, 20);
        frame.getContentPane().add(textFieldDriverID);
        textFieldDriverID.setColumns(10);

        textFieldIncidentType = new JTextField();
        textFieldIncidentType.setBounds(100, 60, 86, 20);
        frame.getContentPane().add(textFieldIncidentType);
        textFieldIncidentType.setColumns(10);

        textFieldSpeedl = new JTextField();
        textFieldSpeedl.setBounds(100, 90, 86, 20);
        frame.getContentPane().add(textFieldSpeedl);
        textFieldSpeedl.setColumns(10);

        textFieldSpeedh = new JTextField();
        textFieldSpeedh.setBounds(100, 110, 86, 20);
        frame.getContentPane().add(textFieldSpeedh);
        textFieldSpeedh.setColumns(10);

        textFieldDatef = new JTextField();
        textFieldDatef.setBounds(100, 140, 86, 20);
        frame.getContentPane().add(textFieldDatef);
        textFieldDatef.setColumns(10);

        textFieldDatet = new JTextField();
        textFieldDatet.setBounds(100, 160, 86, 20);
        frame.getContentPane().add(textFieldDatet);
        textFieldDatet.setColumns(10);

        textFieldTimef = new JTextField();
        textFieldTimef.setBounds(100, 190, 86, 20);
        frame.getContentPane().add(textFieldTimef);
        textFieldTimef.setColumns(10);

        textFieldTimet = new JTextField();
        textFieldTimet.setBounds(100, 210, 86, 20);
        frame.getContentPane().add(textFieldTimet);
        textFieldTimet.setColumns(10);

        textFieldLocation = new JTextField();
        textFieldLocation.setBounds(100, 240, 86, 20);
        frame.getContentPane().add(textFieldLocation);
        textFieldLocation.setColumns(10);

        textFieldTripID = new JTextField();
        textFieldTripID.setBounds(100, 270, 86, 20);
        frame.getContentPane().add(textFieldTripID);
        textFieldTripID.setColumns(10);

        JButton btnFilter = new JButton("Filter");
        btnFilter.setBounds(10, 300, 89, 23);
        frame.getContentPane().add(btnFilter);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(200, 22, 474, 300);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        btnFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                String driverID = textFieldDriverID.getText();
                String IncidentType = textFieldIncidentType.getText();
                String speedl = textFieldSpeedl.getText();
                String speedh = textFieldSpeedh.getText();
                String datef = textFieldDatef.getText();
                String datet = textFieldDatet.getText();
                String timef = textFieldTimef.getText();
                String timet = textFieldTimet.getText();
                String location = textFieldLocation.getText();
                String TripID = textFieldTripID.getText();

                Connection conn = null;
                PreparedStatement stmt = null;
                ResultSet rs = null;

                try {
                    // Create a connection to the MySQL database
conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                // Create the SQL query based on the user input
                String sql = "SELECT * FROM incident WHERE";
                if (!driverID.equals("")) {
                    sql += " Driver_ID=?";
                }
                if (!IncidentType.equals("")) {
                    sql += " AND Incident_Type=?";
                }
                if (!speedl.equals("")) {
                    sql += " AND Speed BETWEEN?";
                }
                if (!speedh.equals("")) {
                    sql += " AND ?";
                }
                if (!datef.equals("")) {
                    sql += " AND Date BETWEEN?";
                }
                if (!datet.equals("")) {
                    sql += " AND ?";
                }
                if (!timef.equals("")) {
                    sql += " AND Time BETWEEN?";
                }
                if (!timet.equals("")) {
                    sql += " AND ?";
                }
                if (!location.equals("")) {
                    sql += " AND Location=?";
                }
                if (!TripID.equals("")) {
                    sql += " AND Trip_ID=?";
                }

                // Prepare the SQL statement
                stmt = conn.prepareStatement(sql);

                // Bind the parameters to the SQL statement
                int parameterIndex = 1;
                
                if (!driverID.equals("")) {
                    stmt.setInt(parameterIndex, Integer.parseInt(driverID));
                    parameterIndex++;
                }
                if (!IncidentType.equals("")) {
                    stmt.setString(parameterIndex, IncidentType);
                    parameterIndex++;
                }
                if (!speedl.equals("")) {
                    stmt.setString(parameterIndex, speedl);
                    parameterIndex++;
                }
                if (!speedh.equals("")) {
                    stmt.setString(parameterIndex, speedh);
                    parameterIndex++;
                }
                if (!datef.equals("")) {
                    stmt.setString(parameterIndex, datef);
                    parameterIndex++;
                }
                if (!datet.equals("")) {
                    stmt.setString(parameterIndex, datet);
                    parameterIndex++;
                }
                if (!timef.equals("")) {
                    stmt.setString(parameterIndex, timef);
                    parameterIndex++;
                }
                if (!timet.equals("")) {
                    stmt.setString(parameterIndex, timet);
                    parameterIndex++;
                }
                if (!location.equals("")) {
                    stmt.setString(parameterIndex, location);
                    parameterIndex++;
                }
                if (!TripID.equals("")) {
                    stmt.setString(parameterIndex, TripID);
                    parameterIndex++;
                }

                // Execute the SQL query
                rs = stmt.executeQuery();

                // Create a table model with the results of the SQL query
                DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Driver_ID", "Incident_Type", "Speed", "Date", "Time", "Location", "Trip_ID"}, 0);
                while (rs.next()) {
                    int driverIDValue = rs.getInt("Driver_ID");
                    String incidentValue = rs.getString("Incident_Type");
                    int speedValue = rs.getInt("Speed");
                    Date dateValue = rs.getDate("Date");
                    Time timeValue = rs.getTime("Time");
                    String locationValue = rs.getString("Location");
                    int tripValue = rs.getInt("Trip_ID");

                    tableModel.addRow(new Object[]{driverIDValue, incidentValue, speedValue, dateValue, timeValue, locationValue, tripValue});
                }

                // Set the table model for the table
                table.setModel(tableModel);

            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    // Close the result set
                    if (rs != null) {
                        rs.close();
                    }
                    // Close the statement
                    if (stmt != null) {
                        stmt.close();
                    }
                    // Close the connection
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    });

    frame.setVisible(true);
}

    public void show() {
    }
}
