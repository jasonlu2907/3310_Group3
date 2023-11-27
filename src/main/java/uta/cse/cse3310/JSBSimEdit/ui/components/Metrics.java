package uta.cse.cse3310.JSBSimEdit.ui.components;

import generated.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class Metrics extends JPanel {
    public Metrics(Wingarea wingarea, Wingspan wingspan, WingIncidence wingincidence, 
       Chord chord, Htailarea htailarea, Htailarm htailarm, Vtailarea vtailarea, Vtailarm vtailarm, List<Location> location){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        //Tool to help make panels less copy-pastey
        PanelBuilder panelBuilder = new PanelBuilder();

        // for combo boxes
        AreaType[] areaType = {AreaType.M_2,AreaType.FT_2};
        LengthType[] lenType = {LengthType.FT, LengthType.IN, LengthType.M};
        AngleType[] angleType = {AngleType.DEG, AngleType.RAD};

        //SET UP FOR PANELS SEPARATION FOR AESTHETIC PURPOSES
        //Panel for the top half of the tab
        JPanel metricsPanel = new JPanel();
        metricsPanel.setLayout(new BoxLayout(metricsPanel,BoxLayout.X_AXIS));
        //panel for wing area, chord, wing span, and wing incidence
        JPanel metrics1 = new JPanel();
        metrics1.setLayout(new BoxLayout(metrics1,BoxLayout.Y_AXIS));
        //panel for the tail metrics
        JPanel metrics2 = new JPanel();
        metrics2.setLayout(new BoxLayout(metrics2,BoxLayout.Y_AXIS));

        //Wing area panel using PANEL BUILDER
        JPanel wingarPanel = new JPanel(new FlowLayout());
        wingarPanel.add(panelBuilder.LabelFieldBoxPanel("wing area(*) = ", String.valueOf(wingarea.getValue()), areaType, wingarea.getUnit()));
        metrics1.add(wingarPanel);
        //Wing Span panel using PANEL BUILDER
        JPanel wingspPanel = new JPanel(new FlowLayout());
        wingspPanel.add(panelBuilder.LabelFieldBoxPanel("wingspan(*) = ", String.valueOf(wingspan.getValue()), lenType, wingspan.getUnit()));
        metrics1.add(wingspPanel);
        //Chord panel
        JPanel chordPanel = new JPanel(new FlowLayout());
        chordPanel.add(panelBuilder.LabelFieldBoxPanel("chord(*) = ", String.valueOf(chord.getValue()), lenType, chord.getUnit()));
        metrics1.add(chordPanel);
        //Wing Incidence panel
        JPanel wingincidencePanel = new JPanel(new FlowLayout());
        wingincidencePanel.add(panelBuilder.LabelFieldBoxPanel("wing incidence(*) = ", wingincidence!=null ? String.valueOf(wingincidence.getValue()):"N/A", angleType, wingincidence!= null? wingincidence.getUnit():"N/A"));
        metrics1.add(wingincidencePanel);

        //Htailarea panel
        JPanel htailarPanel = new JPanel(new FlowLayout());
        htailarPanel.add(panelBuilder.LabelFieldBoxPanel("htailarea(*) = ", String.valueOf(htailarea.getValue()), areaType, htailarea.getUnit()));
        metrics2.add(htailarPanel);
        //Htailarm panel
        JPanel htailarmPanel = new JPanel(new FlowLayout());
        htailarmPanel.add(panelBuilder.LabelFieldBoxPanel("htailarm(*) = ", String.valueOf(htailarm.getValue()),lenType, htailarm.getUnit()));
        metrics2.add(htailarmPanel);
        //Vtailarea panel
        JPanel vtailarPanel = new JPanel(new FlowLayout());
        vtailarPanel.add(panelBuilder.LabelFieldBoxPanel("vtailarea(*) = ", String.valueOf(vtailarea.getValue()), areaType, vtailarea.getUnit()));
        metrics2.add(vtailarPanel);
        //Vtailarm panel
        JPanel vtailarmPanel = new JPanel(new FlowLayout());
        vtailarmPanel.add(panelBuilder.LabelFieldBoxPanel("vtailarm(*) = ", String.valueOf(vtailarm.getValue()), lenType, vtailarm.getUnit()));
        metrics2.add(vtailarmPanel);
        //Add this group to the main top half panel
        metricsPanel.add(metrics1);
        metricsPanel.add(metrics2);
        add(metricsPanel);

        //Aerodynamic Reference Point Panel/////////////////
        JPanel aeroRef = new JPanel(new FlowLayout());
        aeroRef.setBorder(new TitledBorder(location.get(0).getName()));
        //x
        aeroRef.add(panelBuilder.LabelFieldPanel("x = ", String.valueOf(location.get(0).getX())));
        //y
        aeroRef.add(panelBuilder.LabelFieldPanel("y = ", String.valueOf(location.get(0).getY())));
        //z
        aeroRef.add(panelBuilder.LabelFieldBoxPanel("z = ", String.valueOf(location.get(0).getZ()),lenType,location.get(0).getUnit()));
        Component[] aeroComponents = aeroRef.getComponents(); //THIS IS FR SAVING LATER
        
        //Eye Point Panel/////////////////
        JPanel eyePt = new JPanel(new FlowLayout());
        eyePt.setBorder(new TitledBorder(location.get(1).getName()));
        //x
        eyePt.add(panelBuilder.LabelFieldPanel("x = ", String.valueOf(location.get(1).getX())));
        //y
        eyePt.add(panelBuilder.LabelFieldPanel("y = ", String.valueOf(location.get(1).getY())));
        //z
        eyePt.add(panelBuilder.LabelFieldBoxPanel("z = ", String.valueOf(location.get(1).getZ()),lenType,location.get(1).getUnit()));
        Component[] eyeComponents = eyePt.getComponents(); //THIS IS FR SAVING LATER
        
        //Visual Reference Point Panel/////////////////
        JPanel visual = new JPanel(new FlowLayout());
        visual.setBorder(new TitledBorder(location.get(2).getName()));
        //x
        visual.add(panelBuilder.LabelFieldPanel("x = ", String.valueOf(location.get(2).getX())));
        //y
        visual.add(panelBuilder.LabelFieldPanel("y = ", String.valueOf(location.get(2).getY())));
        //z
        visual.add(panelBuilder.LabelFieldBoxPanel("z = ", String.valueOf(location.get(2).getZ()),lenType,location.get(2).getUnit()));
        Component[] visualComponents = visual.getComponents(); //THIS IS FR SAVING LATER
        
        add(aeroRef);
        add(eyePt);
        add(visual);

        //NEW CHANGES 11/25/23
        //add button
        JButton save = new JButton("Save changes");
        save.setActionCommand("Save changes");
        save.setToolTipText("Save changes");
        save.addActionListener(event -> { //SET ALL THE VALUES
            wingarea.setValue(getFieldValue(wingarPanel));
            wingarea.setUnit((AreaType) getUnitValue(wingarPanel));

            wingspan.setValue(getFieldValue(wingspPanel));
            wingspan.setUnit((LengthType) getUnitValue(wingspPanel));

            chord.setValue(getFieldValue(chordPanel));
            chord.setUnit((LengthType) getUnitValue(chordPanel));

            if (wingincidence != null) {
                wingincidence.setValue(getFieldValue(wingincidencePanel));
                wingincidence.setUnit(((AngleType) getUnitValue(wingincidencePanel)));
            }

            htailarea.setValue(getFieldValue(htailarPanel));
            htailarea.setUnit(((AreaType) getUnitValue(htailarPanel)));

            htailarm.setValue(getFieldValue(htailarmPanel));
            htailarm.setUnit(((LengthType) getUnitValue(htailarmPanel)));

            vtailarea.setValue(getFieldValue(vtailarPanel));
            vtailarea.setUnit(((AreaType) getUnitValue(vtailarPanel)));

            vtailarm.setValue(getFieldValue(vtailarmPanel));
            vtailarm.setUnit(((LengthType)getUnitValue(vtailarmPanel)));
            
            //aero ref
            location.get(0).setX(getFieldValue((JPanel)aeroComponents[0]));
            location.get(0).setY(getFieldValue((JPanel)aeroComponents[1]));
            location.get(0).setZ(getFieldValue((JPanel)aeroComponents[2]));
            location.get(0).setUnit(location.get(0).getUnit());
            //eye pt
            location.get(1).setX(getFieldValue((JPanel)eyeComponents[0]));
            location.get(1).setY(getFieldValue((JPanel)eyeComponents[1]));
            location.get(1).setZ(getFieldValue((JPanel)eyeComponents[2]));
            location.get(1).setUnit(location.get(1).getUnit());
            //vis
            location.get(2).setX(getFieldValue((JPanel)visualComponents[0]));
            location.get(2).setY(getFieldValue((JPanel)visualComponents[1]));
            location.get(2).setZ(getFieldValue((JPanel)visualComponents[2]));
            location.get(2).setUnit(location.get(2).getUnit());
        });

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(save);
        add(buttons);
        //NEW METHOD ADDED 11/25/23
        }
    
        //Getting the values out of the dang thing I made that i thought would make things easier
        protected double getFieldValue(JPanel panel) {
            Component[] components = panel.getComponents();

            for (Component component : components) {
                if (component instanceof JPanel) {
                    double value = getFieldValue((JPanel) component); // Recursively iterate through nested JPanels
                    if (value != 0) {
                        return value; // Return the value if found in the nested JPanel
                    }
                } else if (component instanceof JTextField) {
                    try {
                        return Double.parseDouble(((JTextField) component).getText());
                    } catch (NumberFormatException e) {
                        return 0; // Handle cases where parsing the text to double fails
                    }
                }
            }
            return 0;
        }

        //geting the unit from the combo box
        protected Object getUnitValue(JPanel panel) {
            Component[] components = panel.getComponents();

            for (Component component : components) {
                if (component instanceof JPanel) {
                    Object unit = getUnitValue((JPanel) component); // Recursively iterate through nested JPanels
                    if (unit != null) {
                        return unit; // Return the value if found in the nested JPanel
                    }
                } else if (component instanceof JComboBox) {
                    try {
                        return ((JComboBox) component).getSelectedItem();
                    } catch (NumberFormatException e) {
                        return 0; // Handle cases where parsing the text to double fails
                    }
                }
            }
            return null;
        }


}
