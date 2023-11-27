package uta.cse.cse3310.JSBSimEdit.ui.components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import generated.Direction;
import generated.Emptywt;
import generated.Force;
import generated.InertiaType;
import generated.Ixx;
import generated.Ixy;
import generated.Ixz;
import generated.Iyy;
import generated.Iyz;
import generated.Izz;
import generated.LengthType;
import generated.Location;
import generated.Pointmass;
import generated.Weight;
import generated.WeightType;

public class MassBalance extends JPanel{
    private final List<Pointmass> pointmasses;

    private int selectedIndex;
    private final JList<String> itemList;
    public MassBalance(Ixx ixx, Iyy iyy, Izz izz, Ixy ixy, Ixz ixz, Iyz iyz, Emptywt emptywt, Location location, List<Pointmass> pointmasses){
        this.pointmasses = pointmasses;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        WeightType[] weightType = {WeightType.KG,WeightType.LBS};
        LengthType[] lenType = {LengthType.FT, LengthType.IN, LengthType.M};
        InertiaType[] inertiaType = {InertiaType.KG_M_2,InertiaType.SLUG_FT_2};

        PanelBuilder pb = new PanelBuilder();

        // Create a JList with the listModel
        DefaultListModel<String> stringModels = generateList();
        itemList = new JList<>(stringModels);

        itemList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Detect double-click
                    selectedIndex = itemList.getSelectedIndex();
                    onDetailClick(selectedIndex);
                }
            }
        });

        // Automatically select the first item
        itemList.setSelectedIndex(0);
        selectedIndex = itemList.getSelectedIndex();

        //Mass////////////////
        JPanel massPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        massPanel.setBorder(new TitledBorder("Mass"));
        massPanel.add(pb.LabelFieldBoxPanel("Empty Weight(*)", String.valueOf(emptywt.getValue()), weightType, emptywt.getUnit()));
        add(massPanel);

        //LOCATION////////////
        JPanel locationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        locationPanel.setBorder(new TitledBorder("Location(*)"));
        locationPanel.add(pb.LabelFieldPanel("x = ", String.valueOf(location.getX())));
        locationPanel.add(pb.LabelFieldPanel("y = ", String.valueOf(location.getY())));
        locationPanel.add(pb.LabelFieldBoxPanel("z = ", String.valueOf(location.getZ()),lenType,location.getUnit()));
        add(locationPanel);

        //MOMENT OF THE INERTIA////////////
        JPanel momentInertiaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        momentInertiaPanel.setBorder(new TitledBorder("Moment of Inertia(*)"));

        //FIRST COLUMN
        JPanel momentInertiaPanel1 = new JPanel();
        momentInertiaPanel1.setLayout(new BoxLayout(momentInertiaPanel1, BoxLayout.Y_AXIS));

        JPanel ixxPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ixxPanel.add(pb.LabelFieldBoxPanel("Ixx = ", String.valueOf(ixx.getValue()), inertiaType, ixx.getUnit()));
        momentInertiaPanel1.add(ixxPanel);

        JPanel iyyPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        iyyPanel.add(pb.LabelFieldBoxPanel("Iyy = ", String.valueOf(iyy.getValue()), inertiaType, iyy.getUnit()));
        momentInertiaPanel1.add(iyyPanel);

        JPanel izzPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        izzPanel.add(pb.LabelFieldBoxPanel("Izz = ", String.valueOf(izz.getValue()), inertiaType, izz.getUnit()));
        momentInertiaPanel1.add(izzPanel);

        //SECOND COLUMN
        JPanel momentInertiaPanel2 = new JPanel();
        momentInertiaPanel2.setLayout(new BoxLayout(momentInertiaPanel2, BoxLayout.Y_AXIS));

        JPanel ixzPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ixzPanel.add(pb.LabelFieldBoxPanel("Ixz = ", String.valueOf(ixz.getValue()), inertiaType, ixz.getUnit()));
        momentInertiaPanel2.add(ixzPanel);

        JPanel iyzPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        iyzPanel.add(pb.LabelFieldBoxPanel("Iyz = ", String.valueOf(iyz.getValue()), inertiaType, iyz.getUnit()));
        momentInertiaPanel2.add(iyzPanel);

        JPanel ixyPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ixyPanel.add(pb.LabelFieldBoxPanel("Ixy = ", String.valueOf(ixy.getValue()), inertiaType, ixy.getUnit()));
        momentInertiaPanel2.add(ixyPanel);

        momentInertiaPanel.add(momentInertiaPanel1);
        momentInertiaPanel.add(momentInertiaPanel2);

        add(momentInertiaPanel);

        //POINT MASS////////////////////////////////
        JPanel pmPanel = new JPanel(new BorderLayout());
        pmPanel.setBorder(new TitledBorder("Point Mass"));
        pmPanel.add(new JScrollPane(itemList),BorderLayout.CENTER);

        //add button
        JButton newPM = new JButton("Add a new Point Mass");
        newPM.setActionCommand("Add Point Mass");
        newPM.setToolTipText("Add new Point Mass");
        newPM.addActionListener(event -> onAddPMClick());

        //delete button
        JButton deletePM = new JButton("Delete a Point Mass");
        deletePM.setActionCommand("Delete Point Mass");
        deletePM.setToolTipText("Delete Point Mass");
        deletePM.addActionListener(event -> {
            selectedIndex = itemList.getSelectedIndex();
            onDeletePMClick(selectedIndex);
        });

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(newPM);
        buttons.add(deletePM);

        pmPanel.add(buttons,BorderLayout.PAGE_END);

        add(pmPanel);

    }

    protected void onDetailClick(int index) {
        PanelBuilder pb = new PanelBuilder();
        Pointmass currentPM = pointmasses.get(index);

        Object[][] objects = pb.PointmassTemplate(currentPM);
        ImageIcon engineIcon = new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/car.png");
        int button = JOptionPane.showConfirmDialog( // Show the dialog
                this,
                objects,
                "New Force",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                engineIcon);

        if (button == JOptionPane.OK_OPTION) { // If OK clicked, show data
            currentPM.setName((String)((javax.swing.JComboBox)objects[0][1]).getSelectedItem());

            currentPM.getLocation().setX(Double.parseDouble(((javax.swing.JTextField)objects[1][2]).getText()));
            currentPM.getLocation().setY(Double.parseDouble(((javax.swing.JTextField)objects[1][4]).getText()));
            currentPM.getLocation().setZ(Double.parseDouble(((javax.swing.JTextField)objects[1][6]).getText()));
            currentPM.getLocation().setUnit((String)((javax.swing.JComboBox)objects[1][8]).getSelectedItem());

            currentPM.getWeight().setValue((Double.parseDouble(((javax.swing.JTextField)objects[2][1]).getText())));
            currentPM.getWeight().setUnit((WeightType)((javax.swing.JComboBox)objects[2][3]).getSelectedItem());


            // update the JList
            updateJList();
        }

    }


    protected void onAddPMClick() {
        PanelBuilder pb = new PanelBuilder();

        Object[][] objects = pb.PointmassTemplate(null);
        ImageIcon engineIcon = new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/car.png");
        int button = JOptionPane.showConfirmDialog( // Show the dialog
                this,
                objects,
                "New Pointmass",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                engineIcon);

        if (button == JOptionPane.OK_OPTION) { // If OK clicked, show data
            // Add the new pm to the list of pm
            Pointmass newPM = new Pointmass();
            newPM.setName((String)((javax.swing.JComboBox)objects[0][1]).getSelectedItem());

            Location newLocation = new Location();
            newLocation.setX(Double.parseDouble(((javax.swing.JTextField)objects[1][2]).getText()));
            newLocation.setY(Double.parseDouble(((javax.swing.JTextField)objects[1][4]).getText()));
            newLocation.setZ(Double.parseDouble(((javax.swing.JTextField)objects[1][6]).getText()));
            newLocation.setUnit((String)((javax.swing.JComboBox)objects[1][8]).getSelectedItem());
            newPM.setLocation(newLocation);

            Weight newWeight = new Weight();
            newWeight.setValue((Double.parseDouble(((javax.swing.JTextField)objects[2][1]).getText())));
            newWeight.setUnit((WeightType)((javax.swing.JComboBox)objects[2][3]).getSelectedItem());
            newPM.setWeight(newWeight);


            // update the JList
            updateJList();
        }
    }

    protected void onDeletePMClick(int index) {
        this.pointmasses.remove(index);

        // update JList
        updateJList();
    }

    private DefaultListModel<String> generateList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        // Add some items to the list
        for(Pointmass pm : pointmasses) {
            listModel.addElement("Pilot is " + pm.getWeight().getValue() + " at point [" + pm.getLocation().getX() + ", "
                    + pm.getLocation().getY() + ", " + pm.getLocation().getZ() + "] in " + pm.getLocation().getUnit());
        }

        return listModel;
    }

    private void updateJList() {
        DefaultListModel<String> stringModels = generateList();
        String[] stringArray = new String[stringModels.size()];
        for (int i = 0; i < stringModels.size(); i++) {
            stringArray[i] = stringModels.getElementAt(i);
        }
        itemList.setListData(stringArray);
    }

    public List<Pointmass> getPointmasses() {
        return pointmasses;
    }




}
