package uta.cse.cse3310.JSBSimEdit;

import static org.junit.Assert.assertEquals;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.junit.Test;

import uta.cse.cse3310.JSBSimEdit.ui.components.PanelBuilder;

/**
 * Unit test for simple App.
 */
public class RetrieveUnitTest 
{
    private PanelBuilder pb = new PanelBuilder();

    @Test
    public void testGetFieldValue() {
        String[] testStr = {"a","b","c"};
        // Create a JPanel with nested components for testing
        JPanel mainPanel = new JPanel();
        JPanel nestedPanel = new JPanel();
        JComboBox<String> jComboBox = new JComboBox<>(testStr);
        nestedPanel.add(jComboBox);
        mainPanel.add(nestedPanel);

        String expectedValue = "a"; // Expected value from the JTextField

        // Test the getFieldValue method
        Object actualValue = pb.getUnitValue(mainPanel);

        // Assert that the actual value retrieved matches the expected value
        assertEquals(expectedValue, actualValue); // Using a delta for double comparison
    }
    
}

