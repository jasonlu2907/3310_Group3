package uta.cse.cse3310.JSBSimEdit.UIComponents;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uta.cse.cse3310.JSBSimEdit.Helper.CollapsablePanel;

public class Aerodynamics extends JPanel{
    public Aerodynamics(){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        JPanel example = new JPanel();
        JLabel ex = new JLabel("This is just an example");
        example.add(ex);
        CollapsablePanel aerodynamics = new CollapsablePanel("Aerodynamics",example);

        add(aerodynamics);
    }
}
