package uta.cse.cse3310.JSBSimEdit.ui.components;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Output extends JPanel{
    public Output(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel firstRowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel name = new JLabel("Name(*):");
        JTextField nameField = new JTextField("",10);
        JLabel type = new JLabel("Type:");
        JTextField typeField = new JTextField("",10);
        JLabel rate = new JLabel("Rate:");
        JTextField rateField = new JTextField("",10);
        firstRowPanel.add(name);
        firstRowPanel.add(nameField);
        firstRowPanel.add(type);
        firstRowPanel.add(typeField);
        firstRowPanel.add(rate);
        firstRowPanel.add(rateField);
        
        add(firstRowPanel);

        JPanel options = new JPanel(new GridLayout(4,4));
        JCheckBox simulation = new JCheckBox("simulation");
        JCheckBox rates = new JCheckBox("rates");
        JCheckBox position = new JCheckBox("position");
        JCheckBox propulsion = new JCheckBox("propulsion");
        JCheckBox atmosphere = new JCheckBox("atmosphere");
        JCheckBox velocities = new JCheckBox("velocities");
        JCheckBox coefficients = new JCheckBox("coefficients");
        JCheckBox massprops = new JCheckBox("massprops");
        JCheckBox forces = new JCheckBox("forces");
        JCheckBox groundreactions = new JCheckBox("groundreactions");
        JCheckBox aerosurfaces = new JCheckBox("aerosurfaces");
        JCheckBox moments = new JCheckBox("moments");
        JCheckBox fcs = new JCheckBox("fcs");
        options.add(simulation);
        options.add(rates);
        options.add(position);
        options.add(propulsion);
        options.add(atmosphere);
        options.add(velocities);
        options.add(coefficients);
        options.add(massprops);
        options.add(forces);
        options.add(groundreactions);
        options.add(aerosurfaces);
        options.add(moments);
        options.add(fcs);

        add(options);

        JTextArea propertiesArea = new JTextArea();
        propertiesArea.setEditable(false); 
        add(propertiesArea);

        JPanel lastRowPanel = new JPanel();
        JTextArea propertyField = new JTextArea();
        //choose button
        JButton choose = new JButton("choose");
        //add button
        JButton add = new JButton("add");
        //delete button
        JButton delete = new JButton("delete");
        lastRowPanel.add(propertyField);
        lastRowPanel.add(choose);
        lastRowPanel.add(add);
        lastRowPanel.add(delete);

        add(lastRowPanel);

    }

}
