package uta.cse.cse3310.JSBSimEdit.UIComponents;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Autopilot extends JPanel{
    public Autopilot(){
        setLayout(new BorderLayout());

        JPanel filePanel = new JPanel();
        JLabel name = new JLabel("Name:");
        JTextField nameField = new JTextField("",10);
        JLabel file = new JLabel("File:");
        JTextField fileField = new JTextField("",10);
        JButton fileChooseButton = new JButton("Choose File");
        filePanel.add(name);
        filePanel.add(nameField);
        filePanel.add(file);
        filePanel.add(fileField);
        filePanel.add(fileChooseButton);

        add(filePanel, BorderLayout.PAGE_START);

        JPanel occurs = new JPanel();
        JLabel minoccurs = new JLabel("Min Occurs");
        JTextField minocField = new JTextField("",10);
        JLabel maxoccurs = new JLabel("Max Occurs");
        JTextField maxocField = new JTextField("",10);
        String[] ref = {"Property", "Sensor", "Channel"};
        JComboBox<String> refBox = new JComboBox<String>(ref);
        occurs.add(minoccurs);
        occurs.add(minocField);
        occurs.add(maxoccurs);
        occurs.add(maxocField);
        occurs.add(refBox);

        add(occurs, BorderLayout.CENTER);

    }
}
