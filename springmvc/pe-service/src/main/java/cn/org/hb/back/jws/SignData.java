
package cn.org.hb.back.jws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for signData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="signData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="inData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "signData", propOrder = {
    "inData"
})
public class SignData {

    protected String inData;

    /**
     * Gets the value of the inData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInData() {
        return inData;
    }

    /**
     * Sets the value of the inData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInData(String value) {
        this.inData = value;
    }

}
