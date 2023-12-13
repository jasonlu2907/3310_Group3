package uta.cse.cse3310.JSBSimEdit.ui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import generated.Capacity;
import generated.Contents;
import generated.Engine;
import generated.FdmConfig;
import generated.Tank;
import generated.Thruster;
import uta.cse.cse3310.JSBSimEdit.ui.windows.CommanderWindow;
import generated.Location;
import generated.Orient;

public class Propulsion extends JPanel {
  private final CommanderWindow mainFrame;
  private JSONObject engineMgr;
  private FdmConfig cfg;

  private ArrayList<Engine> enginesList;
  private HashMap<Integer, Integer> enginesIndexMap;

  private ArrayList<Tank> tanksList;
  private HashMap<Integer, Integer> tanksIndexMap;

  private ArrayList<Thruster> thrustersList;

  private String[] contentsUnits = { "LBS", "KG" };
  private String[] capacityUnits = { "LBS", "KG" };
  private String[] locationUnits = { "FT", "M", "IN" };
  private String[] orientUnits = { "DEG", "RAD" };

  public Propulsion(FdmConfig cfg, JSONObject engineMgr, CommanderWindow mainFrame) {
    this.mainFrame = mainFrame;
    this.cfg = cfg;
    this.engineMgr = engineMgr;
    this.setLayout(new BorderLayout());

    this.enginesList = new ArrayList<Engine>();
    this.enginesIndexMap = new HashMap<Integer, Integer>();

    this.tanksList = new ArrayList<Tank>();
    this.tanksIndexMap = new HashMap<Integer, Integer>();

    this.thrustersList = new ArrayList<Thruster>();

    for (Integer i = 0; i < cfg.getPropulsion().getEngineOrTank().size(); i++) {
      Object engineOrTank = cfg.getPropulsion().getEngineOrTank().get(i);

      if (engineOrTank instanceof Engine) {
        Engine engine = (Engine) engineOrTank;
        enginesList.add(engine);
        enginesIndexMap.put(enginesList.indexOf(engine), i);

        thrustersList.add(engine.getThruster());
      } else if (engineOrTank instanceof Tank) {
        Tank tank = (Tank) engineOrTank;
        tanksList.add(tank);
        tanksIndexMap.put(tanksList.indexOf(tank), i);
      }
    }

    JPanel AEPanel = createAvailableEnginesPanel();
    JPanel ATPanel = createAvailableThrustersPanel();
    JPanel SEPanel = createSubscribedEnginesPanel();
    JPanel TankPanel = createTanksPanel();

    JPanel mainPanel = new JPanel(new GridLayout(1, 4));
    mainPanel.add(AEPanel);
    mainPanel.add(ATPanel);
    mainPanel.add(SEPanel);
    mainPanel.add(TankPanel);

    add(mainPanel);
  }

  protected JPanel createAvailableEnginesPanel() {
    JSONArray availableEngines = (JSONArray) engineMgr.get("engines");

    DefaultListModel<String> availableEngineModel = new DefaultListModel<String>();
    for (int i = 0; i < availableEngines.size(); i++) {
      JSONObject obj = (JSONObject) availableEngines.get(i);
      availableEngineModel.addElement((String) obj.get("name"));
    }
    JList<String> availableEngineListDisplay = new JList<>(availableEngineModel);

    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(new TitledBorder("Available Engines"));
    panel.add(new JScrollPane(availableEngineListDisplay), BorderLayout.CENTER);

    return panel;
  }

  protected JPanel createAvailableThrustersPanel() {
    JSONArray availableThrusters = (JSONArray) engineMgr.get("thrusters");

    DefaultListModel<String> availableThrustersModel = new DefaultListModel<String>();
    for (int i = 0; i < availableThrusters.size(); i++) {
      JSONObject obj = (JSONObject) availableThrusters.get(i);
      availableThrustersModel.addElement((String) obj.get("name"));
    }
    JList<String> availableThrustersListDisplay = new JList<>(availableThrustersModel);

    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(new TitledBorder("Available Thrusters"));
    panel.add(new JScrollPane(availableThrustersListDisplay), BorderLayout.CENTER);

    return panel;
  }

