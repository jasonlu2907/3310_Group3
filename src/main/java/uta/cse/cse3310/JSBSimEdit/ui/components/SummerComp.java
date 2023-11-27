package uta.cse.cse3310.JSBSimEdit.ui.components;

import generated.Clipto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

public class SummerComp extends JLabel {
    private String description;
    private final List<String> input;
    private final Float bias;
    private final Clipto clipto;
    private String output;
    private final String name;
    private int offsetX, offsetY;

    public SummerComp(String desc, List<String> input, Float bias, Clipto clipto, String output, String name, String file) {
        super(new ImageIcon(file));

        this.description = desc;
        this.input = input;
        this.bias = bias;
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
                showIconDetailsDialog(SummerComp.this);
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

    private void showIconDetailsDialog(SummerComp summer) {
        JFrame detailsFrame = new JFrame("Summer Component");
        detailsFrame.setSize(400, 600);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Basic", createBasicPane(summer));
        tabbedPane.add("Input", createInputPane(summer));

        detailsFrame.add(tabbedPane);

        detailsFrame.setLocationRelativeTo(this);
        detailsFrame.setVisible(true);
    }

    private JPanel createBasicPane(SummerComp summer) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /*Components inside the Basic Pane*/
        // Information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel name = new JLabel("Name: ");
        JTextField name_field = new JTextField(summer.name, 7);
        JLabel type = new JLabel("Type: ");
        JLabel type_field = new JLabel("SUMMER");

        infoPanel.add(name);
        infoPanel.add(name_field);
        infoPanel.add(type);
        infoPanel.add(type_field);

        // Clipper
        JPanel clipperPanel = new JPanel();
        clipperPanel.setBorder(new TitledBorder("Clipper:"));
        clipperPanel.setLayout(new BoxLayout(clipperPanel, BoxLayout.Y_AXIS));

        JLabel min = new JLabel("Min: ");
        String min_value = (summer.clipto != null ? String.valueOf(summer.clipto.getMin()) : "0");
        JTextField min_field = new JTextField(min_value, 15);
        JLabel max = new JLabel("Max: ");
        String max_value = (summer.clipto != null ? String.valueOf(summer.clipto.getMax()) : "0");
        JTextField max_field = new JTextField(max_value, 15);

        clipperPanel.add(min);
        clipperPanel.add(min_field);
        clipperPanel.add(max);
        clipperPanel.add(max_field);

        JLabel bias = new JLabel("Bias: ");
        String bias_value = (summer.bias != null ? String.valueOf(summer.bias) : "0");
        JTextField bias_field = new JTextField(bias_value, 10);

        panel.add(infoPanel);
        panel.add(clipperPanel);
        panel.add(bias);
        panel.add(bias_field);

        return panel;
    }

    private JPanel createInputPane(SummerComp summer) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        List<String> input = summer.input;
        DefaultListModel<String> inputListModel = new DefaultListModel<>();
        for(String str : input) {
            inputListModel.addElement(str);
        }

        JList<String> inputList = new JList<>(inputListModel);

        panel.add(new JScrollPane(inputList), BorderLayout.CENTER);

        return panel;
    }
}
