package uta.cse.cse3310.JSBSimEdit;

import static org.junit.Assert.assertEquals;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.junit.Test;

import uta.cse.cse3310.JSBSimEdit.ui.components.PanelBuilder;

/**
 * Unit test for simple App.
 */
public class RetrieveFieldTest 
{
    private PanelBuilder pb = new PanelBuilder();

    @Test
    public void testGetFieldValue() {
        // Create a JPanel with nested components for testing
        JPanel mainPanel = new JPanel();
        JPanel nestedPanel = new JPanel();
        JTextField textField = new JTextField("10.5");
        nestedPanel.add(textField);
        mainPanel.add(nestedPanel);

        double expectedValue = 10.5; // Expected value from the JTextField

        // Test the getFieldValue method
        double actualValue = pb.getFieldValue(mainPanel);

        // Assert that the actual value retrieved matches the expected value
        assertEquals(expectedValue, actualValue, 0.01); // Using a delta for double comparison
    }

    @Test
    public void testGetFieldValueWithInvalidInput() {
        // Create a JPanel with a JTextField having invalid input
        JPanel mainPanel = new JPanel();
        JTextField invalidTextField = new JTextField("abc");
        mainPanel.add(invalidTextField);

        double expectedValue = 0; // Expected value when parsing fails

        // Test the getFieldValue method with invalid input
        double actualValue = pb.getFieldValue(mainPanel);

        // Assert that the actual value retrieved matches the expected value
        assertEquals(expectedValue, actualValue, 0.01); // Using a delta for double comparison
    }

    
}