  protected JPanel createSubscribedEnginesPanel() {
    String[] columns = { "Engine", "Thruster" };
    Object[][] subscribedEngineDisplayData = new Object[enginesList.size()][columns.length];
    for (int i = 0; i < enginesList.size(); i++) {
      subscribedEngineDisplayData[i][0] = enginesList.get(i).getFile();
      subscribedEngineDisplayData[i][1] = thrustersList.get(i).getFile();
    }

    DefaultTableModel subscribedEngineTableModel = new DefaultTableModel(subscribedEngineDisplayData, columns) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    JTable subscribedEngineDisplayTable = new JTable(subscribedEngineTableModel);
    subscribedEngineDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    JButton newPair = new JButton("Add Pair");
    newPair.setActionCommand("Add Item");
    newPair.setToolTipText("Add new item");
    newPair.addActionListener(event -> onAddPairClick());

    JButton deletePair = new JButton("Delete Pair");
    deletePair.setActionCommand("Delete Item");
    deletePair.setToolTipText("Delete the item");
    deletePair.addActionListener(event -> {
      int selectedRow = subscribedEngineDisplayTable.getSelectedRow();
      if (selectedRow != -1) {
        onDeletePairClick(selectedRow);
      } else {
        JOptionPane.showMessageDialog(this, "No row selected", "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    JButton detailPair = new JButton("Detail Pair");
    detailPair.setActionCommand("More Info");
    detailPair.setToolTipText("See detail of the item");
    detailPair.addActionListener(event -> {
      int selectedRow = subscribedEngineDisplayTable.getSelectedRow();
      if (selectedRow != -1) {
        onDetailPairClick(selectedRow);
      } else {
        JOptionPane.showMessageDialog(this, "No row selected", "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    JPanel SEButtons = new JPanel(new GridLayout(3, 1));
    SEButtons.add(newPair);
    SEButtons.add(deletePair);
    SEButtons.add(detailPair);

    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(new TitledBorder("Subscribed Engines"));
    panel.add(new JScrollPane(subscribedEngineDisplayTable));
    panel.add(SEButtons, BorderLayout.SOUTH);

    return panel;
  }

  protected JPanel createTanksPanel() {
    DefaultListModel<String> tanks = new DefaultListModel<String>();
    tanksList.forEach(tank -> {
      String info = tank.getType() + " weights " + tank.getContents().getValue() + " " + tank.getContents().getUnit()
          + " at ["
          + tank.getLocation().getX() + ", " + tank.getLocation().getY() + ", " + tank.getLocation().getZ()
          + "]";
      tanks.addElement(info);
    });
    JList<String> tanksListDisplay = new JList<>(tanks);

    JButton newTank = new JButton("Add Tank");
    newTank.setActionCommand("Add Item");
    newTank.setToolTipText("Add new item");
    newTank.addActionListener(event -> onAddTankClick());

    JButton deleteTank = new JButton("Delete Tank");
    deleteTank.setActionCommand("Delete Item");
    deleteTank.setToolTipText("Delete the item");
    deleteTank.addActionListener(event -> {
      int selectedRow = tanksListDisplay.getSelectedIndex();
      if (selectedRow != -1) {
        onDeleteTankClick(tanksListDisplay.getSelectedIndex());
      } else {
        JOptionPane.showMessageDialog(this, "No row selected", "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    JButton detailTank = new JButton("Detail Tank");
    detailTank.setActionCommand("More Info");
    detailTank.setToolTipText("See detail of the item");
    detailTank.addActionListener(event -> {
      int selectedRow = tanksListDisplay.getSelectedIndex();
      if (selectedRow != -1) {
        onDetailTankClick(tanksListDisplay.getSelectedIndex());
      } else {
        JOptionPane.showMessageDialog(this, "No row selected", "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    JPanel TankButtons = new JPanel(new GridLayout(3, 1));
    TankButtons.add(newTank);
    TankButtons.add(deleteTank);
    TankButtons.add(detailTank);

    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(new TitledBorder("Tanks"));
    panel.add(new JScrollPane(tanksListDisplay), BorderLayout.CENTER);

    panel.add(TankButtons, BorderLayout.SOUTH);

    return panel;
  }

  protected void onDetailTankClick(Integer index) {
    JFrame frame = new JFrame("Tank Setup");
    frame.setSize(400, 350);

    JPanel main = new JPanel();
    main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

    JPanel tank = new JPanel(new FlowLayout());
    tank.setBorder(new TitledBorder("Tank"));
    tank.add(new JLabel("Tank Type: "));
    JTextField tankNameField = new JTextField(tanksList.get(index).getType());
    tank.add(tankNameField);

    JPanel contents = new JPanel(new FlowLayout());
    contents.setBorder(new TitledBorder("Contents"));
    contents.add(new JLabel("Contents: "));
    JTextField contentsField = new JTextField(String.valueOf(tanksList.get(index).getContents().getValue()));
    contents.add(contentsField);

    JComboBox<String> contentsUnitsComboBox = new JComboBox<String>(contentsUnits);
    contentsUnitsComboBox.setSelectedItem(tanksList.get(index).getContents().getUnit());
    contents.add(contentsUnitsComboBox);

    JPanel capacity = new JPanel(new FlowLayout());
    capacity.setBorder(new TitledBorder("Capacity"));
    capacity.add(new JLabel("Capacity: "));
    JTextField capacityField = new JTextField(String.valueOf(tanksList.get(index).getCapacity().getValue()));
    capacity.add(capacityField);

    JComboBox<String> capacityUnitsComboBox = new JComboBox<String>(capacityUnits);
    capacityUnitsComboBox.setSelectedItem(tanksList.get(index).getCapacity().getUnit());
    capacity.add(capacityUnitsComboBox);

    JPanel location = new JPanel(new GridLayout(1, 4));
    location.setBorder(new TitledBorder("Location"));
    JTextField locationXField = new JTextField(String.valueOf(tanksList.get(index).getLocation().getX()));
    JTextField locationYField = new JTextField(String.valueOf(tanksList.get(index).getLocation().getY()));
    JTextField locationZField = new JTextField(String.valueOf(tanksList.get(index).getLocation().getZ()));
    addLabelAndTextField(location, "x =", locationXField);
    addLabelAndTextField(location, "y =", locationYField);
    addLabelAndTextField(location, "z =", locationZField);

    JComboBox<String> locationUnitsComboBox = new JComboBox<String>(locationUnits);
    locationUnitsComboBox.setSelectedItem(tanksList.get(index).getLocation().getUnit());
    location.add(locationUnitsComboBox);

    main.add(tank);
    main.add(contents);
    main.add(capacity);
    main.add(location);

    JButton ok = new JButton("OK");
    ok.setMaximumSize(new Dimension(100, 50));
    ok.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        Tank tankObject = tanksList.get(index);

        tankObject.setType(tankNameField.getText());
        tankObject.getContents().setValue(Double.parseDouble(contentsField.getText()));
        tankObject.getContents().setUnit(contentsUnitsComboBox.getSelectedItem().toString());
        tankObject.getCapacity().setValue(Double.parseDouble(capacityField.getText()));
        tankObject.getCapacity().setUnit(capacityUnitsComboBox.getSelectedItem().toString());
        tankObject.getLocation().setX(Double.parseDouble(locationXField.getText()));
        tankObject.getLocation().setY(Double.parseDouble(locationYField.getText()));
        tankObject.getLocation().setZ(Double.parseDouble(locationZField.getText()));
        tankObject.getLocation().setUnit(locationUnitsComboBox.getSelectedItem().toString());

        cfg.getPropulsion().getEngineOrTank().set(tanksIndexMap.get(index), tankObject);
        mainFrame.refreshCfg(cfg);

        frame.dispose();
      }
    });

    JButton cancel = new JButton("CANCEL");
    cancel.setMaximumSize(new Dimension(100, 50));
    cancel.addActionListener(event -> frame.dispose());

    JPanel actionButtonsPanel = new JPanel(new FlowLayout());
    actionButtonsPanel.add(ok);
    actionButtonsPanel.add(cancel);

    main.add(actionButtonsPanel);

    frame.add(main);
    frame.setLocationRelativeTo(this);
    frame.setVisible(true);
  }

  protected void onDeleteTankClick(Integer index) {
    Object tankToDelete = cfg.getPropulsion().getEngineOrTank().get(tanksIndexMap.get(index));
    if (tankToDelete != null) {
      cfg.getPropulsion().getEngineOrTank().remove(tankToDelete);
      tanksList.remove(tanksList.get(index));
    }
    mainFrame.refreshCfg(cfg);
  }

  protected void onAddTankClick() {
    JFrame frame = new JFrame("Tank Setup");
    frame.setSize(400, 350);

    JPanel main = new JPanel();
    main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

    JPanel tank = new JPanel(new FlowLayout());
    tank.setBorder(new TitledBorder("Tank"));
    tank.add(new JLabel("Tank Type: "));

    String[] tankType = { "FUEL", "OXIDIZER" };
    JComboBox<String> tankTypeComboBox = new JComboBox<String>(tankType);
    tank.add(tankTypeComboBox);

    JPanel contents = new JPanel(new FlowLayout());
    contents.setBorder(new TitledBorder("Contents"));
    contents.add(new JLabel("Contents: "));
    JTextField contentsField = new JTextField("0");
    contentsField.setPreferredSize(new Dimension(100, 20));
    contents.add(contentsField);

    JComboBox<String> contentsUnitsComboBox = new JComboBox<String>(contentsUnits);
    contents.add(contentsUnitsComboBox);

    JPanel capacity = new JPanel(new FlowLayout());
    capacity.setBorder(new TitledBorder("Capacity"));
    capacity.add(new JLabel("Capacity: "));
    JTextField capacityField = new JTextField("0");
    capacityField.setPreferredSize(new Dimension(100, 20));
    capacity.add(capacityField);

    JComboBox<String> capacityUnitsComboBox = new JComboBox<String>(capacityUnits);
    capacity.add(capacityUnitsComboBox);

    JPanel location = new JPanel(new GridLayout(1, 4));
    location.setBorder(new TitledBorder("Location"));
    JTextField locationXField = new JTextField("0");
    JTextField locationYField = new JTextField("0");
    JTextField locationZField = new JTextField("0");
    addLabelAndTextField(location, "x =", locationXField);
    addLabelAndTextField(location, "y =", locationYField);
    addLabelAndTextField(location, "z =", locationZField);

    JComboBox<String> locationUnitsComboBox = new JComboBox<String>(locationUnits);
    location.add(locationUnitsComboBox);

    main.add(tank);
    main.add(contents);
    main.add(capacity);
    main.add(location);

    JButton ok = new JButton("OK");
    ok.setMaximumSize(new Dimension(100, 50));
    ok.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        Tank tankObject = new Tank();
        Contents contentsObject = new Contents();
        Capacity capacityObject = new Capacity();
        Location locationObject = new Location();

        tankObject.setType(tankTypeComboBox.getSelectedItem().toString());

        tankObject.setContents(contentsObject);
        tankObject.getContents().setValue(Double.parseDouble(contentsField.getText()));
        tankObject.getContents().setUnit(contentsUnitsComboBox.getSelectedItem().toString());

        tankObject.setCapacity(capacityObject);
        tankObject.getCapacity().setValue(Double.parseDouble(capacityField.getText()));
        tankObject.getCapacity().setUnit(capacityUnitsComboBox.getSelectedItem().toString());

        tankObject.setLocation(locationObject);
        tankObject.getLocation().setX(Double.parseDouble(locationXField.getText()));
        tankObject.getLocation().setY(Double.parseDouble(locationYField.getText()));
        tankObject.getLocation().setZ(Double.parseDouble(locationZField.getText()));
        tankObject.getLocation().setUnit(locationUnitsComboBox.getSelectedItem().toString());

        cfg.getPropulsion().getEngineOrTank().add(tankObject);
        mainFrame.refreshCfg(cfg);

        frame.dispose();
      }
    });

    JButton cancel = new JButton("CANCEL");
    cancel.setMaximumSize(new Dimension(100, 50));
    cancel.addActionListener(event -> frame.dispose());

    JPanel actionButtonsPanel = new JPanel(new FlowLayout());
    actionButtonsPanel.add(ok);
    actionButtonsPanel.add(cancel);

    main.add(actionButtonsPanel);

    frame.add(main);
    frame.setLocationRelativeTo(this);
    frame.setVisible(true);
  }

  protected void onDetailPairClick(Integer index) {
    JFrame frame = new JFrame("Engine/Thruster Setup");
    frame.setSize(500, 400);

    JPanel main = new JPanel();
    main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

    // Engine sub-panel.
    JPanel engine = new JPanel(new FlowLayout());
    engine.setBorder(new TitledBorder("Engine"));
    engine.add(new JLabel("Engine Name: "));
    JTextField engineNameField = new JTextField(enginesList.get(index).getFile());
    engine.add(engineNameField);

    // Feed sub-panel.
    JPanel feed = new JPanel(new FlowLayout());
    feed.setBorder(new TitledBorder("Feed"));
    List<BigInteger> feedList = enginesList.get(index).getFeed();
    for (int i = 0; i < feedList.size(); i++) {
      feed.add(new JLabel(feedList.get(i).toString()));
    }

    // Thruster sub-panel.
    Thruster thrusterObject = enginesList.get(index).getThruster();
    Location locationObject = thrusterObject.getLocation();
    Orient orientObject = thrusterObject.getOrient();

    JPanel thruster = new JPanel(new GridLayout(3, 1));
    thruster.setBorder(new TitledBorder("Thruster"));

    JPanel thrusterName = new JPanel(new FlowLayout());
    thrusterName.add(new JLabel("Thruster Name: "));
    JTextField thrusterNameField = new JTextField(thrusterObject.getFile());
    thrusterName.add(thrusterNameField);

    JPanel location = new JPanel(new GridLayout(1, 4));
    location.setBorder(new TitledBorder("Location"));
    JTextField locationXField = new JTextField(String.valueOf(locationObject.getX()));
    JTextField locationYField = new JTextField(String.valueOf(locationObject.getY()));
    JTextField locationZField = new JTextField(String.valueOf(locationObject.getZ()));
    addLabelAndTextField(location, "x =", locationXField);
    addLabelAndTextField(location, "y =", locationYField);
    addLabelAndTextField(location, "z =", locationZField);

    JComboBox<String> locationUnitsComboBox = new JComboBox<String>(locationUnits);
    locationUnitsComboBox.setSelectedItem(locationObject.getUnit());
    location.add(locationUnitsComboBox);

    JPanel orient = new JPanel(new GridLayout(1, 4));
    orient.setBorder(new TitledBorder("Orient"));
    JTextField orientRollField = new JTextField(String.valueOf(orientObject.getRoll()));
    JTextField orientPitchField = new JTextField(String.valueOf(orientObject.getPitch()));
    JTextField orientYawField = new JTextField(String.valueOf(orientObject.getYaw()));
    addLabelAndTextField(orient, "roll =", orientRollField);
    addLabelAndTextField(orient, "pitch =", orientPitchField);
    addLabelAndTextField(orient, "yaw =", orientYawField);

    JComboBox<String> orientUnitsComboBox = new JComboBox<String>(orientUnits);
    orientUnitsComboBox.setSelectedItem(orientObject.getUnit());
    orient.add(orientUnitsComboBox);

    thruster.add(thrusterName);
    thruster.add(location);
    thruster.add(orient);

    main.add(engine);
    main.add(feed);
    main.add(thruster);

    JButton ok = new JButton("OK");
    ok.setMaximumSize(new Dimension(100, 50));
    ok.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        Engine engineObject = enginesList.get(index);

        engineObject.setFile(engineNameField.getText());
        engineObject.getThruster().setFile(thrusterNameField.getText());

        engineObject.getThruster().getLocation().setX(Double.parseDouble(locationXField.getText()));
        engineObject.getThruster().getLocation().setY(Double.parseDouble(locationYField.getText()));
        engineObject.getThruster().getLocation().setZ(Double.parseDouble(locationZField.getText()));
        engineObject.getThruster().getLocation().setUnit(locationUnitsComboBox.getSelectedItem().toString());

        engineObject.getThruster().getOrient().setRoll(Double.parseDouble(orientRollField.getText()));
        engineObject.getThruster().getOrient().setPitch(Double.parseDouble(orientPitchField.getText()));
        engineObject.getThruster().getOrient().setYaw(Double.parseDouble(orientYawField.getText()));
        engineObject.getThruster().getOrient().setUnit(orientUnitsComboBox.getSelectedItem().toString());

        cfg.getPropulsion().getEngineOrTank().set(index, engineObject);
        mainFrame.refreshCfg(cfg);

        frame.dispose();
      }
    });

    JButton cancel = new JButton("CANCEL");
    cancel.setMaximumSize(new Dimension(100, 50));
    cancel.addActionListener(event -> frame.dispose());

    JPanel actionButtonsPanel = new JPanel(new FlowLayout());
    actionButtonsPanel.add(ok);
    actionButtonsPanel.add(cancel);

    main.add(actionButtonsPanel);

    frame.add(main);
    frame.setLocationRelativeTo(this);
    frame.setVisible(true);
  }

  protected void onDeletePairClick(Integer index) {
    Object pairToDelete = cfg.getPropulsion().getEngineOrTank().get(enginesIndexMap.get(index));
    if (pairToDelete != null) {
      cfg.getPropulsion().getEngineOrTank().remove(pairToDelete);
      enginesList.remove(enginesList.get(index));
    }
    mainFrame.refreshCfg(cfg);
  }

  protected void onAddPairClick() {
    JFrame frame = new JFrame("Engine/Thruster Setup");
    frame.setSize(500, 400);

    JPanel main = new JPanel();
    main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

    // Engine sub-panel.
    JPanel engine = new JPanel(new FlowLayout());
    engine.setBorder(new TitledBorder("Engine"));
    engine.add(new JLabel("Engine Name: "));
    JTextField engineNameField = new JTextField();
    engineNameField.setPreferredSize(new Dimension(100, 20));
    engine.add(engineNameField);

    // Thruster sub-panel.
    JPanel thruster = new JPanel(new GridLayout(3, 1));
    thruster.setBorder(new TitledBorder("Thruster"));

    JPanel thrusterName = new JPanel(new FlowLayout());
    thrusterName.add(new JLabel("Thruster Name: "));
    JTextField thrusterNameField = new JTextField();
    thrusterNameField.setPreferredSize(new Dimension(100, 20));
    thrusterName.add(thrusterNameField);

    JPanel location = new JPanel(new GridLayout(1, 4));
    location.setBorder(new TitledBorder("Location"));
    JTextField locationXField = new JTextField("0");
    JTextField locationYField = new JTextField("0");
    JTextField locationZField = new JTextField("0");
    addLabelAndTextField(location, "x =", locationXField);
    addLabelAndTextField(location, "y =", locationYField);
    addLabelAndTextField(location, "z =", locationZField);

    JComboBox<String> locationUnitsComboBox = new JComboBox<String>(locationUnits);
    location.add(locationUnitsComboBox);

    JPanel orient = new JPanel(new GridLayout(1, 4));
    orient.setBorder(new TitledBorder("Orient"));
    JTextField orientRollField = new JTextField("0");
    JTextField orientPitchField = new JTextField("0");
    JTextField orientYawField = new JTextField("0");
    addLabelAndTextField(orient, "roll =", orientRollField);
    addLabelAndTextField(orient, "pitch =", orientPitchField);
    addLabelAndTextField(orient, "yaw =", orientYawField);

    JComboBox<String> orientUnitsComboBox = new JComboBox<String>(orientUnits);
    orient.add(orientUnitsComboBox);

    thruster.add(thrusterName);
    thruster.add(location);
    thruster.add(orient);

    main.add(engine);
    main.add(thruster);

    JButton ok = new JButton("OK");
    ok.setMaximumSize(new Dimension(100, 50));
    ok.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        Engine engineObject = new Engine();
        Thruster thrusterObject = new Thruster();
        Location locationObject = new Location();
        Orient orientObject = new Orient();

        locationObject.setX(Double.parseDouble(locationXField.getText()));
        locationObject.setY(Double.parseDouble(locationYField.getText()));
        locationObject.setZ(Double.parseDouble(locationZField.getText()));
        locationObject.setUnit(locationUnitsComboBox.getSelectedItem().toString());

        orientObject.setRoll(Double.parseDouble(orientRollField.getText()));
        orientObject.setPitch(Double.parseDouble(orientPitchField.getText()));
        orientObject.setYaw(Double.parseDouble(orientYawField.getText()));
        orientObject.setUnit(orientUnitsComboBox.getSelectedItem().toString());

        thrusterObject.setFile(thrusterNameField.getText());
        thrusterObject.setLocation(locationObject);
        thrusterObject.setOrient(orientObject);

        engineObject.setFile(engineNameField.getText());
        engineObject.setThruster(thrusterObject);

        cfg.getPropulsion().getEngineOrTank().add(engineObject);
        mainFrame.refreshCfg(cfg);

        frame.dispose();
      }
    });

    JButton cancel = new JButton("CANCEL");
    cancel.setMaximumSize(new Dimension(100, 50));
    cancel.addActionListener(event -> frame.dispose());

    JPanel actionButtonsPanel = new JPanel(new FlowLayout());
    actionButtonsPanel.add(ok);
    actionButtonsPanel.add(cancel);

    main.add(actionButtonsPanel);

    frame.add(main);
    frame.setLocationRelativeTo(this);
    frame.setVisible(true);
  }

  /**
   * Add a label and a text field to a panel.
   * 
   * @param panel the {@link JPanel} to add the label and text field to.
   * @param label the label string to add.
   * @param field the {@link JTextField} to add.
   */
  private void addLabelAndTextField(JPanel panel, String label, JTextField field) {
    JPanel subPanel = new JPanel(new FlowLayout()); // Use FlowLayout for label and text field in the sub-panel

    JLabel labelComponent = new JLabel(label);
    field.setPreferredSize(new Dimension(50, 20)); // Adjust the size as needed

    subPanel.add(labelComponent);
    subPanel.add(field);

    panel.add(subPanel);
  }
}