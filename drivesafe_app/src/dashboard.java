import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class dashboard {
    private JFrame frame;

    public dashboard() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(2, 1, 0, 10));

        // Create a button to launch the driver form
        JButton filterButton = new JButton("Filter Search");
        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filter filterForm = new filter();
                filterForm.show();
            }
        });
        frame.getContentPane().add(filterButton);

        /*  Create a button to launch the incident form
        JButton incidentButton = new JButton("Incident Form");
        incidentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                incident incidentForm = new incident();
                incidentForm.show();
            }
        });
        frame.getContentPane().add(incidentButton);
                                                                */


        // Create a button to launch the query form
        JButton queryButton = new JButton("Query Search");
        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                query queryForm = new query();
                queryForm.show();
            }
        });
        frame.getContentPane().add(queryButton);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        dashboard dashboard = new dashboard();
    }
}
