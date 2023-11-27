package uta.cse.cse3310.JSBSimEdit.ui.windows;

import generated.*;
import generated.System;
import uta.cse.cse3310.JSBSimEdit.ui.components.Aerodynamics;
import uta.cse.cse3310.JSBSimEdit.ui.components.Autopilot;
import uta.cse.cse3310.JSBSimEdit.ui.components.FlightControl;
import uta.cse.cse3310.JSBSimEdit.ui.components.Input;
import uta.cse.cse3310.JSBSimEdit.ui.components.MassBalance;
import uta.cse.cse3310.JSBSimEdit.ui.components.Metrics;
import uta.cse.cse3310.JSBSimEdit.ui.components.Output;
import uta.cse.cse3310.JSBSimEdit.ui.components.Propulsion;
import uta.cse.cse3310.JSBSimEdit.ui.components.*;
import uta.cse.cse3310.JSBSimEdit.ui.components.system.SystemProperties;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CommanderWindow extends JFrame {
  private FdmConfig cfg;
  private MainWindow mainWindow;
  private JTabbedPane tabbedPane;

  public CommanderWindow(String title, FdmConfig cfg, MainWindow mainWindow) {
    super(title);
    this.cfg = cfg;
    this.mainWindow = mainWindow;

    this.setLocationByPlatform(true);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setSize(1290, 920);

    JToolBar toolbar = createToolbar();
    tabbedPane = createTabbedPane(this);

    Container contentPane = this.getContentPane();
    contentPane.add(toolbar, BorderLayout.NORTH);
    contentPane.add(tabbedPane, BorderLayout.CENTER);

    // JLabel display = new JLabel();
    // display.setFont(new Font("SansSerif", Font.BOLD, 17));
    // add(display, BorderLayout.CENTER);

    setVisible(true);
  }

  protected void onNewClick() {
  }

  public void onSaveClick() {
    mainWindow.parseOutput();
    }

  protected void onOpenClick() {
  }

  protected void onQuitClick() {

    int button = JOptionPane.showConfirmDialog( // Show the save dialog
            this,
            "Do you want to save new files?",
            "Some unchanged files",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE);
    if (button == JOptionPane.OK_OPTION) { // If OK clicks, save new changes
      onSaveClick();
    }
    dispose();
  }

  private List<JButton> createButtons() {
    JButton newFileButton = new JButton(
        new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/new-document.png"));
    newFileButton.setActionCommand("New File");
    newFileButton.setToolTipText("Create new file");
    newFileButton.addActionListener(event -> onNewClick());

    JButton openFileButton = new JButton(
        new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/folder.png"));
    openFileButton.setActionCommand("Open File");
    openFileButton.setToolTipText("Open an existing file");
    openFileButton.addActionListener(event -> onOpenClick());

    JButton saveFileButton = new JButton(
        new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/diskette.png"));
    saveFileButton.setActionCommand("Save File");
    saveFileButton.setToolTipText("Save an existing file");
    saveFileButton.addActionListener(event -> onSaveClick());

    JButton quitButton = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/reject.png"));
    quitButton.setActionCommand("Quit");
    quitButton.setToolTipText("Exit Window");
    quitButton.addActionListener(event -> onQuitClick());

    return List.of(
        newFileButton,
        openFileButton,
        saveFileButton,
        quitButton);
  }

  private JToolBar createToolbar() {
    JToolBar toolbar = new JToolBar("JSBSim Edit Controls");
    List<JButton> buttons = createButtons();
    buttons.forEach(toolbar::add);
    return toolbar;
  }

  private JTabbedPane createTabbedPane(CommanderWindow mainFrame) {
    GeneralInformation generalInfo = new GeneralInformation();
    Metrics metrics = initializeMetrics(cfg);
    MassBalance massBalance = initializeMassBalance(cfg);
    GroundReaction groundReact = initializeGroundReaction();
    ExternalReaction externalReaction = initializeExternalReaction();
    BuoyantForce buoyantForce = new BuoyantForce();
    Propulsion propulsion = new Propulsion(cfg, mainFrame);
    FlightControl flightControl = initializeFlightControl();
    Aerodynamics aeroDynamics = initializeAerodynamics();
    SystemProperties systemProperties = initializeSystem();
    Autopilot autoPilot = initializeAutopilot();
    Output output = new Output();
    Input input = new Input();

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("general_info", generalInfo);
    tabbedPane.addTab("metrics", metrics);
    tabbedPane.addTab("mass_balance", massBalance);
    tabbedPane.addTab("ground_reactions", groundReact);
    tabbedPane.addTab("external_reactions", externalReaction);
    tabbedPane.addTab("propulsion", propulsion);
    tabbedPane.addTab("buoyant_forces", buoyantForce);
    tabbedPane.addTab("system", systemProperties);
    tabbedPane.addTab("flight_control", flightControl);
    tabbedPane.addTab("aerodynamics", aeroDynamics);
    tabbedPane.addTab("autopilot", autoPilot);
    tabbedPane.addTab("input", input);
    tabbedPane.addTab("output", output);
    return tabbedPane;
  }

  private ExternalReaction initializeExternalReaction() {
    ExternalReactions externalReactions = cfg.getExternalReactions();

    List<String> properties = externalReactions.getProperty();
    List<Force> forces = externalReactions.getForce();
    String fileName = externalReactions.getFile();

    return new ExternalReaction(properties, forces, fileName);
  }

  private SystemProperties initializeSystem() {
    List<System> systemsList = cfg.getSystem();

    return new SystemProperties(systemsList);
  }

  private Metrics initializeMetrics(FdmConfig cfg) {
    Wingarea wingarea = cfg.getMetrics().getWingarea();
    Wingspan wingspan = cfg.getMetrics().getWingspan();
    WingIncidence wingincidence = cfg.getMetrics().getWingIncidence();
    Chord chord = cfg.getMetrics().getChord();
    Htailarea htailarea = cfg.getMetrics().getHtailarea();
    Htailarm htailarm = cfg.getMetrics().getHtailarm();
    Vtailarea vtailarea = cfg.getMetrics().getVtailarea();
    Vtailarm vtailarm = cfg.getMetrics().getVtailarm();
    List<Location> location = cfg.getMetrics().getLocation();

    return new Metrics(wingarea, wingspan, wingincidence, chord, htailarea, htailarm, vtailarea, vtailarm, location);
  }

  private GroundReaction initializeGroundReaction() {
    GroundReactions groundReactions = cfg.getGroundReactions();

    return new GroundReaction(groundReactions);
  }

  private MassBalance initializeMassBalance(FdmConfig cfg) {
    Ixx ixx = cfg.getMassBalance().getIxx();
    Iyy iyy = cfg.getMassBalance().getIyy();
    Izz izz = cfg.getMassBalance().getIzz();
    Ixy ixy = cfg.getMassBalance().getIxy();
    Ixz ixz = cfg.getMassBalance().getIxz();
    Iyz iyz = cfg.getMassBalance().getIyz();
    Emptywt emptywt = cfg.getMassBalance().getEmptywt();
    Location location = cfg.getMassBalance().getLocation();
    List<Pointmass> pointmasses = cfg.getMassBalance().getPointmass();

    return new MassBalance(ixx, iyy, izz, ixy, ixz, iyz, emptywt, location, pointmasses);
  }

  private FlightControl initializeFlightControl() {
    generated.FlightControl flightControl = cfg.getFlightControl();

    List<String> property = flightControl.getProperty();
    List<Sensor> sensor = flightControl.getSensor();
    List<Channel> channel = flightControl.getChannel();
    String name = flightControl.getName();
    String file = flightControl.getFile();

    return new FlightControl(property, sensor, channel, name, file);
  }

  private Aerodynamics initializeAerodynamics() {
    Alphalimits alphalimits = cfg.getAerodynamics().getAlphalimits();
    HysteresisLimits hysteresisLimits = cfg.getAerodynamics().getHysteresisLimits();
    List<Function> aeroFunctions = cfg.getAerodynamics().getFunction();
    List<Axis> aeroAxis = cfg.getAerodynamics().getAxis();

    return new Aerodynamics(alphalimits, hysteresisLimits, aeroFunctions, aeroAxis);
  }

  private Autopilot initializeAutopilot() {
    generated.Autopilot autoPilot = cfg.getAutopilot();

    List<String> property = autoPilot.getProperty();
    List<Sensor> sensor = autoPilot.getSensor();
    List<Channel> channel = autoPilot.getChannel();
    String name = autoPilot.getName();
    String file = autoPilot.getFile();

    return new Autopilot(property, sensor, channel, name, file);
  }

  public void refreshCfg(FdmConfig cfg) {
    // Store the index of the currently selected tab
    int selectedTabIndex = tabbedPane.getSelectedIndex();

    this.cfg = cfg;

    // Recreate the JTabbedPane with the updated Config
    JTabbedPane newTabbedPane = createTabbedPane(this);

    // Replace the existing tabbedPane
    Container contentPane = getContentPane();
    contentPane.remove(tabbedPane);
    contentPane.add(newTabbedPane, BorderLayout.CENTER);

    tabbedPane = newTabbedPane; // Update the reference

    // Set the selected tab to the stored index
    tabbedPane.setSelectedIndex(selectedTabIndex);

    // Repaint the frame
    revalidate();
    repaint();
  }


}
