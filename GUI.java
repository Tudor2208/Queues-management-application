package ro.utcn.tudor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    JPanel mainPanel = new JPanel();
    JPanel titlePanel = new JPanel();
    JPanel headerPanel = new JPanel();
    JPanel txtAreaPanel = new JPanel();
    JPanel btnPanel = new JPanel();

    String[] options = {"Default", "Shortest Queue", "Shortest waiting time"};
    JComboBox comboStrategy = new JComboBox(options);

    JLabel lblTitle = new JLabel("Queues management application");
    JLabel lblClients = new JLabel("Clients: ");
    JTextField txtClients = new JTextField(10);
    JLabel lblQueues = new JLabel("Queues: ");
    JTextField txtQueues = new JTextField(10);
    JLabel lblSimulationTime = new JLabel("Simulation time: ");
    JTextField txtSimulationTime = new JTextField(10);
    JLabel lblMinArrTime = new JLabel("Min arrival time: ");
    JTextField txtMinArrTime = new JTextField(10);
    JLabel lblMaxArrTime = new JLabel("Max arrival time: ");
    JTextField txtMaxArrTime = new JTextField(10);
    JLabel lblMinServiceTime = new JLabel("Min service time: ");
    JTextField txtMinServiceTime = new JTextField(10);
    JLabel lblMaxServiceTime = new JLabel("Max service time: ");
    JTextField txtMaxServiceTime = new JTextField(10);
    JTextArea area = new JTextArea(25, 65);
    JButton btnSimulate = new JButton("Simulate");
    JLabel lblStrategy = new JLabel("Strategy: ");

    public GUI() {
        super("Queues management application");

        txtClients.setText("10");
        txtSimulationTime.setText("30");
        txtQueues.setText("3");
        txtMinArrTime.setText("1");
        txtMaxArrTime.setText("3");
        txtMinServiceTime.setText("2");
        txtMaxServiceTime.setText("5");

        this.setVisible(true);
        this.setSize(800, 750);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(300, 130);
        this.setContentPane(mainPanel);

        mainPanel.add(titlePanel);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        titlePanel.add(lblTitle);
        lblTitle.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(headerPanel);
        headerPanel.setLayout(new GridLayout(4, 2));
        headerPanel.add(lblClients);
        headerPanel.add(txtClients);


        headerPanel.add(lblQueues);
        headerPanel.add(txtQueues);

        headerPanel.add(lblSimulationTime);
        headerPanel.add(txtSimulationTime);

        headerPanel.add(lblMinArrTime);
        headerPanel.add(txtMinArrTime);

        headerPanel.add(lblMaxArrTime);
        headerPanel.add(txtMaxArrTime);

        headerPanel.add(lblMinServiceTime);
        headerPanel.add(txtMinServiceTime);

        headerPanel.add(lblMaxServiceTime);
        headerPanel.add(txtMaxServiceTime);

        headerPanel.add(lblStrategy);
        headerPanel.add(comboStrategy);

        area.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);


        mainPanel.add(txtAreaPanel);
        txtAreaPanel.add(scroll);

        mainPanel.add(btnPanel);
        btnPanel.add(btnSimulate);

        btnSimulate.setFocusable(false);
        comboStrategy.setFocusable(false);
        area.setEditable(false);


    }

    public String getTxtClients() {
        return txtClients.getText();
    }

    public String getTxtQueues() {
        return txtQueues.getText();
    }

    public String getTxtSimTime() {
        return txtSimulationTime.getText();
    }

    public String getMinArrTime() {
        return txtMinArrTime.getText();
    }

    public String getMaxArrTime() {
        return txtMaxArrTime.getText();
    }

    public String getMinServiceTime() {
        return txtMinServiceTime.getText();
    }

    public String getMaxServiceTime() {
        return txtMaxServiceTime.getText();
    }

    public Object getPolicy() {
        return comboStrategy.getSelectedItem();
    }

    public void addText(String text) {
        area.append(text);
        area.setCaretPosition(area.getDocument().getLength());
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void addBtnSimulateAL(ActionListener al) {
        btnSimulate.addActionListener(al);
    }

    public void setText(String text) {
        area.setText(text);
    }


}
