package uta.cse.cse3310.JSBSimEdit.ui.components;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import generated.LengthType;
import generated.Location;
import generated.Pointmass;
import generated.Weight;
import generated.WeightType;

public class PanelBuilder extends JPanel{
    //makes a jlabel and a field to go with it
    //INPUTS/////////////////////////////////////////////////////////
    ////label = the text of the jlabel
    ////field = the text in the jtextfield
    //OUTPUTS////////////////////////////////////////////////////////
    //returns a panel with the label and then the field next to it
    public JPanel LabelFieldPanel (String label, String field){
        JLabel jlabel = new JLabel(label);
        JTextField jtextfield = new JTextField(field, 20);
        JPanel LFPanel = new JPanel();
        LFPanel.add(jlabel);
        LFPanel.add(jtextfield);
        LFPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return LFPanel;
    }

    //makes a jlabel and a field and a combo box to go with it
    //INPUTS/////////////////////////////////////////////////////////
    ////label = the text of the jlabel
    ////field = the text in the jtextfield
    ////Generic array for the box contents
    ////Generic object for the obj to be selected
    //OUTPUTS////////////////////////////////////////////////////////
    ////returns a panel with the label and then the field and box next to it
    public <T> JPanel LabelFieldBoxPanel (String label, String field, T[] boxContents, T selectedItem){
        JLabel jlabel = new JLabel(label);
        JTextField jtextfield = new JTextField(field, 20);
        JComboBox<T> jComboBox = new JComboBox<T>(boxContents);
        jComboBox.setSelectedItem(selectedItem);
        JPanel LFBPanel = new JPanel();

        LFBPanel.add(jlabel);
        LFBPanel.add(jtextfield);
        LFBPanel.add(jComboBox);
        LFBPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return LFBPanel;
    }

    public Object[] LocationTemplate(Location loc){
        JLabel location = new JLabel("<HTML><br/>Location:</HTML>");
        JLabel location_x = new JLabel("<HTML><br/>x = </HTML>");
        JTextField location_x_texts = new JTextField(loc!=null ? String.valueOf(loc.getX()):"",10);
        JLabel location_y = new JLabel("<HTML><br/>y = </HTML>");
        JTextField location_y_texts = new JTextField(loc!=null ? String.valueOf(loc.getY()):"",10);
        JLabel location_z = new JLabel("<HTML><br/>z = </HTML>");
        JTextField location_z_texts = new JTextField(loc!=null ? String.valueOf(loc.getZ()):"",10);

        JLabel unit = new JLabel("Unit");
        LengthType[] unitTypes = {LengthType.FT,LengthType.IN,LengthType.M};
        JComboBox<LengthType> unitsBox = new JComboBox<>(unitTypes);
        unitsBox.setSelectedItem((loc!=null ? loc.getUnit():unitTypes[0]));


        Object[] objects = { // Array of widgets to display
                location, location_x, location_x_texts, location_y, location_y_texts, location_z, location_z_texts,
                unit, unitsBox
        };

        return objects;
    }

    public Object[] WeightTemplate(Weight weight){
        JLabel value = new JLabel("<HTML><br/>Value:</HTML>");
        JTextField valueField = new JTextField(weight!=null ? String.valueOf(weight.getValue()):"",10);

        JLabel unit = new JLabel("Unit");
        WeightType[] unitTypes = {WeightType.KG,WeightType.LBS};
        JComboBox<WeightType> unitsBox = new JComboBox<>(unitTypes);
        unitsBox.setSelectedItem((weight!=null ? weight.getUnit():unitTypes[0]));

        Object[] objects = { // Array of widgets to display
                value, valueField,
                unit, unitsBox
        };

        return objects;
    }

    public Object[][] PointmassTemplate(Pointmass pm){
        JLabel name = new JLabel("Name");
        String[] nameOptions = {"weight", "location"};
        JComboBox<String> names = new JComboBox<>(nameOptions);
        names.setSelectedItem((pm!=null ? pm.getName():nameOptions[0]));

        Object[][] objects = { // Array of widgets to display
                {name,names},
                LocationTemplate(pm!=null ? pm.getLocation():null), WeightTemplate(pm!=null ? pm.getWeight():null)
        };
        return objects;
    }
}