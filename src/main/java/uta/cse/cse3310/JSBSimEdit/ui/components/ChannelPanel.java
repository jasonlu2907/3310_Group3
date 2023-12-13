package uta.cse.cse3310.JSBSimEdit.ui.components;

import uta.cse.cse3310.JSBSimEdit.ui.components.flightcontrol.DraggableIcon;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ChannelPanel extends JPanel {
    private final PaintPanel paintPanel;
    private final HashMap<Integer, PaintPanel.Line> lines;
    private final int totalLine;
    public ChannelPanel(List<Object> ActuatorOrAerosurfaceScaleOrIntegrator) {
        super();
        setLayout(new BorderLayout());

        //////////////////TOOLBAR/////////////////////////////////
        JToolBar toolbar = createToolBarWithButtons();

        JButton deleteLine = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/delete.png"));
        deleteLine.setActionCommand("Delete Line");
        deleteLine.setToolTipText("Delete line");
        deleteLine.addActionListener(event -> onDelete());

        toolbar.add(deleteLine);
        add(toolbar, BorderLayout.NORTH);

        ////////////////////Paint Panel/////////////////////////
        paintPanel = new PaintPanel(ActuatorOrAerosurfaceScaleOrIntegrator);
        lines = paintPanel.getLines();
        totalLine = paintPanel.getLines().size();

        add(paintPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    /////////////// BUTTON EVENTS
    private JToolBar createToolBarWithButtons() {
        JToolBar toolbar = new JToolBar("Toolbar");

        JButton srcButton = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/input.png"));
        srcButton.setActionCommand("Source");
        srcButton.setToolTipText("Create new source");
        srcButton.addActionListener(event -> onNewClick("Source", "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/input.png"));
        toolbar.add(srcButton);

        JButton destButton = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/destination.png"));
        destButton.setActionCommand("Destination");
        destButton.setToolTipText("Create new destination");
        destButton.addActionListener(event -> onNewClick("Destination", "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/destination.png"));
        toolbar.add(destButton);

        toolbar.add(Box.createHorizontalStrut(25));

        JButton summerButton = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/summer.png"));
        summerButton.setActionCommand("Summer");
        summerButton.setToolTipText("Create new Summer");
        summerButton.addActionListener(event -> onNewClick("Summer", "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/summer.png"));
        toolbar.add(summerButton);

        JButton pidButton = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/pid.png"));
        pidButton.setActionCommand("PID");
        pidButton.setToolTipText("Create new PID");
        pidButton.addActionListener(event -> onNewClick("PID", "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/pid.png"));
        toolbar.add(pidButton);

        JButton gainButton = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/gain.png"));
        gainButton.setActionCommand("Gain");
        gainButton.setToolTipText("Create new Gain");
        gainButton.addActionListener(event -> onNewClick("Gain", "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/gain.png"));
        toolbar.add(gainButton);

        JButton filterButton = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/filter.png"));
        filterButton.setActionCommand("Filter");
        filterButton.setToolTipText("Create new Filter");
        filterButton.addActionListener(event -> onNewClick("Filter", "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/filter.png"));
        toolbar.add(filterButton);

        JButton deadbandButton = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/deadband.png"));
        deadbandButton.setActionCommand("Dead Band");
        deadbandButton.setToolTipText("Create new Dead Band");
        deadbandButton.addActionListener(event -> onNewClick("Dead Band", "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/deadband.png"));
        toolbar.add(deadbandButton);

        JButton switchButton = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/switch.png"));
        switchButton.setActionCommand("Switch");
        switchButton.setToolTipText("Create new Switch");
        switchButton.addActionListener(event -> onNewClick("Switch", "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/switch.png"));
        toolbar.add(switchButton);

        JButton kinematicButton = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/kinemat.png"));
        kinematicButton.setActionCommand("Kinematic");
        kinematicButton.setToolTipText("Create new Kinematic");
        kinematicButton.addActionListener(event -> onNewClick("Kinemat", "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/kinemat.png"));
        toolbar.add(kinematicButton);

        JButton fcsButton = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/FCSFunction.png"));
        fcsButton.setActionCommand("FCSFunction");
        fcsButton.setToolTipText("Create new FCSFunction");
        fcsButton.addActionListener(event -> onNewClick("FCS Function", "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/FCSFunction.png"));
        toolbar.add(fcsButton);

        JButton nodeButton = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/node.png"));
        nodeButton.setActionCommand("Node");
        nodeButton.setToolTipText("Create new Node");
        nodeButton.addActionListener(event -> onNewClick("Node", "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/node.png"));
        toolbar.add(nodeButton);

        toolbar.add(Box.createHorizontalStrut(25));

        return toolbar;
    }

    private void onNewClick(String text, String file) {
        Random rand = new Random();

        DraggableIcon icon = new DraggableIcon(text, file);
        icon.setBounds(rand.nextInt(100), rand.nextInt(100), 50, 50);
        paintPanel.add(icon);

        // paintPanel has been updated, need to repaint
        paintPanel.repaint();
    }

    private void onDelete() {
//        ArrayList<PaintPanel.Line> lines = paintPanel.getLines();
        JLabel selectedLine = new JLabel("<HTML><br/>Line number to delete: </HTML>");
        JTextField selectedLine_texts = new JTextField(10);

        Object[] objects = {
                selectedLine, selectedLine_texts
        };

        int button = JOptionPane.showConfirmDialog( // Show the dialog
                this,
                objects,
                "Delete A Line",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (button == JOptionPane.OK_OPTION) {
            if (selectedLine_texts.getText() != null) {
                int selectedLineNum = Integer.parseInt(selectedLine_texts.getText());
                if (selectedLineNum >= 0 && selectedLineNum < totalLine) {
                    lines.remove(selectedLineNum);
                    paintPanel.setLines(lines);
                    paintPanel.repaint();
                } else {
                    JLabel err = new JLabel("<HTML>Error! Line number does not exist! </HTML>");
                    JOptionPane.showMessageDialog(this, err);
                }
            }
        }
    }
}
