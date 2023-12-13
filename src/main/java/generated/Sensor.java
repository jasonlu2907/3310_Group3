//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.12.12 at 08:37:35 PM CST 
//


package generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="input" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lag" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element ref="{}noise"/&gt;
 *         &lt;element ref="{}quantization"/&gt;
 *         &lt;element name="drift_rate" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="bias" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element ref="{}clipto"/&gt;
 *         &lt;element name="output" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "description",
    "input",
    "lag",
    "noise",
    "quantization",
    "driftRate",
    "bias",
    "clipto",
    "output"
})
@XmlRootElement(name = "sensor")
public class Sensor {

    protected String description;
    @XmlElement(required = true)
    protected String input;
    protected float lag;
    @XmlElement(required = true)
    protected Noise noise;
    @XmlElement(required = true)
    protected Quantization quantization;
    @XmlElement(name = "drift_rate")
    protected float driftRate;
    protected float bias;
    @XmlElement(required = true)
    protected Clipto clipto;
    @XmlElement(required = true)
    protected Object output;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the input property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInput() {
        return input;
    }

    /**
     * Sets the value of the input property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInput(String value) {
        this.input = value;
    }

    /**
     * Gets the value of the lag property.
     * 
     */
    public float getLag() {
        return lag;
    }

    /**
     * Sets the value of the lag property.
     * 
     */
    public void setLag(float value) {
        this.lag = value;
    }

    /**
     * Gets the value of the noise property.
     * 
     * @return
     *     possible object is
     *     {@link Noise }
     *     
     */
    public Noise getNoise() {
        return noise;
    }

    /**
     * Sets the value of the noise property.
     * 
     * @param value
     *     allowed object is
     *     {@link Noise }
     *     
     */
    public void setNoise(Noise value) {
        this.noise = value;
    }

    /**
     * Gets the value of the quantization property.
     * 
     * @return
     *     possible object is
     *     {@link Quantization }
     *     
     */
    public Quantization getQuantization() {
        return quantization;
    }

    /**
     * Sets the value of the quantization property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantization }
     *     
     */
    public void setQuantization(Quantization value) {
        this.quantization = value;
    }

    /**
     * Gets the value of the driftRate property.
     * 
     */
    public float getDriftRate() {
        return driftRate;
    }

    /**
     * Sets the value of the driftRate property.
     * 
     */
    public void setDriftRate(float value) {
        this.driftRate = value;
    }

    /**
     * Gets the value of the bias property.
     * 
     */
    public float getBias() {
        return bias;
    }

    /**
     * Sets the value of the bias property.
     * 
     */
    public void setBias(float value) {
        this.bias = value;
    }

    /**
     * Gets the value of the clipto property.
     * 
     * @return
     *     possible object is
     *     {@link Clipto }
     *     
     */
    public Clipto getClipto() {
        return clipto;
    }

    /**
     * Sets the value of the clipto property.
     * 
     * @param value
     *     allowed object is
     *     {@link Clipto }
     *     
     */
    public void setClipto(Clipto value) {
        this.clipto = value;
    }

    /**
     * Gets the value of the output property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getOutput() {
        return output;
    }

    /**
     * Sets the value of the output property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setOutput(Object value) {
        this.output = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
