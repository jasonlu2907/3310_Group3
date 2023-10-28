package uta.cse.cse3310.JSBSimEdit.UIComponents;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class MassBalance extends JPanel{
    public MassBalance(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String[] weight = {"KG","N","LBS"};
        String[] len = {"IN","M","FT"};
        String[] inertia = {"KG*M2","SLUG*FT2"};

        //MASS
        JPanel massPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        massPanel.setBorder(new TitledBorder("Mass"));
        JLabel mass = new JLabel("Empty Weight(*)");
        JTextField massField = new JTextField("",10);
        JComboBox<String> massBox = new JComboBox<String>(weight);
        massPanel.add(mass);
        massPanel.add(massField);
        massPanel.add(massBox);

        add(massPanel);

        //LOCATION
        JPanel locationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        locationPanel.setBorder(new TitledBorder("Location(*)"));
        JLabel xLocation = new JLabel("x = ");
        JTextField xLocationField = new JTextField("",10);
        JLabel yLocation = new JLabel("y = ");
        JTextField yLocationField = new JTextField("",10);
        JLabel zLocation = new JLabel("z = ");
        JTextField zLocationField = new JTextField("",10);
        JComboBox<String> locationBox = new JComboBox<String>(len);
        locationPanel.add(xLocation);
        locationPanel.add(xLocationField);
        locationPanel.add(yLocation);
        locationPanel.add(yLocationField);
        locationPanel.add(zLocation);
        locationPanel.add(zLocationField);
        locationPanel.add(locationBox);

        add(locationPanel);

        //MOMENT OF THE INERTIA
        JPanel momentInertiaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        momentInertiaPanel.setBorder(new TitledBorder("Moment of Inertia(*)"));

        //FIRST COLUMN
        JPanel momentInertiaPanel1 = new JPanel();
        momentInertiaPanel1.setLayout(new BoxLayout(momentInertiaPanel1, BoxLayout.Y_AXIS));

        JPanel ixxPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel ixx = new JLabel("Ixx = ");
        JTextField ixxField = new JTextField("",10);
        JComboBox<String> ixxUnits = new JComboBox<String>(inertia);
        ixxPanel.add(ixx);
        ixxPanel.add(ixxField);
        ixxPanel.add(ixxUnits);
        momentInertiaPanel1.add(ixxPanel);

        JPanel iyyPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel iyy = new JLabel("iyy = ");
        JTextField iyyField = new JTextField("",10);
        JComboBox<String> iyyUnits = new JComboBox<String>(inertia);
        iyyPanel.add(iyy);
        iyyPanel.add(iyyField);
        iyyPanel.add(iyyUnits);
        momentInertiaPanel1.add(iyyPanel);

        JPanel izzPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel izz = new JLabel("izz = ");
        JTextField izzField = new JTextField("",10);
        JComboBox<String> izzUnits = new JComboBox<String>(inertia);
        izzPanel.add(izz);
        izzPanel.add(izzField);
        izzPanel.add(izzUnits);
        momentInertiaPanel1.add(izzPanel);

        //SECOND COLUMN
        JPanel momentInertiaPanel2 = new JPanel();
        momentInertiaPanel2.setLayout(new BoxLayout(momentInertiaPanel2, BoxLayout.Y_AXIS));

        JPanel ixzPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel ixz = new JLabel("ixz = ");
        JTextField ixzField = new JTextField("",10);
        JComboBox<String> ixzUnits = new JComboBox<String>(inertia);
        ixzPanel.add(ixz);
        ixzPanel.add(ixzField);
        ixzPanel.add(ixzUnits);
        momentInertiaPanel2.add(ixzPanel);

        JPanel iyzPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel iyz = new JLabel("iyz = ");
        JTextField iyzField = new JTextField("",10);
        JComboBox<String> iyzUnits = new JComboBox<String>(inertia);
        iyzPanel.add(iyz);
        iyzPanel.add(iyzField);
        iyzPanel.add(iyzUnits);
        momentInertiaPanel2.add(iyzPanel);

        JPanel ixyPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel ixy = new JLabel("ixy = ");
        JTextField ixyField = new JTextField("",10);
        JComboBox<String> ixyUnits = new JComboBox<String>(inertia);
        ixyPanel.add(ixy);
        ixyPanel.add(ixyField);
        ixyPanel.add(ixyUnits);
        momentInertiaPanel2.add(ixyPanel);

        momentInertiaPanel.add(momentInertiaPanel1);
        momentInertiaPanel.add(momentInertiaPanel2);

        add(momentInertiaPanel);
        
        //POINT MASS
        JPanel pmPanel = new JPanel(new BorderLayout());
        pmPanel.setBorder(new TitledBorder("Point Mass"));
        pmPanel.add(new JScrollPane(),BorderLayout.CENTER);
        
        //add button
        JButton newPM = new JButton("Add a new Point Mass");
        newPM.setActionCommand("Add Point Mass");
        newPM.setToolTipText("Add new Point Mass");
        newPM.addActionListener(event -> onAddPMClick());

        //delete button
        JButton deletePM = new JButton("Delete a Point Mass");
        deletePM.setActionCommand("Delete Point Mass");
        deletePM.setToolTipText("Delete Point Mass");
        deletePM.addActionListener(event -> onDelettePMClick());

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(newPM);
        buttons.add(deletePM);

        pmPanel.add(buttons,BorderLayout.PAGE_END);

        add(pmPanel);

    }

    protected void onAddPMClick() {
    
    }

    protected void onDelettePMClick() {
    }

}