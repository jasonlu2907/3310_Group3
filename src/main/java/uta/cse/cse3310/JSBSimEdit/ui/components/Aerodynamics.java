package uta.cse.cse3310.JSBSimEdit.ui.components;

import java.util.List;

import generated.Alphalimits;
import generated.HysteresisLimits;
import generated.Function;
import generated.Axis;
import generated.Table;
import generated.Product;
import generated.IndependentVar;
import generated.TableData;
import jakarta.xml.bind.JAXBElement;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Aerodynamics extends JPanel {
    private static JTree aeroTree;
    private static JPanel mainPanel;

    public Aerodynamics(Alphalimits alphalimits, HysteresisLimits hysteresisLimits, List<Function> aeroFunctions, List<Axis> aeroAxis) {
        setLayout(new BorderLayout());

        DefaultMutableTreeNode aerodynamics = new DefaultMutableTreeNode("Aerodynamics");

        createTree(aerodynamics, aeroFunctions, aeroAxis);

        aeroTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TreePath path = aeroTree.getPathForLocation(e.getX(), e.getY());
                    if (path != null) {
                        Object selectedNode = path.getLastPathComponent();
                        if (aeroTree.getModel().isLeaf(selectedNode)) {
                            openNewLeafWindow(selectedNode.toString());
                        } else if (!(aeroTree.getModel().isLeaf(selectedNode))) {
                            openNewWindow(selectedNode.toString(), aeroFunctions, aeroAxis, aerodynamics);
                        }
                    }
                }
            }
        });

        add(mainPanel, BorderLayout.CENTER);
    }

    private static void createFuncTreeChildren(DefaultMutableTreeNode parent, List<Function> aeroFunctions) {
        for (Function i : aeroFunctions) {
            String funcName = i.getName();
            String funcDescription = i.getDescription();
            DefaultMutableTreeNode func = new DefaultMutableTreeNode(funcName + "(" + funcDescription + ")");

            DefaultMutableTreeNode funcProd = new DefaultMutableTreeNode("Product");
            func.add(funcProd);

            if (i.getTable() != null) {
                Table tabl = i.getTable();
                createTableTreeChildren(funcProd, tabl);
            }

            if (i.getProduct() != null) {
                Product prod = i.getProduct();
                createProdTreeChildren(funcProd, prod);
            }
            parent.add(func);
        }
    }

    private static void createAxisTreeChildren(DefaultMutableTreeNode parent, List<Axis> aeroAxis) {
        for (Axis i : aeroAxis) {
            String axisName = i.getName();
            DefaultMutableTreeNode axis = new DefaultMutableTreeNode(axisName);
            if (i.getFunction() != null) {
                List<Function> axisFuncs = i.getFunction();
                createFuncTreeChildren(axis, axisFuncs);
            }
            parent.add(axis);
        }
    }

    private static void createProdTreeChildren(DefaultMutableTreeNode parent, Product prod) {
        List<Object> prods = prod.getTableOrProductOrDifference();
        for (Object item : prods) {
            if (item instanceof JAXBElement && ((JAXBElement<?>) item).getValue() instanceof String && "property".equals(((JAXBElement<String>) item).getName().getLocalPart())) {
                String property = (String) ((JAXBElement<?>) item).getValue();
                DefaultMutableTreeNode prop = new DefaultMutableTreeNode(property);
                parent.add(prop);
            } else if (item instanceof JAXBElement && ((JAXBElement<?>) item).getValue() instanceof Double && "value".equals(((JAXBElement<Double>) item).getName().getLocalPart())) {
                Double value = (Double) ((JAXBElement<?>) item).getValue();
                DefaultMutableTreeNode val = new DefaultMutableTreeNode(value);
                parent.add(val);
            } else if (item instanceof Table) {
                Table table = (Table) item;
                createTableTreeChildren(parent, table);
            }
        }
    }

    private static void createTableTreeChildren(DefaultMutableTreeNode parent, Table table) {
        StringBuilder str = new StringBuilder();
        if (table.getName() != null) {
            str.append(table.getName());
        } else {
            str.append("T");
        }

        if (table.getIndependentVar() != null) {
            List<IndependentVar> vars = table.getIndependentVar();
            String[] varNames = new String[vars.size()];
            int i = 0;
            for (IndependentVar varName : vars) {
                if (varName.getContent() != null) {
                    varNames[i] = varName.getContent().substring(varName.getContent().indexOf("/")+1);
                    i++;
                }
            }

            str.append("(");
            for (i = 0; i < varNames.length; i++) {
                if (i == 0) {
                    str.append(varNames[i]);
                } else {
                    str.append(", " + varNames[i]);
                }
            }
            str.append(")");
        }

        DefaultMutableTreeNode tableChild = new DefaultMutableTreeNode(str);

        List<TableData> data = table.getTableData();

        parent.add(tableChild);
    }

    private static void openNewWindow(String nodeName, List<Function> aeroFunctions, List<Axis> aeroAxis, DefaultMutableTreeNode aerodynamics) {
        if (nodeName == "Product") {
        } else if (nodeName.indexOf("/") >= 0) {
        } else if (nodeName == "aerodynamics") {
        } else {
            AxisNodeWindow(nodeName, aeroFunctions, aeroAxis, aerodynamics);
        }
    }

    private static void openNewLeafWindow(String nodeName) {
        JFrame newFrame = new JFrame("Node Details");
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        newFrame.setSize(200, 100);
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);
    }

    private static void AxisNodeWindow(String nodeName, List<Function> aeroFunctions, List<Axis> aeroAxis, DefaultMutableTreeNode aerodynamics) {
        String[] axisNames = {"DRAG", "SIDE", "LIFT", "ROLL", "PITCH", "YAW", "FORWARD", "RIGHT", "DOWN", "Undef"};

        JFrame frame = new JFrame("Axis Properties");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout());

        JLabel nameLabel = new JLabel("Name:");
        JComboBox<String> nameComboBox = new JComboBox<>(axisNames);
        nameComboBox.setSelectedItem(nodeName);
        panel.add(nameLabel);
        panel.add(nameComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton editButton = new JButton("Edit");
        editButton.setActionCommand("Edit Details");
        editButton.addActionListener(event -> {
            for (Axis a : aeroAxis) {
                if (nodeName == a.getName()) {
                    a.setName(nameComboBox.getSelectedItem().toString());
                }
            }
            createTree(aerodynamics, aeroFunctions, aeroAxis);
            frame.dispose();
        });

        JButton closeButton = new JButton("Close");
        closeButton.setActionCommand("Close Details");
        closeButton.addActionListener(event -> {
            frame.dispose();
        });

        buttonPanel.add(editButton);
        buttonPanel.add(closeButton);

        panel.add(buttonPanel);

        frame.add(panel);
        frame.setSize(250, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createTree(DefaultMutableTreeNode aerodynamics, List<Function> aeroFunctions, List<Axis> aeroAxis) {
        aerodynamics = new DefaultMutableTreeNode("aerodynamics");
        mainPanel = new JPanel(new BorderLayout());
        aeroTree = new JTree(aerodynamics);
        createFuncTreeChildren(aerodynamics, aeroFunctions);
        createAxisTreeChildren(aerodynamics, aeroAxis);
        mainPanel.add(aeroTree);
    }
}

