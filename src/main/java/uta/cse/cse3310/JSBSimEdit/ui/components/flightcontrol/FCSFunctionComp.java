package uta.cse.cse3310.JSBSimEdit.ui.components.flightcontrol;

import generated.Clipto;
import generated.Function;
import generated.Product;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class FCSFunctionComp extends JLabel {
    private String input;
    private String description;
    private Function function;
    private Clipto clipto;
    private String output;
    private String name;
    private int offsetX, offsetY;

    public FCSFunctionComp(String input, String desc, Function function, Clipto clipto, String output, String name, String file) {
        super(new ImageIcon(file));
        this.input = input;
        this.description = desc;
        this.function = function;
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
                showIconDetailsDialog(FCSFunctionComp.this);
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

    private void showIconDetailsDialog(FCSFunctionComp fcs) {
        JFrame detailsFrame = new JFrame("Switch Component");
        detailsFrame.setSize(400, 400);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Basic", createBasicPane(fcs));
        tabbedPane.add("Product", createProductPane(fcs));

        detailsFrame.add(tabbedPane);

        detailsFrame.setLocationRelativeTo(this);
        detailsFrame.setVisible(true);
    }

    private JPanel createBasicPane(FCSFunctionComp fcs) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /*Components inside the Basic Pane*/
        // Information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        JLabel name = new JLabel("Name: ");
        JTextField name_field = new JTextField(fcs.name, 15);
        namePanel.add(name);
        namePanel.add(name_field);

        JPanel typePanel = new JPanel();
        typePanel.setLayout(new FlowLayout());
        JLabel type = new JLabel("Type: ");
        JLabel type_field = new JLabel("FCSFunction");
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
        String min_value = (fcs.clipto != null ? String.valueOf(fcs.clipto.getMin()) : "0");
        JTextField min_field = new JTextField(min_value, 15);
        minPanel.add(min);
        minPanel.add(min_field);

        JPanel maxPanel = new JPanel();
        maxPanel.setLayout(new FlowLayout());
        JLabel max = new JLabel("Max: ");
        String max_value = (fcs.clipto != null ? String.valueOf(fcs.clipto.getMax()) : "0");
        JTextField max_field = new JTextField(max_value, 15);
        maxPanel.add(max);
        maxPanel.add(max_field);

        clipperPanel.add(minPanel);
        clipperPanel.add(maxPanel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        JLabel inputLabel = new JLabel("Input: ");
//        String input_value = String.valueOf(fcs.input);
        JTextField input_field = new JTextField(String.valueOf(fcs.input), 10);
        inputPanel.add(inputLabel);
        inputPanel.add(input_field);

        panel.add(infoPanel);
        panel.add(clipperPanel);
        panel.add(inputPanel);

        return panel;
    }

    private JPanel createProductPane(FCSFunctionComp fcs) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        Product products = fcs.function.getProduct();
        DefaultListModel<String> inputListModel = new DefaultListModel<>();

        for (Object obj : products.getTableOrProductOrDifference()) {
//            String[] temp = test.getValue().split("\\s+");
//            System.out.println(temp[temp.length - 1]);
//            double doubt = Double.parseDouble(temp[temp.length - 1]);
            inputListModel.addElement(obj.toString());
        }

        JList<String> inputList = new JList<>(inputListModel);

        panel.add(new JScrollPane(inputList), BorderLayout.CENTER);

        return panel;
    }
}
