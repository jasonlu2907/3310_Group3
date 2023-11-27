package uta.cse.cse3310.JSBSimEdit.ui.components;

import generated.Clipto;
import generated.Default;
import generated.Test;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

public class SwitchComponent extends JLabel {
    private String description;
    private String input;
    private final Default _default;
    private final List<Test> test;
    private final Clipto clipto;
    private Object output;
    private final String name;
    private int offsetX, offsetY;

    public SwitchComponent(String desc, String input, Default def, List<Test> test,
                           Clipto clipto, Object output, String name, String file) {
        super(new ImageIcon(file));

        this.description = desc;
        this.input = input;
        this._default = def;
        this.test = test;
        this.clipto = clipto;
        this.output = output;
        this.name = name;

        ClickListener clickListener = new ClickListener();
        this.addMouseListener(clickListener);

        DragListener dragListener = new DragListener();
        this.addMouseMotionListener(dragListener);
    }

    private class ClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                // for now only print the kinematic details
                showIconDetailsDialog(SwitchComponent.this);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // If the mouse is pressed on a label, set the offsets for dragging
            offsetX = e.getX();
            offsetY = e.getY();
        }
    }

    private class DragListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            int newX = getX() + e.getX() - offsetX;
            int newY = getY() + e.getY() - offsetY;
            setLocation(newX, newY);

            // Repaint the panel (optional, as the layout is set to null)
//            repaint();
        }
    }

    private void showIconDetailsDialog(SwitchComponent aSwitch) {
        JFrame detailsFrame = new JFrame("Switch Component");
        detailsFrame.setSize(400, 400);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Basic", createBasicPane(aSwitch));
        tabbedPane.add("Input", createInputPane(aSwitch));

        detailsFrame.add(tabbedPane);

        detailsFrame.setLocationRelativeTo(this);
        detailsFrame.setVisible(true);
    }

    private JPanel createBasicPane(SwitchComponent switc) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /*Components inside the Basic Pane*/
        // Information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        JLabel name = new JLabel("Name: ");
        JTextField name_field = new JTextField(switc.name, 15);
        namePanel.add(name);
        namePanel.add(name_field);

        JPanel typePanel = new JPanel();
        typePanel.setLayout(new FlowLayout());
        JLabel type = new JLabel("Type: ");
        JLabel type_field = new JLabel("SWITCH");
        typePanel.add(type);
        typePanel.add(type_field);

        infoPanel.add(namePanel);
        infoPanel.add(typePanel);

        // Clipper
        JPanel clipperPanel = new JPanel();
        clipperPanel.setBorder(new TitledBorder("Clipper:"));
        clipperPanel.setLayout(new BoxLayout(clipperPanel, BoxLayout.Y_AXIS));

        JPanel minPanel = new JPanel();
        minPanel.setLayout(new FlowLayout());
        JLabel min = new JLabel("Min: ");
        String min_value = (switc.clipto != null ? String.valueOf(switc.clipto.getMin()) : "0");
        JTextField min_field = new JTextField(min_value, 15);
        minPanel.add(min);
        minPanel.add(min_field);

        JPanel maxPanel = new JPanel();
        maxPanel.setLayout(new FlowLayout());
        JLabel max = new JLabel("Max: ");
        String max_value = (switc.clipto != null ? String.valueOf(switc.clipto.getMax()) : "0");
        JTextField max_field = new JTextField(max_value, 15);
        maxPanel.add(max);
        maxPanel.add(max_field);

        clipperPanel.add(minPanel);
        clipperPanel.add(maxPanel);

        JPanel defaultPanel = new JPanel();
        defaultPanel.setLayout(new FlowLayout());
        JLabel defaultLabel = new JLabel("Default Value: ");
        String default_value = String.valueOf(switc._default.getValue());
        JTextField default_field = new JTextField(default_value, 10);
        defaultPanel.add(defaultLabel);
        defaultPanel.add(default_field);

        panel.add(infoPanel);
        panel.add(clipperPanel);
        panel.add(defaultPanel);

        return panel;
    }

    private JPanel createInputPane(SwitchComponent switc) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        List<Test> tests = switc.test;
        DefaultListModel<String> inputListModel = new DefaultListModel<>();
        for (Test test : tests) {
            String[] temp = test.getValue().split("\\s+");
//            System.out.println(temp[temp.length - 1]);
            double doubt = Double.parseDouble(temp[temp.length - 1]);
            inputListModel.addElement( doubt < 0 ? "negative" : "positive");
        }

        JList<String> inputList = new JList<>(inputListModel);

        panel.add(new JScrollPane(inputList), BorderLayout.CENTER);

        return panel;
    }
}
