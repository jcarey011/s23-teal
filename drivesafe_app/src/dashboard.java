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
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("DriveSafe");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
        titleLabel.setForeground(Color.decode("#333333"));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton filterButton = new JButton("Filter Search");
        filterButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        filterButton.setBackground(Color.decode("#007BFF"));
        filterButton.setForeground(Color.WHITE);
        filterButton.setFocusPainted(false);
        filterButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filter filterForm = new filter();
                filterForm.show();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(filterButton, gbc);

        JButton queryButton = new JButton("Query Search");
        queryButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        queryButton.setBackground(Color.decode("#28A745"));
        queryButton.setForeground(Color.WHITE);
        queryButton.setFocusPainted(false);
        queryButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                query queryForm = new query();
                queryForm.show();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(queryButton, gbc);

        frame.getContentPane().add(buttonPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        dashboard dashboard = new dashboard();
    }
}
