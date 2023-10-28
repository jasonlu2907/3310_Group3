package uta.cse.cse3310.JSBSimEdit.UIComponents;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Metrics extends JPanel {
    public Metrics(){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        //Strings for combo boxes
        String[] area = {"M2","FT2"};
        String[] len  = {"M","FT"};
        String[] deg  = {"DEG"};

        JPanel metricsPanel = new JPanel();
        metricsPanel.setLayout(new BoxLayout(metricsPanel,BoxLayout.X_AXIS));

        JPanel metrics1 = new JPanel();
        metrics1.setLayout(new BoxLayout(metrics1,BoxLayout.Y_AXIS));

        //WING AREA
        JPanel wingarPanel = new JPanel(new FlowLayout());
        JLabel wingarea = new JLabel("wingarea(*) = ");
        JTextField wingareaField = new JTextField(5);
        JComboBox<String> wingareaBox = new JComboBox<String>(area);
        wingarPanel.add(wingarea);
        wingarPanel.add(wingareaField);
        wingarPanel.add(wingareaBox);
        wingarPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        metrics1.add(wingarPanel);

        //WING SPAN
        JPanel wingspPanel = new JPanel(new FlowLayout());
        JLabel wingsp = new JLabel("wingspan(*) = ");
        JTextField wingspField = new JTextField(5);
        JComboBox<String> wingspBox = new JComboBox<String>(len);
        wingspPanel.add(wingsp);
        wingspPanel.add(wingspField);
        wingspPanel.add(wingspBox);
        wingspPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        metrics1.add(wingspPanel);

        //CHORD
        JPanel chordPanel = new JPanel(new FlowLayout());
        JLabel chord = new JLabel("chord(*) = ");
        JTextField chordField = new JTextField(5);
        JComboBox<String> chordBox = new JComboBox<String>(len);
        chordPanel.add(chord);
        chordPanel.add(chordField);
        chordPanel.add(chordBox);
        chordPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        metrics1.add(chordPanel);

        //WING INCIDENCE
        JPanel incidencePanel = new JPanel(new FlowLayout());
        JLabel incidence = new JLabel("wing incidence(*) = ");
        JTextField incidencField = new JTextField(5);
        JComboBox<String> incidenceBox = new JComboBox<String>(deg);
        incidencePanel.add(incidence);
        incidencePanel.add(incidencField);
        incidencePanel.add(incidenceBox);
        incidencePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        metrics1.add(incidencePanel);
        
        metricsPanel.add(metrics1);


        JPanel metrics2 = new JPanel();
        metrics2.setLayout(new BoxLayout(metrics2,BoxLayout.Y_AXIS));

        //H TAIL AREA
        JPanel htailarPanel = new JPanel(new FlowLayout());
        JLabel htailarea = new JLabel("htailarea(*) = ");
        JTextField htailarField = new JTextField(5);
        JComboBox<String> htailarBox = new JComboBox<String>(area);
        htailarPanel.add(htailarea);
        htailarPanel.add(htailarField);
        htailarPanel.add(htailarBox);
        metrics2.add(htailarPanel);

        //H TAIL ARM
        JPanel htailarmPanel = new JPanel(new FlowLayout());
        JLabel htailarm = new JLabel("htailarm(*) = ");
        JTextField htailarmField = new JTextField(5);
        JComboBox<String> htailarmBox = new JComboBox<String>(len);
        htailarmPanel.add(htailarm);
        htailarmPanel.add(htailarmField);
        htailarmPanel.add(htailarmBox);
        metrics2.add(htailarmPanel);

        //V TAIL AREA
        JPanel vtailarPanel = new JPanel(new FlowLayout());
        JLabel vtailar = new JLabel("vtailarea(*) = ");
        JTextField vtailarField = new JTextField(5);
        JComboBox<String> vtailarBox = new JComboBox<String>(area);
        vtailarPanel.add(vtailar);
        vtailarPanel.add(vtailarField);
        vtailarPanel.add(vtailarBox);
        metrics2.add(vtailarPanel);

        //V TAIL ARM
        JPanel vtailarmPanel = new JPanel(new FlowLayout());
        JLabel vtailarm = new JLabel("vtailarm(*) = ");
        JTextField vtailarmField = new JTextField(5);
        JComboBox<String> vtailarmBox = new JComboBox<String>(len);
        vtailarmPanel.add(vtailarm);
        vtailarmPanel.add(vtailarmField);
        vtailarmPanel.add(vtailarmBox);
        metrics2.add(vtailarmPanel);
        
        metricsPanel.add(metrics2);

        add(metricsPanel);

        //Aerodynamic Reference Point Panel/////////////////
        JPanel aeroRef = new JPanel(new FlowLayout());
        aeroRef.setBorder(new TitledBorder("Aerodynamic Reference Point(*)"));
        //x 
        JLabel xAeroRef = new JLabel("x = ");
        aeroRef.add(xAeroRef);
        JTextField xAeroRefTextField = new JTextField("0",10 );   
        aeroRef.add(xAeroRefTextField);
        //y 
        JLabel yAeroRef = new JLabel("y = ");
        aeroRef.add(yAeroRef);
        JTextField yAeroRefTextField = new JTextField("0",10 );   
        aeroRef.add(yAeroRefTextField);
        //z 
        JLabel zAeroRef = new JLabel("z = ");
        aeroRef.add(zAeroRef);
        JTextField zAeroRefTextField = new JTextField("0" ,10);   
        aeroRef.add(zAeroRefTextField);
        //unit
        JLabel unitAeroRef = new JLabel("Units");
        aeroRef.add(unitAeroRef);
        String[] units = {"IN","M","FT"};
        JComboBox<String> unitAeroRefBox = new JComboBox<String>(units);   
        aeroRef.add(unitAeroRefBox);


        //Eye Point Panel/////////////////
        JPanel eyePt = new JPanel(new FlowLayout());
        eyePt.setBorder(new TitledBorder("Eye Point(*)"));
        //x 
        JLabel xEyePt = new JLabel("x = ");
        eyePt.add(xEyePt);
        JTextField xEyePtTextField = new JTextField("0",10);   
        eyePt.add(xEyePtTextField);
        //y 
        JLabel yEyePt = new JLabel("y = ");
        eyePt.add(yEyePt);
        JTextField yEyePtTextField = new JTextField("0",10);   
        eyePt.add(yEyePtTextField);
        //z 
        JLabel zEyePt = new JLabel("z = ");
        eyePt.add(zEyePt);
        JTextField zEyePtTextField = new JTextField("0" ,10);   
        eyePt.add(zEyePtTextField);
        //unit
        JLabel unitEyePt = new JLabel("Units");
        eyePt.add(unitEyePt);
        JComboBox<String> unitEyeBox = new JComboBox<String>(units);   
        eyePt.add(unitEyeBox);

        //Visual Reference Point Panel/////////////////
        JPanel visual = new JPanel(new FlowLayout());
        visual.setBorder(new TitledBorder("Visual Reference Point(*)"));
        //x 
        JLabel xVisual = new JLabel("x = ");
        visual.add(xVisual);
        JTextField xVisualTextField = new JTextField("0" ,10);   
        visual.add(xVisualTextField);
        //y 
        JLabel yVisual = new JLabel("y = ");
        visual.add(yVisual);
        JTextField yVisuaTextField = new JTextField("0" ,10);   
        visual.add(yVisuaTextField);
        //z 
        JLabel zVisual = new JLabel("z = ");
        visual.add(zVisual);
        JTextField zVisualTextField = new JTextField("0" ,10);   
        visual.add(zVisualTextField);
        //unit
        JLabel unitVis = new JLabel("Units");
        visual.add(unitVis);
        JComboBox<String> unitVisBox = new JComboBox<String>(units);   
        visual.add(unitVisBox);
        
        add(aeroRef);
        add(eyePt);
        add(visual);
    }
}
