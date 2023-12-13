package uta.cse.cse3310.JSBSimEdit.ui.components;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import jakarta.xml.bind.JAXBElement;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import java.io.File;
import java.util.List;

import generated.Fileheader;
import generated.Fileheader.License;
import generated.Reference;

public class GeneralInformation extends JPanel{
    public GeneralInformation(Fileheader header, File file){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel firstRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel fileName = new JLabel("File Name:");
        JTextField fileNameField = new JTextField(file.getName(),10);
        firstRowPanel.add(fileName);
        firstRowPanel.add(fileNameField);

        List<JAXBElement<String>> users = header.getAuthorOrEmailOrOrganization();
        for (int i=0; i<users.size(); i++){
            if (users.get(i) instanceof JAXBElement && users.get(i).getValue() instanceof String) {
                JLabel label = new JLabel(users.get(i).getName().toString() + ":");
                JTextField field = new JTextField(users.get(i).getValue().toString(),10);
                firstRowPanel.add(label);
                firstRowPanel.add(field);
            }
        }

        JLabel fdate = new JLabel("File Date:");
        JTextField fdateField = new JTextField(header.getFilecreationdate().toString(),10);
        firstRowPanel.add(fdate);
        firstRowPanel.add(fdateField);
        
        add(firstRowPanel);

        JPanel secondRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        secondRowPanel.setBorder(new TitledBorder("License"));
        JLabel lName = new JLabel("License Name:");
        License license = header.getLicense();
        JTextField lNameField = new JTextField(header.getLicense().getLicenseName(),10);
        JLabel lUrl = new JLabel("License URL:");
        JTextField lUrlField = new JTextField(header.getLicense().getLicenseURL(),10);
        secondRowPanel.add(lName);
        secondRowPanel.add(lNameField);
        secondRowPanel.add(lUrl);
        secondRowPanel.add(lUrlField);
        add(secondRowPanel);

        JPanel thirdRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel version = new JLabel("Version:");
        JTextField versionField = new JTextField(header.getVersion(),10);
        JLabel copyright = new JLabel("Copyright:");
        JTextField copyrightField = new JTextField(header.getCopyright(),10);
        thirdRowPanel.add(version);
        thirdRowPanel.add(versionField);
        thirdRowPanel.add(copyright);
        thirdRowPanel.add(copyrightField);
        add(thirdRowPanel);

        JPanel fourthRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel fifthRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fifthRowPanel.setBorder(new TitledBorder("Reference"));
        List<Object> details = header.getNoteOrLimitationOrReference();
        for (Object item : details) {
            if (item instanceof JAXBElement) {
                String labelText = ((JAXBElement<?>) item).getName().toString();
                JLabel label = new JLabel(labelText + ":");
                String text = ((JAXBElement<?>) item).getValue().toString();
                JTextArea textArea = new JTextArea(text,10,100);
                textArea.setEditable(true); 
                JScrollPane scrollArea = new JScrollPane(textArea);
                scrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                fourthRowPanel.add(label);
                fourthRowPanel.add(scrollArea);
            } else if (item instanceof Reference) { 
                JPanel reference = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel labelAuthor = new JLabel("Author:");
                JTextField fieldAuthor = new JTextField(((Reference) item).getAuthor(),10);
                reference.add(labelAuthor);
                reference.add(fieldAuthor);
                JLabel labelDate = new JLabel("Date:");
                JTextField fieldDate = new JTextField(((Reference) item).getDate(),10);
                reference.add(labelDate);
                reference.add(fieldDate);
                JLabel labelRefID = new JLabel("Ref ID:");
                JTextField fieldRefID = new JTextField(((Reference) item).getRefID(),10);
                reference.add(labelRefID);
                reference.add(fieldRefID);
                JLabel labelTitle = new JLabel("Title:");
                JTextField fieldTitle = new JTextField(((Reference) item).getTitle(),10);
                reference.add(labelTitle);
                reference.add(fieldTitle);
                fifthRowPanel.add(reference);
            }
        }
        add(fourthRowPanel);
        add(fifthRowPanel);
    }
}
